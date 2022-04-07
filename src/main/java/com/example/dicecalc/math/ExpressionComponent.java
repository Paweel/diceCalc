package com.example.dicecalc.math;

import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

public interface ExpressionComponent {
    @NotNull Mono<Long> evaluate();
}
