package edu.iastate.cs472.proj2;

public class ExpressionTreeNode {
    public enum ExpressionType {
        SYMBOL,
        NOT,
        AND,
        OR,
        IF,
        IFF,
        TRUE,
        FALSE,
        LEFTP,
        RIGHTP
    }

    public ExpressionType type;
    public String value;
    public ExpressionTreeNode left;
    public ExpressionTreeNode right;

    public ExpressionTreeNode(ExpressionType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ExpressionTreeNode(ExpressionType type) {
        this(type, null);
    }

    public static int getPriority(ExpressionType type) {
        switch (type) {
            case NOT:
                return 5;
            case AND:
                return 4;
            case OR:
                return 3;
            case IF:
                return 2;
            case IFF:
                return 1;
            case LEFTP:
                return -1;
        }
        return 0;
    }

    public static ExpressionType getType(String token) {
        if (token.equals("~")) return ExpressionType.NOT;
        else if (token.equals("&&")) return ExpressionType.AND;
        else if (token.equals("||")) return ExpressionType.OR;
        else if (token.equals("=>")) return ExpressionType.IF;
        else if (token.equals("<=>")) return ExpressionType.IFF;
        else if (token.equals("(")) return ExpressionType.LEFTP;
        else if (token.equals(")")) return ExpressionType.RIGHTP;
        else if (token.equals("true")) return ExpressionType.TRUE;
        else if (token.equals("false")) return ExpressionType.FALSE;
        else return ExpressionType.SYMBOL;
    }

    public static boolean isOperand(ExpressionType type) {
        return type == ExpressionType.SYMBOL || type == ExpressionType.TRUE || type == ExpressionType.FALSE;
    }

    public static int numChildren(ExpressionType type) {
        switch (type) {
            case NOT:
                return 1;
            case AND:
            case OR:
            case IF:
            case IFF:
                return 2;
        }
        return 0;
    }
}
