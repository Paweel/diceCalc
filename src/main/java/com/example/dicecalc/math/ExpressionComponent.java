package com.example.dicecalc.math;

import javax.validation.constraints.NotNull;

public interface ExpressionComponent {
    @NotNull Long evaluate();
}
