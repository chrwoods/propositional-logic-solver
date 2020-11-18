package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.ConjunctiveNormalForm;
import edu.iastate.cs472.proj2.models.ExpressionTreeNode;

import static edu.iastate.cs472.proj2.Parser.parse;
import static edu.iastate.cs472.proj2.CNFConverter.convert;
import static edu.iastate.cs472.proj2.Resolution.resolution;

public class Main {
    public static void main(String[] args) {
        // create and print knowledge base
        ConjunctiveNormalForm kb = new ConjunctiveNormalForm();
        System.out.println("knowledge base in clauses:");
        System.out.println();
        for (String knowledge : new String[]{"(( Rain && Outside ) => Wet)",
                "(( Warm && ~Rain ) => Pleasant)",
                "(~Wet)",
                "(Outside)&&" +
                "(Warm)"}) {
            ConjunctiveNormalForm base = convert(parse(knowledge));
            System.out.println(base);
            System.out.println();
            kb.clauses.addAll(base.clauses);
        }

        int goalCounter = 1;
        for (String goal : new String[]{"Pleasant", "Rain"}) {
            // print goal sentence
            System.out.println("****************");
            System.out.println("Goal sentence " + goalCounter++ + ":");
            System.out.println();
            System.out.println(goal);
            System.out.println("****************");
            System.out.println();

            // negate goal
            ExpressionTreeNode negated = new ExpressionTreeNode(ExpressionTreeNode.ExpressionType.NOT);
            negated.left = parse(goal);
            ConjunctiveNormalForm query = convert(negated);

            // print negated goal
            System.out.println("Negated goal in clauses:");
            System.out.println();
            System.out.println(query);
            System.out.println();

            if(resolution(kb, query)) {
                System.out.println("The KB entails " + goal + ".");
            } else {
                System.out.println("The KB does not entail " + goal + ".");
            }
            System.out.println();
        }
    }
}
