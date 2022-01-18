package ru.ars2014.logiccalculator.lexer;

public class Lexer {
    private final String text;
    private int pos = 0;
    private char current;

    public Lexer(String text) {
        this.text = text;
        if (!this.text.isEmpty()) {
            this.current = text.charAt(pos);
        } else {
            this.current = 0;
        }
    }

    public Lexer(Lexer lexer) {
        this.text = lexer.text;
        this.pos = lexer.pos;
        this.current = lexer.current;
    }

    private void advance() {
        pos++;
        if (pos >= text.length())
            current = 0;
        else
            current = text.charAt(pos);
    }

    private char peek(int c) {
        int peekPos = pos + c;
        if (peekPos >= text.length())
            return 0;
        else
            return text.charAt(peekPos);
    }

    private void skipWhitespace() {
        while (current != 0 && Character.isWhitespace(current))
            advance();
    }

    private Token parameter() {
        StringBuilder value = new StringBuilder();

        int tPos = pos;

        while (current != 0 && Character.isLetterOrDigit(current)) {
            value.append(current);
            advance();
        }

        return new Token(value.toString(), Token.Type.Param, tPos);
    }

    private Token constant() throws LexerError {
        if (Character.isDigit(peek(1)))
            throw new LexerError(peek(1), pos + 1);

        Token t;
        if (current == '1' || current == '0')
            t = new Token("" + current, Token.Type.Const, pos);
        else
            throw new LexerError(current, pos);
        advance();

        return t;
    }

    public Token getNextToken() throws LexerError {
        while (current != 0) {
            if (Character.isWhitespace(current)) {
                skipWhitespace();
                continue;
            }

            if (Character.isLetter(current))
                return parameter();

            if (current == '1' || current == '0')
                return constant();

            if (current == '-' && peek(1) == '>') {
                Token tok = new Token("->", Token.Type.Impl, pos);
                advance();
                advance();
                return tok;
            }

            if (current == '<' && peek(1) == '=' && peek(2) == '>') {
                Token tok = new Token("<=>", Token.Type.Equal, pos);
                advance();
                advance();
                advance();
                return tok;
            }

            Token.Type t;
            switch (current) {
                case '!':
                    t = Token.Type.Not;
                    break;
                case '*':
                    t = Token.Type.Conj;
                    break;
                case '+':
                    t = Token.Type.Disj;
                    break;
                case '(':
                    t = Token.Type.LParen;
                    break;
                case ')':
                    t = Token.Type.RParen;
                    break;
                default:
                    throw new LexerError(current, pos);
            }

            Token tok = new Token("" + current, t, pos);
            advance();
            return tok;
        }

        return new Token("", Token.Type.EOF, pos);
    }
}
