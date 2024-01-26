package fr.an.exprlib.sql.expr;

import fr.an.exprlib.metadata.types.DataTypeInfo;
import fr.an.exprlib.metadata.types.DataTypeInfo.StructFieldInfo;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.beans.Expression;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class SimpleExpr {

    protected DataTypeInfo<?> resolvedType;
    protected Map<String,Object> attrs;

    //---------------------------------------------------------------------------------------------

    public abstract void accept(SimpleExprVisitor visitor);

    public abstract <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param);


    public <T> T getAttr(String attrName) {
        if (attrs == null) return null;
        return (T) attrs.get(attrName);
    }
    public <T> void setAttr(String attrName, T value) {
        if (attrs == null) this.attrs = new HashMap<>();
        attrs.put(attrName, value);
    }

    public void setResolvedType(DataTypeInfo<?> p) {
        if (resolvedType != null) throw new IllegalStateException("already set");
        this.resolvedType = Objects.requireNonNull(p);
    }
    public DataTypeInfo<?> getResolvedType() { return this.resolvedType; }

    //---------------------------------------------------------------------------------------------

    @AllArgsConstructor
    public static class LiteralExpr extends SimpleExpr {
        public Object value;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseLiteral(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseLiteral(this, param);
        }

    }

    public static class NamedSimpleExpr extends SimpleExpr {
        public SimpleExpr underlying;
        public String qualifier;
        public String name;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseNamed(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseNamed(this, param);
        }

    }


    public static class FieldAccessExpr extends SimpleExpr {
        public SimpleExpr objExpr;
        public String field;

        public StructFieldInfo<?,?> resolvedField;

        public FieldAccessExpr(SimpleExpr objExpr, String field) {
            this.objExpr = objExpr;
            this.field = field;
        }

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseFieldAccess(this);
        }

        @Override
        public <TRes, TParam> TRes accept(SimpleExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseFieldAccess(this, param);
        }
    }

    @AllArgsConstructor
    public static class RefByIdLookupExpr extends SimpleExpr {
        public String namespace;
        public SimpleExpr idExpr;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseRefByIdLookup(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseRefByIdLookup(this, param);
        }

    }

    @AllArgsConstructor
    public static class BinaryOpExpr extends SimpleExpr {
        public SimpleExpr left;
        public String op;
        public SimpleExpr right;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseBinaryOp(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseBinaryOp(this, param);
        }

    }

    @AllArgsConstructor
    public static class UnaryOpExpr extends SimpleExpr {
        public String op;
        public SimpleExpr expr;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseUnaryOp(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseUnaryOp(this, param);
        }

    }

    @AllArgsConstructor
    public static class ApplyFuncExpr extends SimpleExpr {
        public String function;
        public List<SimpleExpr> args;

        @Override
        public void accept(SimpleExprVisitor visitor) {
            visitor.caseApplyFunc(this);
        }

        @Override
        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseApplyFunc(this, param);
        }

    }

    /**
     * cf https://spark.apache.org/docs/latest/sql-ref-syntax-qry-select-window.html
     * <PRE>
     * window_function [ nulls_option ] OVER ( [  { PARTITION | DISTRIBUTE } BY partition_col_name = partition_col_val ( [ , ... ] ) ]
     *   { ORDER | SORT } BY expression [ ASC | DESC ] [ NULLS { FIRST | LAST } ] [ , ... ]
     *   [ window_frame ] )
     *
     * window_function:
     *   Ranking Functions
     *      Syntax: RANK | DENSE_RANK | PERCENT_RANK | NTILE | ROW_NUMBER
     *   Analytic Functions
     *      Syntax: CUME_DIST | LAG | LEAD | NTH_VALUE | FIRST_VALUE | LAST_VALUE
     *   Aggregate Functions
     *      Syntax: MAX | MIN | COUNT | SUM | AVG | ...
     *  Please refer to the Built-in Aggregation Functions document for a complete list of Spark aggregate functions.
     *
     * nulls_option : Specifies whether or not to skip null values when evaluating the window function. RESPECT NULLS means not skipping null values, while IGNORE NULLS means skipping. If not specified, the default is RESPECT NULLS.
     * Syntax: { IGNORE | RESPECT } NULLS
     * Note: Only LAG | LEAD | NTH_VALUE | FIRST_VALUE | LAST_VALUE can be used with IGNORE NULLS.
     *
     * window_frame: Specifies which row to start the window on and where to end it.
     * Syntax: { RANGE | ROWS } { frame_start | BETWEEN frame_start AND frame_end }
     *
     * frame_start and frame_end have the following syntax:
     * Syntax: UNBOUNDED PRECEDING | offset PRECEDING | CURRENT ROW | offset FOLLOWING | UNBOUNDED FOLLOWING
     *
     * offset: specifies the offset from the position of the current row.
     *
     * Note: If frame_end is omitted it defaults to CURRENT ROW.
     * </PRE>
     */
//    @AllArgsConstructor
//    public static class GroupAccumulatorExpr extends SimpleExpr {
//        public String accumulatorFunction; // average, min, max, count, first, last, distinctSet, ...
//        public SimpleExpr expr;
//
//        @Override
//        public void accept(SimpleExprVisitor visitor) {
//            visitor.caseGroupAccumulator(this);
//        }
//
//        @Override
//        public <TRes,TParam> TRes accept(SimpleExprVisitor2<TRes,TParam> visitor, TParam param) {
//            return visitor.caseGroupAccumulator(this, param);
//        }
//
//    }


//    public static class WindowExpr extends SimpleExpr {
//        windowExpressions: Seq[NamedExpression],
//        partitionSpec: Seq[Expression],
//        orderSpec: Seq[SortOrder],
//        child: LogicalPlan) extends UnaryNode {
//
//    }
//
//    public static class SimpleWindowSpec {
//        public List<SimpleExpr> partitionSpec;
//        public List<SimpleExprSortOrder> orderSpec;
//        public SimpleWindowFrame frame;
//    }
//    public static class SimpleWindowFrame {
//        public SimpleFrameType frameType;
//        public SimpleExpr lower;
//        public SimpleExpr upper;
//    }
//    public enum SimpleFrameType { ROW, RANGE };
//

    @AllArgsConstructor
    public static class SimpleExprSortOrder {
        public SimpleExpr child;
        public boolean descending;
        public boolean nullLast;
        //?? List<SimpleExpr> sameOrderExpressions
    }

}
