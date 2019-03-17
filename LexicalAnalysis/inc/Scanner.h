//
// Created by lucian on 03.03.19.
//

#ifndef LEXICALANALYSIS_SCANNER_H
#define LEXICALANALYSIS_SCANNER_H

#include <unordered_set>
#include <list>
#include <FlexLexer.h>
#include <iostream>
#include "Tokens.h"

using namespace std;

struct Token {
    int type;
    const string* value;
    int line;
    int col;
    friend ostream& operator<<(ostream &out, const Token & t);

    bool isError() const { return type == BAD_CHARACTER; }
};

class TokenIterator;

class Scanner {
private:
    unordered_set<string> discoveredTokenValues;
    FlexLexer* lexer;
    bool done = false;
public:
    explicit Scanner(istream& in) :
        discoveredTokenValues(),
        lexer(new yyFlexLexer(in, cout))
    {};

    const Token fetchNext();

    bool isDone() {
        return done;
    }

    ~Scanner();
};



#endif //LEXICALANALYSIS_SCANNER_H
