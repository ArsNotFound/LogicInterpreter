package ru.ars2014.logiccalculator.parser;

import ru.ars2014.logiccalculator.ast.AST;
import ru.ars2014.logiccalculator.ast.BinaryOp;
import ru.ars2014.logiccalculator.ast.Identifier;
import ru.ars2014.logiccalculator.ast.UnaryOp;
import ru.ars2014.logiccalculator.lexer.Lexer;
import ru.ars2014.logiccalculator.lexer.LexerError;
import ru.ars2014.logiccalculator.lexer.Token;

public class Parser {
    private final Lexer lexer;
    private Token current;

    public Parser(Lexer lexer) throws LexerError {
        this.lexer = lexer;
        this.current = getNextToken();
    }

    public Parser(Parser parser) {
        this.lexer = new Lexer(parser.lexer);
        this.current = new Token(parser.current);
    }

    private Token getNextToken() throws LexerError {
        return lexer.getNextToken();
    }

    private void eat(Token.Type tokenType) throws LexerError, ParserError {
        if (current.getType() == tokenType) {
            current = getNextToken();
        } else {
            throw new ParserError(current, tokenType);
        }
    }

    /*
    id: Parameter | Const
     */
    private Identifier identifier() throws LexerError, ParserError {
        Identifier id = new Identifier(current);
        if (current.getType() == Token.Type.Const) {
            eat(Token.Type.Const);
        } else {
            eat(Token.Type.Param);
        }

        return id;
    }

    /*
    factor: id | LParen expr RParen
     */
    private AST factor() throws LexerError, ParserError {
        if (current.getType() == Token.Type.Const || current.getType() == Token.Type.Param) {
            return identifier();
        } else {
            eat(Token.Type.LParen);
            AST node = expr();
            eat(Token.Type.RParen);
            return node;
        }
    }

    /*
    not: Not factor | factor
     */
    private AST not() throws LexerError, ParserError {
        if (current.getType() == Token.Type.Not) {
            Token token = current;
            eat(Token.Type.Not);
            return new UnaryOp(token, factor());
        }

        return factor();
    }

    /*
    conjunction: not (Conj not)*
     */
    private AST conjunction() throws LexerError, ParserError {
        AST node = not();

        while (current.getType() == Token.Type.Conj) {
            Token token = current;
            eat(Token.Type.Conj);

            node = new BinaryOp(node, token, not());
        }

        return node;
    }

    /*
    disjunction: conjunction (Disj conjunction)*
     */
    private AST disjunction() throws LexerError, ParserError {
        AST node = conjunction();

        while (current.getType() == Token.Type.Disj) {
            Token token = current;
            eat(Token.Type.Disj);

            node = new BinaryOp(node, token, conjunction());
        }

        return node;
    }

    /*
    implication: disjunction (IMPL disjunction)*
     */
    private AST implication() throws LexerError, ParserError {
        AST node = disjunction();

        while (current.getType() == Token.Type.Impl) {
            Token token = current;
            eat(Token.Type.Impl);

            node = new BinaryOp(node, token, disjunction());
        }

        return node;
    }

    /*
    equalization: implication (EQUAL implication)*
     */
    private AST equalization() throws LexerError, ParserError {
        AST node = implication();

        while (current.getType() == Token.Type.Equal) {
            Token token = current;
            eat(Token.Type.Equal);

            node = new BinaryOp(node, token, implication());
        }

        return node;
    }

    private AST expr() throws LexerError, ParserError {
        return equalization();
    }

    public AST parse() throws LexerError, ParserError {
        AST ex = expr();
        eat(Token.Type.EOF);
        return ex;
    }
}
