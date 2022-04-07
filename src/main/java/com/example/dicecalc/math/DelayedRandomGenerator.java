package com.example.dicecalc.math;

import reactor.core.publisher.Flux;

public interface DelayedRandomGenerator {
    Flux<Long> longFlux(Long min, Long max);
}
