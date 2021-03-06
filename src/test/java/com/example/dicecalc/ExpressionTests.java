package com.example.dicecalc;

import com.example.dicecalc.math.DelayedRandomGeneratorSimple;
import com.example.dicecalc.math.operations.Sum;
import com.example.dicecalc.math.operations.Product;
import com.example.dicecalc.math.value.MultipleDiceValue;
import com.example.dicecalc.math.value.SimpleValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.Random;
import java.util.stream.Stream;

@WebFluxTest
public class ExpressionTests {

    @ParameterizedTest
    @MethodSource("addParameters")
    void addTest(Long givenA, Long givenB, Long expected) {
        Assertions.assertEquals(expected, new Sum(new SimpleValue(givenA), new SimpleValue(givenB)).evaluate().block());
    }

    public static Stream<Arguments> addParameters() {
        return Stream.of(
                Arguments.arguments(5L, 4L, 9L),
                Arguments.arguments(5L, -4L, 1L),
                Arguments.arguments(-321L, 424L, 103L),
                Arguments.arguments(-5L, -4L, -9L)
        );
    }

    @ParameterizedTest
    @MethodSource("multiplyParameters")
    void multiplyTest(Long givenA, Long givenB, Long expected) {
        Assertions.assertEquals(expected, new Product(new SimpleValue(givenA), new SimpleValue(givenB)).evaluate().block());
    }

    public static Stream<Arguments> multiplyParameters() {
        return Stream.of(
                Arguments.arguments(5L, 4L, 20L),
                Arguments.arguments(5L, -4L, -20L),
                Arguments.arguments(-321L, 424L, -136104L),
                Arguments.arguments(-5L, -4L, 20L),
                Arguments.arguments(-5L, -0L, 0L)
        );
    }

    @Test
    void diceValueTest() {
        Long givenDiceAmount = 1L;
        Long givenMin = 5L;
        Long givenMax = 16L;
        final Long evaluated = new MultipleDiceValue(givenDiceAmount, givenMin, givenMax, new DelayedRandomGeneratorSimple(new Random(1))).evaluate().block();
        Assertions.assertTrue(evaluated >= givenMin);
        Assertions.assertTrue(evaluated <= givenMin);
    }

}
