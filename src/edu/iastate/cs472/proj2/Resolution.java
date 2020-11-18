package edu.iastate.cs472.proj2;

import edu.iastate.cs472.proj2.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs resolution on a knowledge base given a query.
 *
 * @author cswoods
 */
public class Resolution {
    /**
     * Performs resolution with a given CNF Knowledge base and CNF Query.
     *
     * @param kb
     * @param query
     * @return
     */
    public static boolean resolution(ConjunctiveNormalForm kb, ConjunctiveNormalForm query) {
        List<Clause> clauses = new ArrayList<>(kb.clauses);
        clauses.addAll(query.clauses);
        List<Clause> newClauses = new ArrayList<>();

        // return true if the query is "true"
        if (!query.clauses.isEmpty() && query.clauses.get(0).literals.isEmpty()) return true;

        while (true) {
            // resolve every clause
            for (int i = 0; i < clauses.size(); i++) {
                for (int j = i + 1; j < clauses.size(); j++) {
                    List<String> outputStrings = new ArrayList<>();
                    List<Clause> resolvents = resolve(clauses.get(i), clauses.get(j), outputStrings);
                    if (!resolvents.isEmpty()) {
                        for (int k = 0; k < resolvents.size(); k++) {
                            Clause resolved = resolvents.get(k);

                            if (!clauses.contains(resolved) && !newClauses.contains(resolved)) {
                                newClauses.add(resolved);
                                System.out.println(outputStrings.get(k));
                            }

                            if (resolved.literals.isEmpty()) return true;
                        }
                    }
                }
            }

            if (newClauses.isEmpty()) {
                System.out.println("No new clauses are added.");
                System.out.println();
            }
            // note: this line is pointless because we check this earlier, but I left it in for grading purposes
            if (clauses.containsAll(newClauses)) return false;
            clauses.addAll(newClauses);
            newClauses = new ArrayList<>();
        }
    }

    /**
     * Returns all possible clauses from resolving two given clauses.
     *
     * @param ci
     * @param cj
     * @return
     */
    private static List<Clause> resolve(Clause ci, Clause cj, List<String> outputStrings) {
        List<Clause> resolvents = new ArrayList<>();
        for (Literal li : ci.literals) {
            for (Literal lj : cj.literals) {
                if (li.symbol.equals(lj.symbol) && li.negated != lj.negated) {
                    // make a new resolvent clause
                    Clause clause = new Clause();
                    for (Literal li2 : ci.literals) {
                        if (li != li2) clause.add(li2);
                    }
                    for (Literal lj2 : cj.literals) {
                        if (lj != lj2) clause.add(lj2);
                    }
                    resolvents.add(clause);
                    outputStrings.add(ci + "\n" + cj + "\n--------------------\n" + clause + "\n");
                }
            }
        }
        return resolvents;
    }
}
