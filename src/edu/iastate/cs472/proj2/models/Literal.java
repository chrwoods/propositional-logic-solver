package edu.iastate.cs472.proj2.models;

/**
 * @author cswoods
 *
 * A literal, a part of a clause, which can be negated or non-negated.
 */
public class Literal {
    public String symbol;
    public boolean negated;

    public Literal(String symbol, boolean negated) {
        this.symbol = symbol;
        this.negated = negated;
    }

    @Override
    public String toString() {
        return (negated ? "~" : "") + symbol;
    }
}
