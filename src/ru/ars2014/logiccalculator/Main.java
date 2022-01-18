package ru.ars2014.logiccalculator;

import ru.ars2014.logiccalculator.interpreter.Interpreter;
import ru.ars2014.logiccalculator.lexer.Lexer;
import ru.ars2014.logiccalculator.parser.Parser;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final PrintStream out = System.out;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            out.print("logic> ");
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                return;
            }

            try {
                Lexer lexer = new Lexer(line);
                Parser parser = new Parser(lexer);
                Interpreter interpreter = new Interpreter(parser);


                List<Boolean> res = interpreter.interpret();
                for (boolean r : res) {
                    out.println(r);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
