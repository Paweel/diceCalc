package com.example.dicecalc.parser;

import com.example.EquationParser;
import com.example.EquationParserBaseVisitor;
import com.example.dicecalc.math.ExpressionComponent;
import com.example.dicecalc.math.operations.Add;
import com.example.dicecalc.math.operations.Multiply;
import com.example.dicecalc.math.value.MulitpleDiceValue;
import com.example.dicecalc.math.value.SimpleValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.random.RandomGenerator;

@Slf4j
@AllArgsConstructor
public class EquationParserCustomVisitor extends EquationParserBaseVisitor<ExpressionComponent> {
    final RandomGenerator random;

    @Override
    public ExpressionComponent visitStart(EquationParser.StartContext ctx) {
        return visitOperation(ctx.operation());
    }

    @Override
    public ExpressionComponent visitLiteral(EquationParser.LiteralContext ctx) {
        if (ctx.Integer() != null)
            return new SimpleValue(Long.valueOf(ctx.Integer().toString()));
        if (ctx.dice() != null)
            return visitDice(ctx.dice());
        throw new RuntimeException("operation not handled");
    }

    @Override
    public ExpressionComponent visitOperation(EquationParser.OperationContext ctx) {
        if (ctx.Add() != null)
            return new Add(visitOperation(ctx.operation(0)), visitOperation(ctx.operation(1)));
        if (ctx.Multiply() != null)
            return new Multiply(visitOperation(ctx.operation(0)), visitOperation(ctx.operation(1)));
        if (ctx.literal() != null)
            return visitLiteral(ctx.literal());
        throw new RuntimeException("operation not handled");
    }

    @Override
    public ExpressionComponent visitDice(EquationParser.DiceContext ctx) {
        return new MulitpleDiceValue(Long.valueOf(ctx.Integer(0).toString()), 1L, Long.valueOf(ctx.Integer(1).toString()), random);
    }
}