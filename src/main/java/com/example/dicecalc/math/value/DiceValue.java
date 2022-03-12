package com.example.dicecalc.math.value;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class DiceValue implements ExpressionComponent {
    private final Long min;
    private final Long max;
    private final Random random;

    @Override
    public Long evaluate() {
        return random.nextLong(min, max + 1);
    }
}
