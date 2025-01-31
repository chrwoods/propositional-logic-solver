package edu.iastate.cs472.proj2.models;

/**
 * A node for a Binary Expression Tree that builds a propositional logic statement.
 *
 * Note: for unary operators, only the left child is used.
 *
 * @author cswoods
 */
public class ExpressionTreeNode {
    /**
     * Possible expression types.
     */
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

    /**
     * Copy constructor.
     *
     * @param node The original node
     */
    public ExpressionTreeNode(ExpressionTreeNode node) {
        ExpressionTreeNode clone = new ExpressionTreeNode(node.type, node.value);
        if (node.left != null) clone.left = new ExpressionTreeNode(node.left);
        if (node.right != null) clone.right = new ExpressionTreeNode(node.right);
    }

    /**
     * Gets stack priority for postfix evaluation.
     *
     * @param type
     * @return
     */
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

    /**
     * Gets type enum from token.
     *
     * @param token
     * @return
     */
    public static ExpressionType getType(String token) {
        switch (token) {
            case "~":
                return ExpressionType.NOT;
            case "&&":
                return ExpressionType.AND;
            case "||":
                return ExpressionType.OR;
            case "=>":
                return ExpressionType.IF;
            case "<=>":
                return ExpressionType.IFF;
            case "(":
                return ExpressionType.LEFTP;
            case ")":
                return ExpressionType.RIGHTP;
            case "true":
                return ExpressionType.TRUE;
            case "false":
                return ExpressionType.FALSE;
        }
        return ExpressionType.SYMBOL;
    }

    /**
     * Returns true if the type is an operand, false if it is an operator.
     *
     * @param type
     * @return
     */
    public static boolean isOperand(ExpressionType type) {
        return type == ExpressionType.SYMBOL || type == ExpressionType.TRUE || type == ExpressionType.FALSE;
    }

    /**
     * Gets number of children a node will have for each type.
     *
     * @param type
     * @return
     */
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

    @Override
    public String toString() {
        if (type == ExpressionType.NOT) {
            return "~(" + left.toString() + ")";
        }
        String ret = "";
        if (left != null) ret += left.toString();
        switch (type) {
            case AND:
                ret += "&&";
                break;
            case OR:
                ret += "||";
                break;
            case IF:
                ret += "=>";
                break;
            case IFF:
                ret += "<=>";
                break;
            default:
                ret += value;
        }
        if (right != null) ret += right.toString();
        return ret;
    }
}
