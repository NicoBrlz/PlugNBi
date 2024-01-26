package fr.an.exprlib.sql.eval.ops;

public abstract class SimpleBinaryOperator {
    public final EnumBinaryOperator op;

    protected SimpleBinaryOperator(EnumBinaryOperator op) {
        this.op = op;
    }

    public abstract Object evalBool(boolean left, boolean right);
    public abstract Object evalInt(int left, int right);
    public abstract Object evalLong(long left, long right);
    public abstract Object evalDouble(double left, double right);
    public abstract Object evalString(String left, String right);
    public abstract Object evalObject(Object left, Object right);

}
