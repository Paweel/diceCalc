package com.example.dicecalc.math.operations;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class Multiply implements ExpressionComponent {
    private final ExpressionComponent a;
    private final ExpressionComponent b;

    @Override
    public @NotNull Long evaluate() {
        return a.evaluate() * b.evaluate();
    }
}
