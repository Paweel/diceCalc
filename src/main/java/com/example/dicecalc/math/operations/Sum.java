package com.example.dicecalc.math.operations;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class Sum implements ExpressionComponent {
    private final ExpressionComponent a;
    private final ExpressionComponent b;

    @Override
    public @NotNull Mono<Long> evaluate() {
        return Mono.zip(a.evaluate(), b.evaluate(), Long::sum);
    }
}
