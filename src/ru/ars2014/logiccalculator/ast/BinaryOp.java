package ru.ars2014.logiccalculator.ast;

import ru.ars2014.logiccalculator.interpreter.InterpreterError;
import ru.ars2014.logiccalculator.lexer.Token;
import ru.ars2014.logiccalculator.visitor.Visitor;

public class BinaryOp extends AST {
    private final AST left;
    private final Token op;
    private final AST right;

    public BinaryOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryOp{" +
                "left=" + left +
                ", op=" + op +
                ", right=" + right +
                '}';
    }

    public AST getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public AST getRight() {
        return right;
    }

    @Override
    public boolean accept(Visitor visitor) throws InterpreterError {
        return visitor.visitBinaryOp(this);
    }
}
