package ru.ars2014.logiccalculator.ast;

import ru.ars2014.logiccalculator.interpreter.InterpreterError;
import ru.ars2014.logiccalculator.visitor.Visitor;

public abstract class AST {
    public abstract boolean accept(Visitor visitor) throws InterpreterError;
}
