package com.example.dicecalc.parser;

import com.example.EquationParser;
import com.example.EquationParserBaseVisitor;
import com.example.dicecalc.math.ExpressionComponent;
import com.example.dicecalc.math.operations.Add;
import com.example.dicecalc.math.operations.Multiply;
import com.example.dicecalc.math.value.SimpleValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EquationParserCustomVisitor extends EquationParserBaseVisitor<ExpressionComponent> {
    @Override
    public ExpressionComponent visitStart(EquationParser.StartContext ctx) {
        return visitOperation(ctx.operation());
    }

    @Override
    public ExpressionComponent visitLiteral(EquationParser.LiteralContext ctx) {
        return new SimpleValue(Long.valueOf(ctx.Integer().toString()));
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

}