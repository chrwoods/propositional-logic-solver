package edu.iastate.cs472.proj2;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parse("~( PaaaaAa && ~ Q) || R => S && ~T");
    }
}
