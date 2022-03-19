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
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@WebFluxTest
public class ParserTests {

    @Mock
    public RandomGenerator random;

    @ParameterizedTest
    @MethodSource({"addTestParameters", "multiplyTestParameters", "precedenceTestParameters", "diceTestParameters", "parenthesesTestParameters"})
    void parseAndEvaluateTree(String givenString, Long expectedResult) {
        final EquationLexer lexer = new EquationLexer(new ANTLRInputStream((givenString)));
        final EquationParser parser = new EquationParser(new CommonTokenStream(lexer));

        //k6 return 4
        when(random.nextLong(1, 7)).thenReturn(4L);
        //k10 return 5
        when(random.nextLong(1, 11)).thenReturn(5L);
        final EquationParserCustomVisitor customVisitor = new EquationParserCustomVisitor(random);
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

    public static Stream<Arguments> diceTestParameters() {
        return Stream.of(
                Arguments.arguments("3k6", 12L),
                Arguments.arguments("3 + 5 * 2k6", 43L),
                Arguments.arguments("3 + 5 * 1k10", 28L)
        );
    }

    public static Stream<Arguments> parenthesesTestParameters() {
        return Stream.of(
                Arguments.arguments("(2 + 6) * 4", 32L),
                Arguments.arguments("4 * (2 + 6)", 32L),
                Arguments.arguments("4 + (2 + 6)", 12L),
                Arguments.arguments("(3 + 5) * 2k6", 64L),
                Arguments.arguments("(3 + 5) * 1k10", 40L),
                Arguments.arguments("(3 * 5) * 1k10", 75L),
                Arguments.arguments("1k10 * (3 * 5)", 75L),
                Arguments.arguments("1k10 * ((3 + 5) * 5)", 200L)
        );
    }

}
