package com.example.dicecalc.parser;

import com.example.EquationParser;
import com.example.EquationParserBaseVisitor;
import com.example.dicecalc.math.DelayedRandomGenerator;
import com.example.dicecalc.math.ExpressionComponent;
import com.example.dicecalc.math.operations.Sum;
import com.example.dicecalc.math.operations.Product;
import com.example.dicecalc.math.value.MultipleDiceValue;
import com.example.dicecalc.math.value.SimpleValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;

@Slf4j
@AllArgsConstructor
public class EquationParserCustomVisitor extends EquationParserBaseVisitor<ExpressionComponent> {
    final DelayedRandomGenerator random;

    @Override
    public ExpressionComponent visitStart(EquationParser.StartContext ctx) {
        return visitOperation(ctx.operation());
    }

    @Override
    public ExpressionComponent visitLiteral(EquationParser.LiteralContext ctx) {
        if (ctx.Minus() != null)
            return new SimpleValue(-Long.parseLong(ctx.Integer().toString()));
        if (ctx.Integer() != null)
            return new SimpleValue(Long.valueOf(ctx.Integer().toString()));
        if (ctx.dice() != null)
            return visitDice(ctx.dice());
        throw new RuntimeException("operation not handled");
    }

    @Override
    public ExpressionComponent visitOperation(EquationParser.OperationContext ctx) {
        if (ctx.LeftParenthesis() != null && ctx.RightParenthesis() != null)
            return visitOperation(ctx.operation(0));
        if (ctx.Add() != null)
            return new Sum(visitOperation(ctx.operation(0)), visitOperation(ctx.operation(1)));
        if (ctx.Multiply() != null)
            return new Product(visitOperation(ctx.operation(0)), visitOperation(ctx.operation(1)));
        if (ctx.literal() != null)
            return visitLiteral(ctx.literal());
        throw new RuntimeException("operation not handled");
    }

    @Override
    public ExpressionComponent visitDice(EquationParser.DiceContext ctx) {
        final Long amount = Long.valueOf(ctx.Integer(0).toString());
        final Long sides = Long.valueOf(ctx.Integer(1).toString());
        if (amount <= 0L) {
            final Token symbol = ctx.Integer(0).getSymbol();
            CustomErrorListener.INSTANCE.syntaxError(null, symbol.getText(), symbol.getLine(), symbol.getCharPositionInLine(), "Dice amount must be more than 0", null);
        }
        if (sides <= 0L) {
            final Token symbol = ctx.Integer(1).getSymbol();
            CustomErrorListener.INSTANCE.syntaxError(null, symbol.getText(), symbol.getLine(), symbol.getCharPositionInLine(), "Dice sides number must be more than 0", null);
        }
        return new MultipleDiceValue(amount, 1L, sides, random);
    }
}