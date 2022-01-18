package ru.ars2014.logiccalculator.parser;

import ru.ars2014.logiccalculator.lexer.Token;

public class ParserError extends Exception {
    private final Token token;
    private final Token.Type expectedType;

    public ParserError(Token token, Token.Type expectedType) {
        this.token = token;
        this.expectedType = expectedType;
    }

    public Token getToken() {
        return token;
    }

    public Token.Type getExpectedType() {
        return expectedType;
    }

    @Override
    public String getMessage() {
        return "Parser error: Expected type " + expectedType + ", got " + token;
    }
}
