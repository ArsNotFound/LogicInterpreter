package ru.ars2014.logiccalculator.visitor;

import ru.ars2014.logiccalculator.ast.BinaryOp;
import ru.ars2014.logiccalculator.ast.Identifier;
import ru.ars2014.logiccalculator.ast.UnaryOp;
import ru.ars2014.logiccalculator.interpreter.InterpreterError;

public interface Visitor {
    boolean visitIdentifier(Identifier ident) throws InterpreterError;

    boolean visitBinaryOp(BinaryOp op) throws InterpreterError;

    boolean visitUnaryOp(UnaryOp op) throws InterpreterError;
}
