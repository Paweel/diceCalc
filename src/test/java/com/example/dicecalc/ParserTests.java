package com.example.dicecalc;

import com.example.EquationLexer;
import com.example.EquationParser;
import com.example.dicecalc.math.ExpressionComponent;
import com.example.dicecalc.parser.EquationParserCustomVisitor;
import org.antlr.v4.runtime.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.stream.Stream;

@WebFluxTest
public class ParserTests {

    @ParameterizedTest
    @MethodSource({"addTestParameters", "multiplyTestParameters", "precedenceTestParameters"})
    void parseAndEvaluateTree(String givenString, Long expectedResult) {
        final EquationLexer lexer = new EquationLexer(new ANTLRInputStream((givenString)));
        final EquationParser parser = new EquationParser(new CommonTokenStream(lexer));
//        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        final EquationParserCustomVisitor customVisitor = new EquationParserCustomVisitor();
        final ExpressionComponent expressionComponent = customVisitor.visitStart(parser.start());
        Assertions.assertEquals(expectedResult, expressionComponent.evaluate());
    }

    public static Stream<Arguments> addTestParameters() {
        return Stream.of(
                Arguments.arguments("3 + 5", 8L),
                Arguments.arguments("3 + 5 + 11", 19L),
                Arguments.arguments("3 + 5 + -11", -3L)
        );
    }
    public static Stream<Arguments> multiplyTestParameters() {
        return Stream.of(
                Arguments.arguments("3 * 5", 15L),
                Arguments.arguments("7 * 4 * 2", 56L)
        );
    }
    public static Stream<Arguments> precedenceTestParameters() {
        return Stream.of(
                Arguments.arguments("3 * 5 + 3", 18L),
                Arguments.arguments("3 + 5 * 3", 18L)
        );
    }
}
