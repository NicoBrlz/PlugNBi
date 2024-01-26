package fr.an.exprlib.sql.analysis;

import fr.an.exprlib.metadata.TableInfo;
import fr.an.exprlib.sql.eval.EvalCtx;
import fr.an.exprlib.sql.eval.ops.SimpleBinaryOperator;
import fr.an.exprlib.sql.eval.ops.SimpleBinaryOperators;
import fr.an.exprlib.sql.expr.SimpleExpr;
import fr.an.exprlib.sql.expr.SimpleExpr.NamedSimpleExpr;
import fr.an.exprlib.sql.expr.SimpleExprVisitor2;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

public class SimpleExprToEvalFunctionResolver {

    public <T> BiFunction<T, EvalCtx,Object> resolveSimpleExprToObjEvalFunction(
            TableInfo<T> tableInfo,
            SimpleExpr expr) {
        val visitor = new SimpleExprVisitor2<BiFunction<T,EvalCtx,Object>,Void>() {
            @Override
            public BiFunction<T, EvalCtx, Object> caseLiteral(SimpleExpr.LiteralExpr expr, Void unused) {
                return (obj,ctx) -> expr.value;
            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseNamed(NamedSimpleExpr expr, Void unused) {
                return expr.underlying.accept(this, unused);
            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseFieldAccess(SimpleExpr.FieldAccessExpr expr, Void unused) {
                Function<T, ?> objGetter = tableInfo.resolveObjectEvalFunc(expr.field);
                return (obj,ctx) -> objGetter.apply(obj);
            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseRefByIdLookup(SimpleExpr.RefByIdLookupExpr expr, Void unused) {
                BiFunction<T, EvalCtx, Object> idValueEvalFunc = expr.idExpr.accept(this, null);
                // TODO get or register Map<Integer,Object> objByIdMap  for namespace...
                return (obj,ctx) -> {
                    int idValue = (Integer) idValueEvalFunc.apply(obj, ctx);
                    Map<Integer,Object> objByIdMap = ctx.getProp(expr.namespace);
                    if (objByIdMap == null) {
                        objByIdMap = new HashMap<Integer,Object>();
                        // TODO need to perform "findAll", then put in map for corresponding object ids

                        throw new UnsupportedOperationException("NOT IMPLEMENTED YET");
                        // ctx.putProp(expr.namespace, objByIdMap);
                    }
                    return objByIdMap.get(idValue);
                };

            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseBinaryOp(SimpleExpr.BinaryOpExpr expr, Void param) {
                BiFunction<T, EvalCtx, Object> leftEvalFunc = expr.left.accept(this, param);
                BiFunction<T, EvalCtx, Object> rightEvalFunc = expr.right.accept(this, param);
                BiFunction<Object,Object,Object> opFunction;
                switch(expr.op) {
                    case "=": case "!=":
                    case "+": case "-": case "*": case "/":
                    case "<": case "<=": case ">": case ">=":
                        // coerce types, example: "int", "long" -> "long", "long"
                        opFunction = stdArithBinaryOpExprToEvalFunction(expr);
                        break;
                    case "like": case "~":
                        opFunction = (BiFunction) likeExprToEvalFunction(expr);
                        break;
                    default:
                        // should not occur
                        throw new IllegalArgumentException("unrecognized op '" + expr.op + "'");
                }
                return (obj,ctx) -> {
                    val left = leftEvalFunc.apply(obj, ctx);
                    val right = rightEvalFunc.apply(obj, ctx);
                    return opFunction.apply(left, right);
                };
            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseUnaryOp(SimpleExpr.UnaryOpExpr expr, Void param) {
                BiFunction<T, EvalCtx, Object> underlyingEvalFunc = expr.expr.accept(this, param);
                Function<Object,Object> opFunction = switch(expr.op) {
                    case "-" -> negativeOpExprToEvalFunction(expr);
                    case "not" -> notOpExprToEvalFunction(expr);
                    default -> throw new IllegalArgumentException("unrecognized op '" + expr.op + "'");
                };
                return (obj,ctx) -> {
                    val underlyingValue = underlyingEvalFunc.apply(obj, ctx);
                    return opFunction.apply(underlyingValue);
                };
            }

            @Override
            public BiFunction<T, EvalCtx, Object> caseApplyFunc(SimpleExpr.ApplyFuncExpr expr, Void param) {
                Function<List<Object>,Object> evalFunction = switch(expr.function) {
                    case "struct" -> ((ls) -> ls);
                    // TOADD "min", "max", "
                    default -> throw new IllegalArgumentException("unrecognized function '" + expr.function + "'");
                };
                List<BiFunction<T, EvalCtx, Object>> argEvalFuncs = new ArrayList<>();
                for(val argExpr: expr.args) {
                    val argEvalFunc = argExpr.accept(this, param);
                    argEvalFuncs.add(argEvalFunc);
                }
                return (obj,ctx) -> {
                    List<Object> argValues = new ArrayList<>();
                    for(val argEvalFunc : argEvalFuncs) {
                        Object argValue = argEvalFunc.apply(obj, ctx);
                        argValues.add(argValue);
                    }
                    return evalFunction.apply(argValues);
                };
            }

        };
        return expr.accept(visitor, null);
    }


    private static BiFunction<Object, Object, Object> stdArithBinaryOpExprToEvalFunction(SimpleExpr.BinaryOpExpr expr) {
        BiFunction<Object, Object, Object> opFunction;
        SimpleBinaryOperator simpleOp = SimpleBinaryOperators.forName(expr.op);
        opFunction = (left,right) -> {
            if (left == null || right == null) {
                return null; // should not occur?
            }
            if (left instanceof Integer) { // TODO type could be resolved once at "prepare"(=compile) time
                int l = (Integer) left;
                if (right instanceof Integer) {
                    int r = (Integer) right;
                    return simpleOp.evalInt(l, r);
                } else if (right instanceof Long) {
                    long  r = (Long) right;
                    return simpleOp.evalLong((long) l, r);
                } else if (right instanceof Double) {
                    double r = (Double) right;
                    return simpleOp.evalDouble((double) l, r);
                } else {
                    throw new UnsupportedOperationException();
                }
            } else if (left instanceof Long) {
                long l = (Long) left;
                if (right instanceof Integer) {
                    int r = (Integer) right;
                    return simpleOp.evalLong(l, (long) r);
                } else if (right instanceof Long) {
                    long r = (Long) right;
                    return simpleOp.evalLong(l, r);
                } else if (right instanceof Double) {
                    double r = (Double) right;
                    return simpleOp.evalDouble((double) l, r);
                } else {
                    throw new UnsupportedOperationException();
                }
            } else if (left instanceof String) {
                String l = (String) left;
                if (right instanceof String) {
                    String r = (String) right;
                    return simpleOp.evalString(l, r);
                } else {
                    throw new UnsupportedOperationException();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        };
        return opFunction;
    }

    private static BiFunction<Object, Object, Boolean> likeExprToEvalFunction(SimpleExpr.BinaryOpExpr expr) {
        BiFunction<Object, Object, Boolean> opFunction;
        // right should always be LiteralExprDTO ?
        if (!(expr.right instanceof SimpleExpr.LiteralExpr)) {
            throw new IllegalArgumentException("binaryOpExpr with 'like' should use literal right expr");
        }
        String right = (String) ((SimpleExpr.LiteralExpr) expr.right).value;
        if (right.startsWith("%") && right.endsWith("%")) {
            opFunction = (left,r) -> {
                if (left == null) {
                    return null;
                }
                if (left instanceof String) {
                    String leftStr = (String) left;
                    return leftStr.indexOf(right) != -1;
                } else {
                    return null;
                }
            };
        } else if (right.endsWith("%")) {
            opFunction = (left,r) -> {
                if (left == null) {
                    return null;
                }
                if (left instanceof String) {
                    String leftStr = (String) left;
                    return leftStr.startsWith(right);
                } else {
                    return null;
                }
            };
        } else if (right.startsWith("%")) {
            opFunction = (left,r) -> {
                if (left == null) {
                    return null;
                }
                if (left instanceof String) {
                    String leftStr = (String) left;
                    return leftStr.endsWith(right);
                } else {
                    return null;
                }
            };
        } else {
            // transform to regexp
            String rightRegexpText = right.replace("%", ".*")
                    .replace("?", "."); // more replace ??
            val rightPattern = Pattern.compile(rightRegexpText);
            opFunction = (left,r) -> {
                if (left == null) {
                    return null;
                }
                if (left instanceof String) {
                    String leftStr = (String) left;
                    return rightPattern.matcher(leftStr).matches();
                } else {
                    return false;
                }
            };
        }
        return opFunction;
    }

    private Function<Object, Object> negativeOpExprToEvalFunction(SimpleExpr.UnaryOpExpr expr) {
        return (value) -> {
            if (value == null) {
                return null;
            } else if (value instanceof Integer) {
                boolean v = (Boolean) value;
                return ! v;
            } else if (value instanceof Long) {
                long v = (Long) value;
                return - v;
            } else if (value instanceof Float) {
                Float v = (Float) value;
                return - v;
            } else if (value instanceof Double) {
                Double v = (Double) value;
                return - v;
            } else {
                return null; // should not occur
            }
        };
    }


    private Function<Object, Object> notOpExprToEvalFunction(SimpleExpr.UnaryOpExpr expr) {
        return (value) -> {
            if (value == null) {
                return null;
            } else if (value instanceof Boolean) {
                Boolean boolValue = (Boolean) value;
                return ! boolValue;
            } else {
                return null; // should not occur
            }
        };
    }

}
