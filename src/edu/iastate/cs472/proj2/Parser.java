package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.iastate.cs472.proj2.models.ExpressionTreeNode.*;

/**
 * @author cswoods
 *
 * Parses given propositional logic sentences into binary expression trees.
 */
public class Parser {
    static final String tokenRegex = "[A-Z][A-Za-z]*|~|\\(|\\)|&&|\\|\\||=>|<=>|true|false";

    /**
     * Parses a sentence into a tree. This is done by first converting into a postfix stack,
     * then converting that stack into a tree with a helper method.
     *
     * @param sentence
     * @return
     */
    public static ExpressionTreeNode parse(String sentence) {
        Pattern pattern = Pattern.compile(tokenRegex);
        Matcher matcher = pattern.matcher(sentence);

        PureStack<ExpressionTreeNode> fullStack = new ArrayBasedStack<>();
        PureStack<ExpressionTreeNode> operatorStack = new ArrayBasedStack<>();

        while (matcher.find()) {
            String token = matcher.group();
            ExpressionType type = getType(token);
            ExpressionTreeNode node = new ExpressionTreeNode(type);

            if (isOperand(type)) {
                // if (type == ExpressionType.SYMBOL) node.value = token;
                node.value = token;
                fullStack.push(node);
            } else {
                if (type == ExpressionType.RIGHTP) {
                    ExpressionTreeNode popped = operatorStack.pop();
                    while (popped.type != ExpressionType.LEFTP) {
                        fullStack.push(popped);
                        popped = operatorStack.pop();
                    }
                } else if (type == ExpressionType.LEFTP) {
                    operatorStack.push(node);
                } else {
                    while (!operatorStack.isEmpty()) {
                        ExpressionTreeNode top = operatorStack.peek();
                        if (getPriority(type) >= getPriority(top.type)) {
                            break;
                        } else {
                            fullStack.push(operatorStack.pop());
                        }
                    }
                    operatorStack.push(node);
                }
            }
        }

        while(!operatorStack.isEmpty()) {
            fullStack.push(operatorStack.pop());
        }

        return makeTree(fullStack);
    }

    /**
     * Recursively makes a tree from a stack of nodes.
     *
     * @param stack
     * @return
     */
    private static ExpressionTreeNode makeTree(PureStack<ExpressionTreeNode> stack) {
        if (stack.isEmpty()) return null;

        ExpressionTreeNode node = stack.pop();
        int children = numChildren(node.type);
        if (children == 0) return node;
        if (children == 2) {
            node.right = makeTree(stack);
        }
        if (children > 0) {
            node.left = makeTree(stack);
        }

        return node;
    }
}
