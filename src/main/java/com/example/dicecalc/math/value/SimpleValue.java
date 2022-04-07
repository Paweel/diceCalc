package com.example.dicecalc.math.value;

import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SimpleValue implements ExpressionComponent {
    private final Long value;

    @Override
    public Mono<Long> evaluate() {
        return Mono.just(value);
    }
}
