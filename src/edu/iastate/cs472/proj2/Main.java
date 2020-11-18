package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.ConjunctiveNormalForm;
import edu.iastate.cs472.proj2.models.ExpressionTreeNode;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        ExpressionTreeNode tree = parser.parse("~( P && ~ Q) || R => S && ~T");
//        System.out.println(tree);

        CNFConverter converter = new CNFConverter();
        ConjunctiveNormalForm cnf = converter.convert(tree);
        System.out.println(cnf);
    }
}
