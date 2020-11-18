package edu.iastate.cs472.proj2.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cswoods
 *
 * A linked list of literals (logical ORed together).
 */
public class Clause {
    public List<Literal> literals;

    public Clause() {
        literals = new LinkedList<>();
    }

    public void add(Literal literal) {
        literals.add(literal);
    }

    @Override
    public String toString() {
        if (literals.isEmpty()) return "empty clause";

        Iterator<Literal> iterator = literals.iterator();
        StringBuilder ret = new StringBuilder();

        while (iterator.hasNext()) {
            ret.append(iterator.next().toString());
            if (iterator.hasNext()) ret.append(" || ");
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Clause) {
            Clause clause = (Clause)o;
            if (clause.literals.size() != literals.size()) return false;
            for (int i = 0; i < literals.size(); i++) {
                if (!clause.literals.get(i).equals(literals.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
