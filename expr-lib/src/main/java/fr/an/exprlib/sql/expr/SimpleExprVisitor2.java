package fr.an.exprlib.sql.expr;

import fr.an.exprlib.sql.expr.SimpleExpr.NamedSimpleExpr;

public abstract class SimpleExprVisitor2<TRes,TParam> {

    public abstract TRes caseLiteral(SimpleExpr.LiteralExpr expr, TParam param);

    public abstract TRes caseNamed(NamedSimpleExpr expr, TParam param);

    public abstract TRes caseFieldAccess(SimpleExpr.FieldAccessExpr expr, TParam param);

    public abstract TRes caseRefByIdLookup(SimpleExpr.RefByIdLookupExpr expr, TParam param);

    public abstract TRes caseBinaryOp(SimpleExpr.BinaryOpExpr expr, TParam param);

    public abstract TRes caseUnaryOp(SimpleExpr.UnaryOpExpr expr, TParam param);

    public abstract TRes caseApplyFunc(SimpleExpr.ApplyFuncExpr expr, TParam param);

}
