package fr.an.exprlib.sql.expr;

import fr.an.exprlib.sql.expr.SimpleExpr.*;

public abstract class SimpleExprVisitor {

    public abstract void caseLiteral(LiteralExpr expr);

    public abstract void caseNamed(NamedSimpleExpr expr);

    public abstract void caseFieldAccess(FieldAccessExpr expr);

    public abstract void caseRefByIdLookup(RefByIdLookupExpr expr);

    public abstract void caseBinaryOp(BinaryOpExpr expr);

    public abstract void caseUnaryOp(UnaryOpExpr expr);

    public abstract void caseApplyFunc(ApplyFuncExpr expr);

}
