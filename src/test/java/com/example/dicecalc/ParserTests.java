package com.example.dicecalc;

import com.example.EquationLexer;
import com.example.EquationParser;
import com.example.dicecalc.math.DelayedRandomGenerator;
import com.example.dicecalc.math.DelayedRandomGeneratorSimple;
import com.example.dicecalc.math.ExpressionComponent;
import com.example.dicecalc.parser.CustomErrorListener;
import com.example.dicecalc.parser.EquationParserCustomVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@WebFluxTest
public class ParserTests {

    @Mock
    public DelayedRandomGenerator random;

    @ParameterizedTest
    @MethodSource({"addTestParameters", "multiplyTestParameters", "precedenceTestParameters", "diceTestParameters", "parenthesesTestParameters"})
    void parseAndEvaluateTree(String givenString, Long expectedResult) {
        final EquationLexer lexer = new EquationLexer(new ANTLRInputStream((givenString)));
        final EquationParser parser = new EquationParser(new CommonTokenStream(lexer));

        //k6 return 4
        when(random.longFlux(1L, 7L)).thenReturn(Flux.generate(sink -> sink.next(4L)));
        //k10 return 5
        when(random.longFlux(1L, 11L)).thenReturn(Flux.generate(sink -> sink.next(5L)));
        final EquationParserCustomVisitor customVisitor = new EquationParserCustomVisitor(random);
        final ExpressionComponent expressionComponent = customVisitor.visitStart(parser.start());
        Assertions.assertEquals(expectedResult, expressionComponent.evaluate().block());
    }
    @ParameterizedTest
    @MethodSource({"errorsTestParameters"})
    void parseAndEvaluateTree(String givenString, String expectedResult) {
        final EquationLexer lexer = new EquationLexer(new ANTLRInputStream((givenString)));
        lexer.addErrorListener(CustomErrorListener.INSTANCE);
        final EquationParser parser = new EquationParser(new CommonTokenStream(lexer));
        parser.addErrorListener(CustomErrorListener.INSTANCE);
        final EquationParserCustomVisitor customVisitor = new EquationParserCustomVisitor(random);
        final ParseCancellationException exception = assertThrows(ParseCancellationException.class, () -> customVisitor.visitStart(parser.start()));
        Assertions.assertEquals(expectedResult, exception.getMessage());
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

    public static Stream<Arguments> errorsTestParameters() {
        return Stream.of(
                Arguments.arguments("(2 + 6) * ", "line: 1, char position: 10, no viable alternative at input '<EOF>'"),
                Arguments.arguments("4 * 2 + 6)", "line: 1, char position: 9, extraneous input ')' expecting {<EOF>, '+', '*'}"),
                Arguments.arguments("4 + (2 + 6", "line: 1, char position: 10, missing ')' at '<EOF>'"),
                Arguments.arguments("(3 + 5) * 2k", "line: 1, char position: 12, missing Integer at '<EOF>'"),
                Arguments.arguments("(3 + 5) * k10", "line: 1, char position: 10, no viable alternative at input 'k'"),
                Arguments.arguments("(3 * 5) ** 1k10", "line: 1, char position: 9, no viable alternative at input '*'"),
                Arguments.arguments("1k0 * (3 * 5)", "line: 1, char position: 2, Dice sides number must be more than 0"),
                Arguments.arguments("0k2 * (3 * 5)", "line: 1, char position: 0, Dice amount must be more than 0"),
                Arguments.arguments("1k-1 * (3 * 5)", "line: 1, char position: 2, extraneous input '-' expecting Integer"),
                Arguments.arguments("-1k7 * (3 * 5)", "line: 1, char position: 2, mismatched input 'k' expecting {<EOF>, '+', '*'}"),
                Arguments.arguments("1k10 * (3 * a)", "line: 1, char position: 12, token recognition error at: 'a'")
        );
    }

}
