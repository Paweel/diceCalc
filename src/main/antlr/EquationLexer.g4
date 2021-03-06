lexer grammar EquationLexer;

Integer
    : [0-9]+;
Minus : '-';
Add : '+';
Multiply : '*';
DiceSymbol
    : 'K'
    | 'k'
    | 'D'
    | 'd';
Whitespace : [ \t\r\n]+ -> skip ;
LeftParenthesis : '(' ;
RightParenthesis : ')';
