#include <iostream>
#include <fstream>
#include "Scanner.h"

using namespace std;

void produceOutput(Scanner & scanner, ostream &out);
string getFileName(int argc, char* argv[]);
string getFileName();


int main(int argc, char* argv[]) {

    string fileName = getFileName(argc, argv);
//    string fileName = getFileName();

    ifstream in(fileName);

    if (!in.is_open()) {
        cout<<"Fisierul nu a fost gasit!";
        return -1;
    }

    Scanner scanner = Scanner(in);

    produceOutput(scanner, cout);

    in.close();

    return 0;
}

void produceOutput(Scanner & scanner, ostream &out) {
    Token currentToken = scanner.fetchNext();

    while (!scanner.isDone()) {
        out<<currentToken.line<<" "<<currentToken.col<<": "<<currentToken<<endl;
        currentToken = scanner.fetchNext();
    }

    if (currentToken.isError()) {
        out<<"Eroare la linia "<<currentToken.line<<", coloana "<<currentToken.col<<endl;
    }
}

string getFileName(int argc, char* argv[]) {
    if (argc != 2) {
        cout<<"Numar gresit de argumente. Introduceti calea catre un fisier";
        exit(-1);
    }
    return argv[1];
}

string getFileName() {
    return "./test/aici.txt";
//    return "./test/mytest.txt";
}

