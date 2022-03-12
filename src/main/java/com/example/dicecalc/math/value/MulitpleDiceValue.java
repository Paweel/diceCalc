package com.example.dicecalc.math.value;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;

import java.util.random.RandomGenerator;

@RequiredArgsConstructor
public class MulitpleDiceValue implements ExpressionComponent {
    private final Long amount;
    private final Long min;
    private final Long max;
    private final RandomGenerator random;


    @Override
    public Long evaluate() {
        Long sum = 0L;
        for (int i = 0; i < amount; i++) {
            sum += random.nextLong(min, max + 1);
        }
        return sum;
    }
}
