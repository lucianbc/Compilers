#include <iostream>
#include <FlexLexer.h>
#include <fstream>

using namespace std;

int main() {
    string fileName = "./test/aici.txt";

    ifstream in(fileName);

    FlexLexer *lexer = new yyFlexLexer(in, cout);

    int c = lexer->yylex();

    while (c != 0) {
        cout<<lexer->YYText()<<endl;
        c = lexer->yylex();
    }
}
