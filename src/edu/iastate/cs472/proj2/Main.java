package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.ConjunctiveNormalForm;
import edu.iastate.cs472.proj2.models.ExpressionTreeNode;

import static edu.iastate.cs472.proj2.Resolution.*;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        CNFConverter converter = new CNFConverter();

        ExpressionTreeNode tree = parser.parse("(( Rain && Outside ) => Wet)&&" +
                "(( Warm && ~Rain ) => Pleasant)&&" +
                "(~Wet)&&" +
                "(Outside)&&" +
                "(Warm)");
        ConjunctiveNormalForm kb = converter.convert(tree);

        System.out.println(kb);

        tree = parser.parse("Rain");
        ExpressionTreeNode negated = new ExpressionTreeNode(ExpressionTreeNode.ExpressionType.NOT);
        negated.left = tree;
        ConjunctiveNormalForm query = converter.convert(negated);

        System.out.println(resolution(kb, query));
    }
}
