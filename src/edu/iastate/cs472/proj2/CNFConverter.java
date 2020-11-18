package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.*;

import static edu.iastate.cs472.proj2.models.ExpressionTreeNode.*;

/**
 * Converts given binary expression trees into Conjunctive Normal Form.
 *
 * @author cswoods
 */
public class CNFConverter {

    /**
     * Removes implications and double implications, converting them to ORs and negations.
     *
     * @param tree
     */
    private void removeIfs(ExpressionTreeNode tree) {
        if (tree == null) return;

        if (tree.type == ExpressionType.IF) {
            tree.type = ExpressionType.OR;
            ExpressionTreeNode tempNode = new ExpressionTreeNode(ExpressionType.NOT);
            tempNode.left = tree.left;
            tree.left = tempNode;
        } else if (tree.type == ExpressionType.IFF) {
            tree.type = ExpressionType.AND;
            ExpressionTreeNode left = tree.left;
            ExpressionTreeNode right = tree.right;

            ExpressionTreeNode tempNode = new ExpressionTreeNode(ExpressionType.IF);
            tempNode.left = left;
            tempNode.right = right;
            tree.left = tempNode;

            tempNode = new ExpressionTreeNode(ExpressionType.IF);
            tempNode.left = new ExpressionTreeNode(right);
            tempNode.right = new ExpressionTreeNode(left);
            tree.right = tempNode;
        }

        removeIfs(tree.left);
        removeIfs(tree.right);
    }

    /**
     * Moves logical NOTs downwards to symbols, flipping ANDs and ORs as necessary.
     *
     * @param tree
     * @return
     */
    private ExpressionTreeNode moveNots(ExpressionTreeNode tree) {
        if (tree == null) return null;

        if (tree.type == ExpressionType.NOT) {
            ExpressionTreeNode child = tree.left;
            if (child.type == ExpressionType.NOT) {
                return moveNots(child.left);
            } else if (child.type == ExpressionType.AND || child.type == ExpressionType.OR) {
                if (child.type == ExpressionType.AND) child.type = ExpressionType.OR;
                else child.type = ExpressionType.AND;

                ExpressionTreeNode tempNode = new ExpressionTreeNode(ExpressionType.NOT);
                tempNode.left = child.left;
                child.left = moveNots(tempNode);

                tempNode = new ExpressionTreeNode(ExpressionType.NOT);
                tempNode.left = child.right;
                child.right = moveNots(tempNode);

                return child;
            }
            return tree;
        }

        tree.left = moveNots(tree.left);
        tree.right = moveNots(tree.right);

        return tree;
    }

    /**
     * Recursively makes CNF from trees with ANDs, ORs, NOTs, and symbols.
     *
     * @param tree
     * @return
     */
    private ConjunctiveNormalForm makeCNF(ExpressionTreeNode tree) {
        ConjunctiveNormalForm cnf = new ConjunctiveNormalForm();

        // this new tree should have only ANDs, ORs, and symbols.
        if (tree.type == ExpressionType.AND) {
            cnf.clauses.addAll(makeCNF(tree.left).clauses);
            cnf.clauses.addAll(makeCNF(tree.right).clauses);
        } else if (tree.type == ExpressionType.OR) {
            ConjunctiveNormalForm left = makeCNF(tree.left);
            ConjunctiveNormalForm right = makeCNF(tree.right);
            for (Clause ci : left.clauses) {
                for (Clause cj : right.clauses) {
                    Clause clause = new Clause();
                    clause.literals.addAll(ci.literals);
                    clause.literals.addAll(cj.literals);
                    cnf.add(clause);
                }
            }
        } else {
            Literal literal;
            if (tree.type == ExpressionType.NOT) {
                if (tree.left.type == ExpressionType.TRUE) literal = new Literal("false", false);
                else if (tree.left.type == ExpressionType.FALSE) literal = new Literal("true", false);
                else literal = new Literal(tree.left.value, true);
            } else {
                literal = new Literal(tree.value, false);
            }
            Clause clause = new Clause();
            clause.add(literal);
            cnf.add(clause);
        }

        return cnf;
    }

    /**
     * Converts a given binary expression tree to CNF.
     *
     * @param tree
     * @return
     */
    public ConjunctiveNormalForm convert(ExpressionTreeNode tree) {
        // remove extraneous symbols
        removeIfs(tree);
        tree = moveNots(tree);

        return makeCNF(tree);
    }
}
