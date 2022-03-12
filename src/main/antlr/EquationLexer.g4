lexer grammar EquationLexer;

Integer
    : '-'?[0-9]+;

Add : '+';
Multiply : '*';

Whitespace : [ \t\r\n]+ -> skip ;
