package edu.iastate.cs472.proj2.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cswoods
 *
 * A linked list of clauses (logical ANDed together).
 */
public class ConjunctiveNormalForm {
    public List<Clause> clauses;

    public ConjunctiveNormalForm() {
        clauses = new LinkedList<>();
    }

    public void add(Clause clause) {
        clauses.add(clause);
    }

    @Override
    public String toString() {
        Iterator<Clause> iterator = clauses.iterator();
        StringBuilder ret = new StringBuilder();

        while (iterator.hasNext()) {
            ret.append(iterator.next().toString());
            if (iterator.hasNext()) ret.append("\n");
        }
        return ret.toString();
    }
}
