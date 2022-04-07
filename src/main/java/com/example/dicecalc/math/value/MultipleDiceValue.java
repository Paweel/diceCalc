package com.example.dicecalc.math.value;

import com.example.dicecalc.math.DelayedRandomGenerator;
import com.example.dicecalc.math.ExpressionComponent;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class MultipleDiceValue implements ExpressionComponent {
    private final Long amount;
    private final Long min;
    private final Long max;
    private final DelayedRandomGenerator random;


    @Override
    public Mono<Long> evaluate() {
        return random.longFlux(min, max + 1).take(amount).reduce(Long::sum);
    }
}
