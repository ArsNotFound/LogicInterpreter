package ru.ars2014.logiccalculator.interpreter;

import java.util.HashSet;
import java.util.Set;

import ru.ars2014.logiccalculator.ast.AST;
import ru.ars2014.logiccalculator.ast.BinaryOp;
import ru.ars2014.logiccalculator.ast.Identifier;
import ru.ars2014.logiccalculator.ast.UnaryOp;
import ru.ars2014.logiccalculator.lexer.LexerError;
import ru.ars2014.logiccalculator.lexer.Token;
import ru.ars2014.logiccalculator.parser.Parser;
import ru.ars2014.logiccalculator.parser.ParserError;
import ru.ars2014.logiccalculator.visitor.Visitor;

public class IdentCounter implements Visitor {
    private final Parser parser;
    private final Set<String> idents = new HashSet<>();

    public IdentCounter(Parser parser) {
        this.parser = parser;

    }

    @Override
    public boolean visitIdentifier(Identifier ident) {
        if (ident.getP().getType() == Token.Type.Const) {
            return false;
        }

        idents.add(ident.getP().getLiteral());
        return false;
    }

    @Override
    public boolean visitBinaryOp(BinaryOp op) throws InterpreterError {
        op.getLeft().accept(this);
        op.getRight().accept(this);
        return false;
    }

    @Override
    public boolean visitUnaryOp(UnaryOp op) throws InterpreterError {
        op.getExpr().accept(this);
        return false;
    }

    public Set<String> interpret() throws LexerError, ParserError, InterpreterError {
        AST ast = parser.parse();
        ast.accept(this);
        return idents;
    }
}
