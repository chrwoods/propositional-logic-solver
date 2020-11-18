package edu.iastate.cs472.proj2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.iastate.cs472.proj2.ExpressionTreeNode.*;

/**
 * @author cswoods
 */
public class Parser {
    static final String tokenRegex = "[A-Z][A-Za-z]*|~|\\(|\\)|&&|\\|\\||=>|<=>|true|false";

    public ExpressionTreeNode parse(String sentence) {
        Pattern pattern = Pattern.compile(tokenRegex);
        Matcher matcher = pattern.matcher(sentence);

        PureStack<ExpressionTreeNode> fullStack = new ArrayBasedStack<>();
        PureStack<ExpressionTreeNode> operatorStack = new ArrayBasedStack<>();

        while (matcher.find()) {
            String token = matcher.group();
            ExpressionType type = getType(token);
            ExpressionTreeNode node = new ExpressionTreeNode(type);

            if (isOperand(type)) {
                if (type == ExpressionType.SYMBOL) node.value = token;
                fullStack.push(node);
            } else {
                if (type == ExpressionType.RIGHTP) {
                    ExpressionTreeNode popped = operatorStack.pop();
                    while (popped.type != ExpressionType.LEFTP) {
                        fullStack.push(popped);
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
                operatorStack.push(node);
            }
        }

        while(!operatorStack.isEmpty()) {
            fullStack.push(operatorStack.pop());
        }

        return null;
    }
}
