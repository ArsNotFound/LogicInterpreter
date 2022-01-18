package ru.ars2014.logiccalculator.interpreter;


import ru.ars2014.logiccalculator.lexer.Token;

public class InterpreterError extends Exception {
    private final Token token;

    public InterpreterError(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String getMessage() {
        return "Interpreter error: Unexpected token: " + token.getType();
    }
}
