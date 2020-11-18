package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.ConjunctiveNormalForm;
import edu.iastate.cs472.proj2.models.ExpressionTreeNode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static edu.iastate.cs472.proj2.Parser.parse;
import static edu.iastate.cs472.proj2.CNFConverter.convert;
import static edu.iastate.cs472.proj2.Resolution.resolution;

public class Main {
    public static void main(String[] args) throws IOException {
        // prompt user for filename
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the name of the file you would like to read from? (src/inputs/<filename>): ");
        String filename = scan.nextLine();
        scan.close();

        // open file
        File inputFile = new File("src/inputs/" + filename);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Given filename was not found.");
            return;
        }

        // read opening of file
        String line;
        while (!reader.readLine().startsWith("Knowledge Base"));
        reader.readLine();

        // read knowledge base
        String knowledgeBasic = "";
        List<String> kbList = new ArrayList<>();
        while (!(line = reader.readLine()).startsWith("Prove the following")) {
            if (line.isEmpty() && !knowledgeBasic.isEmpty()) {
                kbList.add(knowledgeBasic);
                knowledgeBasic = "";
            } else {
                knowledgeBasic += line;
            }
        }

        // read queries
        List<String> queries = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) queries.add(line);
        }

        // close file
        reader.close();

        // create and print knowledge base
        ConjunctiveNormalForm kb = new ConjunctiveNormalForm();
        System.out.println("knowledge base in clauses:");
        System.out.println();
        for (String knowledge : kbList) {
            ConjunctiveNormalForm base = convert(parse(knowledge));
            System.out.println(base);
            System.out.println();
            kb.clauses.addAll(base.clauses);
        }

        // solve for queries
        int goalCounter = 1;
        for (String goal : queries) {
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
