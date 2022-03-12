package com.example.dicecalc.math.value;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleValue implements ExpressionComponent {
    private final Long value;

    @Override
    public Long evaluate() {
        return value;
    }
}
