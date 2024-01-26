package fr.an.exprlib.sql.analysis;

import fr.an.exprlib.dto.expr.ExprDTO;
import fr.an.exprlib.sql.expr.SimpleExpr;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExprDTOToSimpleExprConverter {

    private static final ExprDTOConverterVisitor exprDtoToSimpleExprConverter = new ExprDTOConverterVisitor();


    public static SimpleExpr dtoToSimpleExpr(ExprDTO expr) {
        return expr.accept(exprDtoToSimpleExprConverter, null);
    }

    //---------------------------------------------------------------------------------------------

    private static class ExprDTOConverterVisitor extends ExprDTO.SimpleExprDTOVisitor<SimpleExpr,Void> {

        protected SimpleExpr toExprOrNull(ExprDTO expr) {
            return (expr != null)? toExpr(expr) : null;
        }
        protected SimpleExpr toExpr(ExprDTO expr) {
            return expr.accept(this, null);
        }

        @Override
        public SimpleExpr caseLiteral(ExprDTO.LiteralExprDTO expr, Void unused) {
            if (expr.intValue != null) {
                return new SimpleExpr.LiteralExpr(expr.intValue);
            }else if (expr.doubleValue != null) {
                return new SimpleExpr.LiteralExpr(expr.doubleValue);
            } else if (expr.strValue != null) {
                return new SimpleExpr.LiteralExpr(expr.strValue);
            } else {
                // should not occur
                return new SimpleExpr.LiteralExpr(null);
            }
        }

        @Override
        public SimpleExpr caseFieldAccess(ExprDTO.FieldAccessExprDTO expr, Void unused) {
            val objExpr = toExpr(expr);
            return new SimpleExpr.FieldAccessExpr(objExpr, expr.field);
        }

        @Override
        public SimpleExpr caseRefByIdLookup(ExprDTO.RefByIdLookupExprDTO expr, Void unused) {
            val idExpr = toExpr(expr.idExpr);
            return new SimpleExpr.RefByIdLookupExpr(expr.namespace, idExpr);
        }

        @Override
        public SimpleExpr caseGroupAccumulator(ExprDTO.GroupAccumulatorExprDTO expr, Void unused) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SimpleExpr caseBinaryOp(ExprDTO.BinaryOpExprDTO expr, Void unused) {
            val leftExpr = toExpr(expr.left);
            val rightExpr = toExpr(expr.right);
            val op = Objects.requireNonNull(expr.op);
            return new SimpleExpr.BinaryOpExpr(leftExpr, op, rightExpr);
        }

        @Override
        public SimpleExpr caseUnaryOp(ExprDTO.UnaryOpExprDTO expr, Void unused) {
            val op = Objects.requireNonNull(expr.op);
            val underlyingExpr = toExpr(expr.expr);
            return new SimpleExpr.UnaryOpExpr(op, underlyingExpr);
        }

        @Override
        public SimpleExpr caseApplyFunc(ExprDTO.ApplyFuncExprDTO expr, Void unused) {
            List<SimpleExpr> args = new ArrayList<>();
            for(val argDto: expr.args) {
                val argExpr = toExpr(argDto);
                args.add(argExpr);
            }
            val function = Objects.requireNonNull(expr.function);
            return new SimpleExpr.ApplyFuncExpr(function, args);
        }

    };

}
