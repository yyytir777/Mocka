grammar Regex;

@header {
    package entityinstantiator.generator.regex;
}

regex       : expr (PIPE expr)* ;
expr        : element+ ;
element     : atom quantifier? ;
group       : LPAREN regex RPAREN ;
quantifier  : STAR
            | PLUS
            | QUESTION
            | QUANTITY
            ;

atom        : LITERAL
            | ESCAPE
            | group
            | charClass
            | DOT
            | HYTPHEN
            ;


charClass   : LBRACK CARET? classAtom+ RBRACK ;
charRange   : LITERAL HYTPHEN LITERAL;
classAtom   : ESCAPE
            | charRange
            | LITERAL
            | CARET
            ;

PIPE        : '|' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
LBRACK      : '[' ;
RBRACK      : ']' ;
CARET       : '^' ;
STAR        : '*' ;
PLUS        : '+' ;
QUESTION    : '?' ;
DOT         : '.' ;
HYTPHEN     : '-' ;
QUANTITY    : '{' [0-9]+ (',' [0-9]*)? '}' ;
LITERAL     : ~[|*+?.(){}[\]\\] ;
ESCAPE      : '\\' . ;
BLANK       : [ \t\r\n]+ -> skip ;
