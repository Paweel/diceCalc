package com.example.dicecalc.webapi;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DiceRollerController {

    @GetMapping("/parse")
    public Mono<String> parse(@RequestParam String equation) {

        return Mono.fromSupplier(() -> equation);
    }
}
