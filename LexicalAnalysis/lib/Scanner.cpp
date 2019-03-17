//
// Created by lucian on 03.03.19.
//

#include <Scanner.h>

#include "Scanner.h"
#include "Tokens.h"
#include "Tracking.h"

#include <iostream>

const Token Scanner::fetchNext() {
    int type = lexer->yylex();

    string value = lexer->YYText();

    const string *tokenValuePtr = nullptr;

    auto itToExisting = discoveredTokenValues.find(value);
    if (itToExisting != discoveredTokenValues.end()) {
        tokenValuePtr = &*itToExisting;
    } else {
        tokenValuePtr = &*discoveredTokenValues.insert(value).first;
    }

    if (type == END_OF_FILE || type == BAD_CHARACTER)
        done = true;

    return Token { type, tokenValuePtr, getLine(), getCol() };
}

Scanner::~Scanner() {
    delete lexer;
}




