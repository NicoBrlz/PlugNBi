package fr.an.exprlib.sql.eval.ops;

import java.util.Objects;

public class SimpleBinaryOperators {

    public static final SimpleBinaryOperator EQ = new EqSimpleBinaryOperator();
    public static final SimpleBinaryOperator NOT_EQ = new NotEqSimpleBinaryOperator();
    public static final SimpleBinaryOperator LOWER_THAN = new LowerThanSimpleBinaryOperator();
    public static final SimpleBinaryOperator LOWER_EQ = new LowerEqSimpleBinaryOperator();
    public static final SimpleBinaryOperator GREATER_EQ = new GreaterEqSimpleBinaryOperator();
    public static final SimpleBinaryOperator GREATER_THAN = new GreaterThanSimpleBinaryOperator();

    public static final SimpleBinaryOperator PLUS = new PlusSimpleBinaryOperator();
    public static final SimpleBinaryOperator MINUS = new MinusSimpleBinaryOperator();
    public static final SimpleBinaryOperator MULTIPLY =  new MultiplySimpleBinaryOperator();
    public static final SimpleBinaryOperator DIVIDE =  new DivideSimpleBinaryOperator();
    public static final SimpleBinaryOperator MODULO =  new ModuloSimpleBinaryOperator();

//    public static final SimpleBinaryOperator LIKE =  new LikeSimpleBinaryOperator();
//    public static final SimpleBinaryOperator ILIKE =  new ILikeSimpleBinaryOperator();

    public static SimpleBinaryOperator forName(String text) {
        return switch(text) {
            case "+" -> PLUS;
            case "-" -> MINUS;
            case "*" -> MULTIPLY;
            case "/" -> DIVIDE;
            case "%" -> MODULO;
            case "<" -> LOWER_THAN;
            case "<=" -> LOWER_EQ;
            case "=" -> EQ;
            case "!=" -> NOT_EQ;
            case ">=" -> GREATER_EQ;
            case ">" -> GREATER_THAN;
//            case "like" -> LIKE;
//            case "ilike" -> ILIKE;
            default -> throw new IllegalArgumentException("unrecognized binary operator '" + text + "'");
        };
    }

    //---------------------------------------------------------------------------------------------

    public static class EqSimpleBinaryOperator extends SimpleBinaryOperator {

        private EqSimpleBinaryOperator() {
            super(EnumBinaryOperator.EQ);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            return left == right;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left == right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left == right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left == right;
        }

        @Override
        public Object evalString(String left, String right) {
            return Objects.equals(left, right);
        }

        @Override
        public Object evalObject(Object left, Object right) {
            return Objects.equals(left, right);
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class NotEqSimpleBinaryOperator extends SimpleBinaryOperator {

        private NotEqSimpleBinaryOperator() {
            super(EnumBinaryOperator.NOT_EQ);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            return left != right;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left != right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left != right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left != right;
        }

        @Override
        public Object evalString(String left, String right) {
            return ! Objects.equals(left, right);
        }

        @Override
        public Object evalObject(Object left, Object right) {
            return ! Objects.equals(left, right);
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class LowerThanSimpleBinaryOperator extends SimpleBinaryOperator {

        private LowerThanSimpleBinaryOperator() {
            super(EnumBinaryOperator.LOWER_THAN);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            int l = (left)? 1 : 0, r = (right)? 1 : 0;
            return l < r;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left < right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left < right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left < right;
        }

        @Override
        public Object evalString(String left, String right) {
            if (left == null) return right == null;
            return left.compareTo(right) < 0;
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class LowerEqSimpleBinaryOperator extends SimpleBinaryOperator {

        private LowerEqSimpleBinaryOperator() {
            super(EnumBinaryOperator.LOWER_EQ);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            int l = (left)? 1 : 0, r = (right)? 1 : 0;
            return l <= r;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left <= right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left <= right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left <= right;
        }

        @Override
        public Object evalString(String left, String right) {
            if (left == null) return right == null;
            return left.compareTo(right) <= 0;
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }


    //---------------------------------------------------------------------------------------------

    public static class GreaterThanSimpleBinaryOperator extends SimpleBinaryOperator {

        private GreaterThanSimpleBinaryOperator() {
            super(EnumBinaryOperator.GREATER_THAN);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            int l = (left)? 1 : 0, r = (right)? 1 : 0;
            return l > r;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left > right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left > right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left > right;
        }

        @Override
        public Object evalString(String left, String right) {
            if (left == null) return right == null;
            return left.compareTo(right) > 0;
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }


    //---------------------------------------------------------------------------------------------

    public static class GreaterEqSimpleBinaryOperator extends SimpleBinaryOperator {

        private GreaterEqSimpleBinaryOperator() {
            super(EnumBinaryOperator.GREATER_EQ);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            int l = (left)? 1 : 0, r = (right)? 1 : 0;
            return l >= r;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left >= right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left >= right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left >= right;
        }

        @Override
        public Object evalString(String left, String right) {
            if (left == null) return right == null;
            return left.compareTo(right) >= 0;
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class PlusSimpleBinaryOperator extends SimpleBinaryOperator {

        private PlusSimpleBinaryOperator() {
            super(EnumBinaryOperator.PLUS);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            return left || right;
        }

        @Override
        public Object evalInt(int left, int right) {
            return left + right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left + right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left + right;
        }

        @Override
        public Object evalString(String left, String right) {
            return left + right;
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class MinusSimpleBinaryOperator extends SimpleBinaryOperator {

        private MinusSimpleBinaryOperator() {
            super(EnumBinaryOperator.MINUS);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalInt(int left, int right) {
            return left - right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left - right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left - right;
        }

        @Override
        public Object evalString(String left, String right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class MultiplySimpleBinaryOperator extends SimpleBinaryOperator {

        private MultiplySimpleBinaryOperator() {
            super(EnumBinaryOperator.MULTIPLY);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalInt(int left, int right) {
            return left - right;
        }

        @Override
        public Object evalLong(long left, long right) {
            return left - right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            return left - right;
        }

        @Override
        public Object evalString(String left, String right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }


    //---------------------------------------------------------------------------------------------

    public static class DivideSimpleBinaryOperator extends SimpleBinaryOperator {

        private DivideSimpleBinaryOperator() {
            super(EnumBinaryOperator.DIVIDE);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalInt(int left, int right) {
            if (right == 0) {
                return null; // NaN !
            }
            return left / right;
        }

        @Override
        public Object evalLong(long left, long right) {
            if (right == 0) {
                return null; // NaN !
            }
            return left / right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            if (right == 0) {
                return null; // NaN !
            }
            return left / right;
        }

        @Override
        public Object evalString(String left, String right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class ModuloSimpleBinaryOperator extends SimpleBinaryOperator {

        private ModuloSimpleBinaryOperator() {
            super(EnumBinaryOperator.MODULO);
        }

        @Override
        public Object evalBool(boolean left, boolean right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalInt(int left, int right) {
            if (right == 0) {
                return null; // NaN !
            }
            return left % right;
        }

        @Override
        public Object evalLong(long left, long right) {
            if (right == 0) {
                return null; // NaN !
            }
            return left % right;
        }

        @Override
        public Object evalDouble(double left, double right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalString(String left, String right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evalObject(Object left, Object right) {
            throw new UnsupportedOperationException();
        }
    }

}
