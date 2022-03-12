parser grammar EquationParser;
options{tokenVocab=EquationLexer;}

start
    : operation;

dice
    : Integer DiceSymbol Integer
    ;
literal
	: Integer
	| dice
	;

operation
    : operation Multiply operation
    | operation Add operation
    | literal;
//
//addOperation
//     : operation Add operation;
//multiplyOperation
//     : operation Multiply operation;
//
