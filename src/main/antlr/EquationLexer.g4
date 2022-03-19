lexer grammar EquationLexer;

Integer
    : '-'?[0-9]+;

Add : '+';
Multiply : '*';
DiceSymbol
    : 'K'
    | 'k';
Whitespace : [ \t\r\n]+ -> skip ;
LeftParenthesis : '(' ;
RightParenthesis : ')';
