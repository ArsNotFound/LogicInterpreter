package ru.ars2014.logiccalculator.interpreter;

import ru.ars2014.logiccalculator.ast.AST;
import ru.ars2014.logiccalculator.ast.BinaryOp;
import ru.ars2014.logiccalculator.ast.Identifier;
import ru.ars2014.logiccalculator.ast.UnaryOp;
import ru.ars2014.logiccalculator.lexer.LexerError;
import ru.ars2014.logiccalculator.lexer.Token;
import ru.ars2014.logiccalculator.parser.Parser;
import ru.ars2014.logiccalculator.parser.ParserError;
import ru.ars2014.logiccalculator.visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Interpreter implements Visitor {
    private final Parser parser;
    private final List<String> idents;
    private long currentLine = 0;

    public Interpreter(Parser parser) throws LexerError, ParserError, InterpreterError {
        IdentCounter identCounter = new IdentCounter(new Parser(parser));
        this.idents = new ArrayList<>(identCounter.interpret());
        this.idents.sort(String.CASE_INSENSITIVE_ORDER);
        Collections.reverse(idents);

        this.parser = parser;
    }

    @Override
    public boolean visitIdentifier(Identifier ident) {
        if (ident.getP().getType() == Token.Type.Const) {
            return ident.getP().getLiteral().equals("1");
        }

        int index = idents.indexOf(ident.getP().getLiteral());
        return ((currentLine >> (index)) & 0x1) == 1;
    }

    @Override
    public boolean visitBinaryOp(BinaryOp op) throws InterpreterError {
        boolean left = op.getLeft().accept(this);
        boolean right = op.getRight().accept(this);
        Token.Type tt = op.getOp().getType();

        boolean res;
        switch (tt) {
            case Conj:
                res = left && right;
                break;
            case Disj:
                res = left || right;
                break;
            case Impl:
                res = !left || right;
                break;
            case Equal:
                res = (left == right);
                break;
            default:
                throw new InterpreterError(op.getOp());
        }

        return res;
    }

    @Override
    public boolean visitUnaryOp(UnaryOp op) throws InterpreterError {
        boolean expr = op.getExpr().accept(this);
        Token.Type tt = op.getOp().getType();

        boolean res;
        if (tt == Token.Type.Not) {
            res = !expr;
        } else {
            throw new InterpreterError(op.getOp());
        }

        return res;
    }

    public List<Boolean> interpret() throws LexerError, ParserError, InterpreterError {
        AST tree = parser.parse();
        List<Boolean> truthTable = new ArrayList<>();
        for (currentLine = 0; currentLine < Math.pow(2, idents.size()); currentLine++) {
            truthTable.add(tree.accept(this));
        }
        return truthTable;
    }

}
