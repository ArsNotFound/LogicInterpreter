package ru.ars2014.logiccalculator.lexer;

import java.util.Objects;


public class Token {
    private String literal;
    private Type type;
    private int pos;

    public Token(String literal, Type type, int pos) {
        this.literal = literal;
        this.type = type;
        this.pos = pos;
    }

    public Token(Token token) {
        this.literal = token.literal;
        this.type = token.type;
        this.pos = token.pos;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return pos == token.pos && literal.equals(token.literal) && type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal, type, pos);
    }

    @Override
    public String toString() {
        return "Token{" +
                "literal='" + literal + '\'' +
                ", type=" + type +
                ", pos=" + pos +
                '}';
    }

    public enum Type {
        // Special
        EOF("EOF"),

        // Identifiers
        Param("Parameter"), // A, B, Test, ...
        Const("Const"), // 1, 0

        // Operators
        Not("Not"), // !
        Conj("Conjunction"), // *
        Disj("Disjunction"), // +
        Impl("Implication"), // ->
        Equal("Equalization"), // <=>

        LParen("LeftParen"), // (
        RParen("RightParen"), // )
        ;

        final String str;

        Type(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return "Type(" + str + ")";
        }
    }

}
