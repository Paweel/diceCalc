parser grammar EquationParser;
options{tokenVocab=EquationLexer;}

start
    : operation;

dice
    : Integer DiceSymbol Integer
    ;
literal
	: Integer
	| Minus Integer
	| dice
	;
operation
    : LeftParenthesis operation RightParenthesis
    | operation Multiply operation
    | operation Add operation
    | literal;
//
//addOperation
//     : operation Add operation;
//multiplyOperation
//     : operation Multiply operation;
//
