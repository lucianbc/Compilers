//
// Created by lucian on 03.03.19.
//
#include "Scanner.h"
#include "Tokens.h"

string typeToString(int type);

ostream& operator<<(ostream &out, const Token & t) {
    out<<typeToString(t.type)<<" "<<*t.value;
    return out;
}


string typeToString(int type) {
    switch (type) {
        case AUTO:return "AUTO";
        case BOOL:return "BOOL";
        case BREAK:return "BREAK";
        case CASE:return "CASE";
        case CHAR:return "CHAR";
        case COMPLEX:return "COMPLEX";
        case CONST:return "CONST";
        case CONTINUE:return "CONTINUE";
        case DEFAULT:return "DEFAULT";
        case DO:return "DO";
        case DOUBLE:return "DOUBLE";
        case ELSE:return "ELSE";
        case ENUM:return "ENUM";
        case EXTERN:return "EXTERN";
        case FLOAT:return "FLOAT";
        case FOR:return "FOR";
        case GOTO:return "GOTO";
        case IMAGINARY:return "IMAGINARY";
        case IF:return "IF";
        case INT:return "INT";
        case INLINE:return "INLINE";
        case LONG:return "LONG";
        case REGISTER:return "REGISTER";
        case RETURN:return "RETURN";
        case RESTRICT:return "RESTRICT";
        case SHORT:return "SHORT";
        case SIGNED:return "SIGNED";
        case SIZEOF:return "SIZEOF";
        case STATIC:return "STATIC";
        case STRUCT:return "STRUCT";
        case SWITCH:return "SWITCH";
        case TYPEDEF:return "TYPEDEF";
        case UNION:return "UNION";
        case UNSIGNED:return "UNSIGNED";
        case VOID:return "VOID";
        case VOLATILE:return "VOLATILE";
        case WHILE:return "WHILE";
        case IDENTIFIER:return "IDENTIFIER";
        case CONSTANT:return "CONSTANT";
        case STRING_LITERAL:return "STRING_LITERAL";
        case ELLIPSIS:return "ELLIPSIS";
        case RIGHT_ASSIGN:return "RIGHT_ASSIGN";
        case LEFT_ASSIGN:return "LEFT_ASSIGN";
        case ADD_ASSIGN:return "ADD_ASSIGN";
        case SUB_ASSIGN:return "SUB_ASSIGN";
        case MUL_ASSIGN:return "MUL_ASSIGN";
        case DIV_ASSIGN:return "DIV_ASSIGN";
        case MOD_ASSIGN:return "MOD_ASSIGN";
        case AND_ASSIGN:return "AND_ASSIGN";
        case XOR_ASSIGN:return "XOR_ASSIGN";
        case OR_ASSIGN:return "OR_ASSIGN";
        case RIGHT_OP:return "RIGHT_OP";
        case LEFT_OP:return "LEFT_OP";
        case INC_OP:return "INC_OP";
        case DEC_OP:return "DEC_OP";
        case PTR_OP:return "PTR_OP";
        case AND_OP:return "AND_OP";
        case OR_OP:return "OR_OP";
        case LE_OP:return "LE_OP";
        case GE_OP:return "GE_OP";
        case EQ_OP:return "EQ_OP";
        case NE_OP:return "NE_OP";
        case SEMICOLON:return "SEMICOLON";
        case CURLY_LEFT:return "CURLY_LEFT";
        case CURLY_RIGHT:return "CURLY_RIGHT";
        case COMMA:return "COMMA";
        case COLON:return "COLON";
        case EQUALS:return "EQUALS";
        case ROUND_LEFT:return "ROUND_LEFT";
        case ROUND_RIGHT:return "ROUND_RIGHT";
        case SQUARE_LEFT:return "SQUARE_LEFT";
        case SQUARE_RIGHT:return "SQUARE_RIGHT";
        case DOT:return "DOT";
        case AND:return "AND";
        case EXCLAMATION:return "EXCLAMATION";
        case MINUS:return "MINUS";
        case TILDE:return "TILDE";
        case PLUS:return "PLUS";
        case STAR:return "STAR";
        case PERCENT:return "PERCENT";
        case ARROW_LEFT:return "ARROW_LEFT";
        case ARROW_RIGHT:return "ARROW_RIGHT";
        case ARROW_UP:return "ARROW_UP";
        case PIPE:return "PIPE";
        case QUESTION:return "QUESTION";
        case CHAR_LITERAL:return "CHAR_LITERAL";
        case SLASH:return "SLASH";
        case END_OF_FILE:return "END_OF_FILE";
        case BAD_CHARACTER:return "BAD_CHARACTER";
        case INCLUDE: return "INCLUDE";
        default:break;
    }
    return "UNDEFINED_TOKEN_NUMBER";
}