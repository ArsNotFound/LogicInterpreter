package ru.ars2014.logiccalculator.ast;

import ru.ars2014.logiccalculator.interpreter.InterpreterError;
import ru.ars2014.logiccalculator.lexer.Token;
import ru.ars2014.logiccalculator.visitor.Visitor;

public class Identifier extends AST {
    private final Token p;

    public Identifier(Token p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "p=" + p +
                '}';
    }

    public Token getP() {
        return p;
    }

    @Override
    public boolean accept(Visitor visitor) throws InterpreterError  {
        return visitor.visitIdentifier(this);
    }
}
