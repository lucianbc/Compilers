#include <string>
#include <iostream>
#include <sstream>

#define START_COL 1
#define START_LINE 1

int line = START_LINE;
int column = START_COL;

int crtLine = START_LINE;
int crtCol = START_COL;

void setPos(const std::string &text);

void count(const std::string &text) {
    line = crtLine;
    column = crtCol;
    if (text.back() == '\n') {
        crtLine += 1;
        crtCol = START_COL;
    } else if (text.front() == '"') {
        setPos(text);
    } else {
        crtCol += text.length();
    }
}

void setPos(const std::string & text) {
    std::stringstream ss(text);
    int lineNum = 0;
    int lastColLen = 0;
    std::string line;
    while (getline(ss, line)) {
        lineNum += 1;
        lastColLen = (int) line.length();
    }
    if (lineNum > 1) {
        crtLine += lineNum - 1;
        crtCol = START_COL + lastColLen;
    } else {
        crtCol += lastColLen;
    }
}

int getLine() {
    return line;
}

int getCol() {
    return column;
}