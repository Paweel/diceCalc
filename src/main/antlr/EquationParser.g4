parser grammar EquationParser;
options{tokenVocab=EquationLexer;}

start
    : operation;


literal
	:	Integer
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
