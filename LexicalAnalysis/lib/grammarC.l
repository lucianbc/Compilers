D               [0-9]
NZ              [1-9]
L               [a-zA-Z_]
HEX             [a-fA-F0-9]
P               ([Pp][+-]?{D}+)
ENR             ([Ee][+-]?{D}+)
IS              ((u|U)|(u|U)?(l|L|ll|LL)|(l|L|ll|LL)(u|U))
FS              (f|F|l|L)

%x              C_COMMENT
%option         c++
%option noyywrap


%{
#include "Tokens.h"
#include "Tracking.h"
%}

%%
"/*"            { count(yytext); BEGIN(C_COMMENT); }
<C_COMMENT>"*/" { count(yytext); BEGIN(INITIAL); }
<C_COMMENT>.    { count(yytext); }
<C_COMMENT>\n   { count(yytext); }
"//".*          { count(yytext); /* INLINE COMMENT - DO NOTHING */ }


"auto"          { count(yytext); return(AUTO); }
"_Bool"         { count(yytext); return(BOOL); }
"break"         { count(yytext); return(BREAK); }
"case"          { count(yytext); return(CASE); }
"char"          { count(yytext); return(CHAR); }
"_Complex"      { count(yytext); return(COMPLEX); }
"const"         { count(yytext); return(CONST); }
"continue"      { count(yytext); return(CONTINUE); }
"default"       { count(yytext); return(DEFAULT); }
"do"            { count(yytext); return(DO); }
"double"        { count(yytext); return(DOUBLE); }
"else"          { count(yytext); return(ELSE); }
"enum"          { count(yytext); return(ENUM); }
"extern"        { count(yytext); return(EXTERN); }
"float"         { count(yytext); return(FLOAT); }
"for"           { count(yytext); return(FOR); }
"goto"          { count(yytext); return(GOTO); }
"_Imaginary"    { count(yytext); return(IMAGINARY); }
"if"            { count(yytext); return(IF); }
"int"           { count(yytext); return(INT); }
"inline"        { count(yytext); return(INLINE); }
"long"          { count(yytext); return(LONG); }
"register"      { count(yytext); return(REGISTER); }
"return"        { count(yytext); return(RETURN); }
"restrict"      { count(yytext); return(RESTRICT); }
"short"         { count(yytext); return(SHORT); }
"signed"        { count(yytext); return(SIGNED); }
"sizeof"        { count(yytext); return(SIZEOF); }
"static"        { count(yytext); return(STATIC); }
"struct"        { count(yytext); return(STRUCT); }
"switch"        { count(yytext); return(SWITCH); }
"typedef"       { count(yytext); return(TYPEDEF); }
"union"         { count(yytext); return(UNION); }
"unsigned"      { count(yytext); return(UNSIGNED); }
"void"          { count(yytext); return(VOID); }
"volatile"      { count(yytext); return(VOLATILE); }
"while"         { count(yytext); return(WHILE); }
"#include"      { count(yytext); return(INCLUDE); }

[a-zA-Z]([a-zA-Z0-9])*   { count(yytext); return(IDENTIFIER); }

{NZ}{D}*{IS}?       { count(yytext); return(CONSTANT); }
0[0-7]*{IS}?        { count(yytext); return(CONSTANT); }
(0b|0B)(0|1)*{IS}?  { count(yytext); return(CONSTANT); }
0[xX]{HEX}+         { count(yytext); return(CONSTANT); }

{D}+{ENR}{FS}?              { count(yytext); return(CONSTANT); }
{D}*"."{D}+{ENR}?{FS}?      { count(yytext); return(CONSTANT); }
{D}+"."{D}*{ENR}?{FS}?      { count(yytext); return(CONSTANT); }
0[xX]{HEX}+{P}{FS}?         { count(yytext); return(CONSTANT); }


0[xX]{HEX}*"."{HEX}+{P}{FS}?    { count(yytext); return(CONSTANT); }
0[xX]{HEX}+"."{HEX}*{P}{FS}?    { count(yytext); return(CONSTANT); }

\"(\\(.|\r?\n)|[^\\"\n])*\"               { count(yytext); return(STRING_LITERAL); }
\'(.|[\t\v\n\f]|\\t|\\v|\\n|\\f)\'  { count(yytext); return(CHAR_LITERAL); }

"..."           { count(yytext); return(ELLIPSIS); }
">>="           { count(yytext); return(RIGHT_ASSIGN); }
"<<="           { count(yytext); return(LEFT_ASSIGN); }
"+="            { count(yytext); return(ADD_ASSIGN); }
"-="            { count(yytext); return(SUB_ASSIGN); }
"*="            { count(yytext); return(MUL_ASSIGN); }
"/="            { count(yytext); return(DIV_ASSIGN); }
"%="            { count(yytext); return(MOD_ASSIGN); }
"&="            { count(yytext); return(AND_ASSIGN); }
"^="            { count(yytext); return(XOR_ASSIGN); }
"|="            { count(yytext); return(OR_ASSIGN); }
">>"            { count(yytext); return(RIGHT_OP); }
"<<"            { count(yytext); return(LEFT_OP); }
"++"            { count(yytext); return(INC_OP); }
"--"            { count(yytext); return(DEC_OP); }
"->"            { count(yytext); return(PTR_OP); }
"&&"            { count(yytext); return(AND_OP); }
"||"            { count(yytext); return(OR_OP); }
"<="            { count(yytext); return(LE_OP); }
">="            { count(yytext); return(GE_OP); }
"=="            { count(yytext); return(EQ_OP); }
"!="            { count(yytext); return(NE_OP); }
";"             { count(yytext); return(SEMICOLON); }
"{"             { count(yytext); return(CURLY_LEFT); }
"}"             { count(yytext); return(CURLY_RIGHT); }
","             { count(yytext); return(COMMA); }
":"             { count(yytext); return(COLON); }
"="             { count(yytext); return(EQUALS); }
"("             { count(yytext); return(ROUND_LEFT); }
")"             { count(yytext); return(ROUND_RIGHT); }
"["             { count(yytext); return(SQUARE_LEFT); }
"]"             { count(yytext); return(SQUARE_RIGHT); }
"."             { count(yytext); return(DOT); }
"&"             { count(yytext); return(AND); }
"!"             { count(yytext); return(EXCLAMATION); }
"~"             { count(yytext); return(TILDE); }
"-"             { count(yytext); return(MINUS); }
"+"             { count(yytext); return(PLUS); }
"*"             { count(yytext); return(STAR); }
"/"             { count(yytext); return(SLASH); }
"%"             { count(yytext); return(PERCENT); }
"<"             { count(yytext); return(ARROW_LEFT); }
">"             { count(yytext); return(ARROW_RIGHT); }
"^"             { count(yytext); return(ARROW_UP); }
"|"             { count(yytext); return(PIPE); }
"?"             { count(yytext); return(QUESTION); }

[ \t\v\r\n\f]   { count(yytext); /* IGNORE WHITE SPACE */ }
.               { count(yytext); return(BAD_CHARACTER); }

%%