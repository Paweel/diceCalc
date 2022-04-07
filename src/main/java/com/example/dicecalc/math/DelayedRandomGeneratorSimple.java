package com.example.dicecalc.math;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.random.RandomGenerator;

@AllArgsConstructor
public class DelayedRandomGeneratorSimple implements DelayedRandomGenerator {
    final RandomGenerator random;

    @Override
    public Flux<Long> longFlux(Long min, Long max) {
        return Flux.generate(sink -> sink.next(random.nextLong(min, max)));
    }
}
