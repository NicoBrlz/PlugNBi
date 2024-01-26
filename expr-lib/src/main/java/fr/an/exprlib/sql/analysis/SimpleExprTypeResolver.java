package fr.an.exprlib.sql.analysis;

import fr.an.exprlib.metadata.types.DataTypeInfo;
import fr.an.exprlib.metadata.types.DataTypeInfo.*;
import fr.an.exprlib.metadata.types.DataTypeInfoVisitor2;
import fr.an.exprlib.metadata.types.StructTypeInfoBuilder;
import fr.an.exprlib.sql.eval.EvalCtx;
import fr.an.exprlib.sql.eval.ops.EnumBinaryOperator;
import fr.an.exprlib.sql.expr.SimpleExpr;
import fr.an.exprlib.sql.expr.SimpleExpr.*;
import fr.an.exprlib.sql.expr.SimpleExprVisitor2;
import fr.an.exprlib.utils.NotImpl;
import lombok.val;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class SimpleExprTypeResolver {

    public static DataTypeInfo<?> resolve(SimpleExpr expr) {
        DataTypeInfo<?> type = expr.getResolvedType();
        if (type == null) {
            type = TypeResolverSimpleExprVisitor.INSTANCE.resolve(expr);
        }
        return type;
    }

    //---------------------------------------------------------------------------------------------

    protected static class TypeResolverSimpleExprVisitor extends SimpleExprVisitor2<DataTypeInfo<?>,Void> {
        static final TypeResolverSimpleExprVisitor INSTANCE = new TypeResolverSimpleExprVisitor();

        DataTypeInfo<?> resolve(SimpleExpr expr) {
            DataTypeInfo<?> type = expr.getResolvedType();
            if (type == null) {
                type = expr.accept(this, null);
                expr.setResolvedType(type);
            }
            return type;
        }

        @Override
        public DataTypeInfo<?> caseLiteral(LiteralExpr expr, Void ignore) {
            return literalValueType(expr.value);
        }

        @Override
        public DataTypeInfo<?> caseNamed(NamedSimpleExpr expr, Void ignore) {
            return expr.underlying.accept(this, ignore);
        }

        @Override
        public DataTypeInfo<?> caseFieldAccess(FieldAccessExpr expr, Void ignore) {
            val objType = resolve(expr.objExpr);
            if (objType instanceof DataTypeInfo.StructTypeInfo<?> objStructType) {
                val field = objStructType.nameToField.get(expr.field);
                if (field != null) {
                    expr.resolvedField = field;
                    return field.dataType;
                } else {
                    // field not found!
                    throw new IllegalArgumentException("field not found '" + expr.field + "'");
                }
            }
            throw new IllegalArgumentException("left expr is not a struct");
        }

        @Override
        public DataTypeInfo<?> caseRefByIdLookup(RefByIdLookupExpr expr, Void ignore) {
            throw NotImpl.throwNotImpl(); // TODO
        }

        @Override
        public DataTypeInfo<?> caseBinaryOp(BinaryOpExpr expr, Void ignore) {
            val leftType = resolve(expr.left);
            val rightType = resolve(expr.right);
            EnumBinaryOperator binaryOp = EnumBinaryOperator.fromText(expr.op);
            switch(binaryOp) {
                case PLUS: case MINUS: case MULTIPLY: case DIVIDE:
                    break;
                case MODULO:
                    break;
                case LOWER_THAN: case LOWER_EQ: case EQ: case NOT_EQ: case GREATER_EQ: case GREATER_THAN:
                case LIKE: case ILIKE:
                    break;
            }
            throw NotImpl.throwNotImpl(); // TODO
        }

        @Override
        public DataTypeInfo<?> caseUnaryOp(UnaryOpExpr expr, Void ignore) {
            throw NotImpl.throwNotImpl(); // TODO
        }

        @Override
        public DataTypeInfo<?> caseApplyFunc(ApplyFuncExpr expr, Void ignore) {
            throw NotImpl.throwNotImpl(); // TODO
        }
    }

    //---------------------------------------------------------------------------------------------

    public static DataTypeInfo<?> literalValueType(Object value) {
        DataTypeInfo<?> type;
        if (value == null) {
            type = NullDataTypeInfo.INSTANCE;
        } else if (value instanceof String) {
            type = StringTypeInfo.INSTANCE;
        } else if (value instanceof Boolean) {
            type = BooleanTypeInfo.INSTANCE;
        } else if (value instanceof Integer) {
            type = IntegerTypeInfo.INSTANCE;
        } else if (value instanceof Long) {
            type = LongTypeInfo.INSTANCE;
        } else if (value instanceof Float) {
            type = FloatTypeInfo.INSTANCE;
        } else if (value instanceof Double) {
            type = DoubleTypeInfo.INSTANCE;
        } else if (value instanceof Character) {
            type = CharTypeInfo.INSTANCE;
        } else if (value instanceof Short) {
            type = ShortTypeInfo.INSTANCE;
        } else if (value instanceof Byte) {
            type = ByteTypeInfo.INSTANCE;
        } else if (value instanceof BigDecimal) {
            type = DecimalTypeInfo.INSTANCE;
        } else if (value instanceof java.time.LocalDate) {
            type = DateTypeInfo.INSTANCE;
        } else if (value instanceof java.time.LocalDateTime) {
            type = DateTimeTypeInfo.INSTANCE;
        } else if (value instanceof byte[]) {
            type = BinaryTypeInfo.INSTANCE;
        } else if (value instanceof Map right) {
            type = literalValueType_Map((Map) value);
        } else if (value instanceof List) {
            type = literalValueType_List((List) value);
        } else {
            type = literalValueType_Struct(value);
        }
        return type;
    }

    private static DataTypeInfo<?> literalValueType_Struct(Object value) {
        DataTypeInfo<?> type;
        Class<?> objClass = value.getClass();
        String name = objClass.getName();
        StructTypeInfoBuilder<?> structBuilder = new StructTypeInfoBuilder<>(name, objClass);
        NotImpl.throwNotImpl(); // TODO
        type = structBuilder.build();
        // throw new IllegalStateException("unrecognized type");
        return type;
    }

    private static DataTypeInfo<?> literalValueType_List(List value) {
        DataTypeInfo<?> type;
        List<Object> listValue = value;
        DataTypeInfo<?> elementType;
        boolean valueContainsNull;
        val iter = listValue.iterator();
        if (iter.hasNext()) {
            { val firstValue = iter.next();
                elementType = literalValueType(firstValue);
                valueContainsNull = (firstValue == null);
            }
            for(; iter.hasNext(); ) {
                val elementValue = iter.next();
                val valueType = literalValueType(elementValue);
                elementType = commonTypeOf(elementType, valueType);
                valueContainsNull = (elementValue == null);
            }
        } else {
            elementType = NullDataTypeInfo.INSTANCE;
            valueContainsNull = true;
        }
        type = new ArrayTypeInfo<>(elementType, valueContainsNull);
        return type;
    }

    private static DataTypeInfo<?> literalValueType_Map(Map<Object, Object> mapValue) {
        DataTypeInfo<?> type;
        val entryIter = mapValue.entrySet().iterator();
        DataTypeInfo<?> keyType;
        DataTypeInfo<?> valueType;
        boolean valueContainsNull;
        if (entryIter.hasNext()) {
            { val firstEntry = entryIter.next();
                Object firstKey = firstEntry.getKey();
                Object firstValue = firstEntry.getValue();
                keyType = literalValueType(firstKey);
                valueType = literalValueType(firstValue);
                valueContainsNull = (firstValue == null);
            }
            for(; entryIter.hasNext(); ) {
                val entry = entryIter.next();
                Object entryKey = entry.getKey();
                Object entryValue = entry.getValue();
                DataTypeInfo<?> entryKeyType = literalValueType(entryKey);
                DataTypeInfo<?> entryValueType = literalValueType(entryValue);
                keyType = commonTypeOf(keyType, entryKeyType);
                valueType = commonTypeOf(valueType, entryValueType);
                valueContainsNull |= (entryValue == null);
            }
        } else {
            // empty! can not infer type, use Map<Null,Null> ?
            keyType = NullDataTypeInfo.INSTANCE;
            valueType = NullDataTypeInfo.INSTANCE;
            valueContainsNull = true;
        }
        type = new MapTypeInfo<>(keyType, valueType, valueContainsNull);
        return type;
    }

    public static DataTypeInfo<?> commonTypeOf(DataTypeInfo<?> left, DataTypeInfo<?> right) {
        if (left == null || left == NullDataTypeInfo.INSTANCE) {
            return right; // ?
        } else if (right == null || right == NullDataTypeInfo.INSTANCE) {
            return left; // ?
        }
        if (left == right || left.equals(right)) {
            return left;
        }
        return left.accept2(CommonTypeOfDataTypeInfoVisitor.INSTANCE, right);
    }

    private static class CommonTypeOfDataTypeInfoVisitor extends DataTypeInfoVisitor2<DataTypeInfo<?>,DataTypeInfo<?>> {
        static final CommonTypeOfDataTypeInfoVisitor INSTANCE = new CommonTypeOfDataTypeInfoVisitor();
        @Override
        public DataTypeInfo<?> caseNull(NullDataTypeInfo p, DataTypeInfo<?> right) {
            return right;
        }

        @Override
        public DataTypeInfo<?> caseStruct(StructTypeInfo<?> p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseMap(MapTypeInfo<?,?> p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof MapTypeInfo rightMap) {
                val commonKeyType = commonTypeOf(p.keyType, rightMap.keyType);
                val commonValueType = commonTypeOf(p.valueType, rightMap.valueType);
                val commonValueContainsNull = p.valueContainsNull || rightMap.valueContainsNull;
                return new MapTypeInfo<>(commonKeyType, commonValueType, commonValueContainsNull);
            } else {
                return NullDataTypeInfo.INSTANCE;// invalid!
            }
        }

        @Override
        public DataTypeInfo<?> caseArray(ArrayTypeInfo<?> p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof ArrayTypeInfo rightArray) {
                val commonElementType = commonTypeOf(p.elementType, rightArray.elementType);
                val commonContainsNulls = p.containsNulls || rightArray.containsNulls;
                return new ArrayTypeInfo<>(commonElementType, commonContainsNulls);
            } else {
                return NullDataTypeInfo.INSTANCE;// invalid!
            }
        }

        @Override
        public DataTypeInfo<?> caseBinary(BinaryTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseBoolean(BooleanTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            // TODO: if right is byte,short,int,long => coerce boolean to be 0|1 compatible
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseChar(CharTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof StringTypeInfo) {
                // (char,string) -> string
                return StringTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseString(StringTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof CharTypeInfo) {
                // (string,char) -> string
                return StringTypeInfo.INSTANCE;
            }
            // TODO: if right has a "toString()" => coerce to be string compatible?
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseDate(DateTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof StringTypeInfo) {
                // (date,string) -> date
                return DateTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseDateTime(DateTimeTypeInfo p, DataTypeInfo<?> right) {
            if (p.equals(right)) {
                return p;
            }
            if (right instanceof StringTypeInfo) {
                // (date,string) -> date
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseByte(ByteTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return ByteTypeInfo.INSTANCE; } // IDENT (byte,byte) -> byte
                if (right instanceof ShortTypeInfo) { return ShortTypeInfo.INSTANCE; } // (byte,short) -> short
                if (right instanceof IntegerTypeInfo) { return IntegerTypeInfo.INSTANCE; } // (byte,int) -> int
                if (right instanceof LongTypeInfo) { return LongTypeInfo.INSTANCE; } // (byte,long) -> long
                if (right instanceof FloatTypeInfo) { return FloatTypeInfo.INSTANCE; } // (byte,float) -> float
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (byte,double) -> double
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; } // (byte,decimal) -> decimal
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseInteger(IntegerTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return IntegerTypeInfo.INSTANCE; } // (int,byte) -> int
                if (right instanceof ShortTypeInfo) { return IntegerTypeInfo.INSTANCE; } // (int,short) -> int
                if (right instanceof IntegerTypeInfo) { return IntegerTypeInfo.INSTANCE; } // IDENT
                if (right instanceof LongTypeInfo) { return LongTypeInfo.INSTANCE; }
                if (right instanceof FloatTypeInfo) { return FloatTypeInfo.INSTANCE; }
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; }
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; }
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseLong(LongTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return LongTypeInfo.INSTANCE; } // (long,byte) -> long
                if (right instanceof ShortTypeInfo) { return LongTypeInfo.INSTANCE; } // (long,short) -> long
                if (right instanceof IntegerTypeInfo) { return LongTypeInfo.INSTANCE; } // (int,long) -> long
                if (right instanceof LongTypeInfo) { return LongTypeInfo.INSTANCE; } // IDENT
                if (right instanceof FloatTypeInfo) { return FloatTypeInfo.INSTANCE; }
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; }
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; }
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseShort(ShortTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return ShortTypeInfo.INSTANCE; } // (long,short) -> short
                if (right instanceof ShortTypeInfo) { return LongTypeInfo.INSTANCE; } // IDENT  (short,short) -> short
                if (right instanceof IntegerTypeInfo) { return IntegerTypeInfo.INSTANCE; }
                if (right instanceof LongTypeInfo) { return LongTypeInfo.INSTANCE; }
                if (right instanceof FloatTypeInfo) { return FloatTypeInfo.INSTANCE; }
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; }
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; }
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseFloat(FloatTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return FloatTypeInfo.INSTANCE; } // (float,short) -> float
                if (right instanceof ShortTypeInfo) { return FloatTypeInfo.INSTANCE; } // (float,short) -> float
                if (right instanceof IntegerTypeInfo) { return FloatTypeInfo.INSTANCE; } // (float,int) -> float
                if (right instanceof LongTypeInfo) { return FloatTypeInfo.INSTANCE; } // (float,long) -> float
                if (right instanceof FloatTypeInfo) { return FloatTypeInfo.INSTANCE; } // IDENT (float,float) -> float
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; }
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; }
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseDouble(DoubleTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                if (right instanceof ByteTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (double,short) -> double
                if (right instanceof ShortTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (double,short) -> double
                if (right instanceof IntegerTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (double,int) -> double
                if (right instanceof LongTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (double,long) -> double
                if (right instanceof FloatTypeInfo) { return DoubleTypeInfo.INSTANCE; } // (double,float) -> double
                if (right instanceof DoubleTypeInfo) { return DoubleTypeInfo.INSTANCE; } // IDENT
                if (right instanceof DecimalTypeInfo) { return DecimalTypeInfo.INSTANCE; }
                return DateTimeTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }

        @Override
        public DataTypeInfo<?> caseDecimal(DecimalTypeInfo p, DataTypeInfo<?> right) {
            if (right instanceof NumericTypeInfo) {
                return DecimalTypeInfo.INSTANCE;
            }
            return NullDataTypeInfo.INSTANCE;// invalid!
        }
    }
}
