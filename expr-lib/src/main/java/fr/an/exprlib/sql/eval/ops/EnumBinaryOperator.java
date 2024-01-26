package fr.an.exprlib.sql.eval.ops;

public enum EnumBinaryOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE, MODULO,
    LOWER_THAN, LOWER_EQ, EQ, NOT_EQ, GREATER_EQ, GREATER_THAN,
    LIKE, ILIKE;

    public static EnumBinaryOperator fromText(String text) {
        return switch (text) {
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
            case "like" -> LIKE;
            case "ilike" -> ILIKE;
            default -> throw new IllegalArgumentException("unrecognized binary operator '" + text + "'");
        };
    }
}
