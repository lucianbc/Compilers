import States.*

enum class States (val final: Boolean = true) {
    START(false),
    OP_MULTIPLY,
    OP_DIVIDE,
    OP_MOD,
    OP_PLUS,
    OP_MINUS,
    OP_LESS,
    OP_LESS_EQUAL,
    OP_GREATER,
    OP_GREATER_EQUAL,
    OP_EQUAL,
    OP_NOT_EQUAL,
    OP_NOT,
    OP_ASSIGN,
    OP_AND,
    OP_OR,
    OP_INCREMENT,
    OP_DECREMENT,
    OP_INCREMENT_ASSIGN,
    OP_DECREMENT_ASSIGN,
    OP_BIT_AND,
    OP_BIT_OR,

    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    SEMICOLON,
    COMMA,
    DOT,

    WHITESPACE,

    WORD_I(false),
    WORD_IF,
    WORD_IN(false),
    WORD_INT,

    WORD_R(false),
    WORD_RE(false),
    WORD_RET(false),
    WORD_RETU(false),
    WORD_RETUR(false),
    WORD_RETURN,

    WORD_C(false),
    WORD_CH(false),
    WORD_CHA(false),
    WORD_CHAR,

    WORD_D(false),
    WORD_DO(false),
    WORD_DOU(false),
    WORD_DOUB(false),
    WORD_DOUBL(false),
    WORD_DOUBLE,

    WORD_E(false),
    WORD_EL(false),
    WORD_ELS(false),
    WORD_ELSE,

    WORD_W(false),
    WORD_WH(false),
    WORD_WHI(false),
    WORD_WHIL(false),
    WORD_WHILE,

    WORD_F(false),
    WORD_FO(false),
    WORD_FOR,

    WORD_FL(false),
    WORD_FLO(false),
    WORD_FLOA(false),
    WORD_FLOAT,

    IDENTIFIER,

    STRING_CONTENT(false),
    STRING,

    LINE_COMMENT,
    LINE_COMMENT_END,

    C_COMMENT_CONTENT(false),
    C_COMMENT_EXIT_1(false),
    C_COMMENT,

    HEX_OCT(false),
    HEX_START(false),
    HEX_1(false),
    HEX_CONSTANT,
    OCT_1(false),
    OCT_CONSTANT,
    INT_CONSTANT
}

val IGNORED_STATES = setOf(
    WHITESPACE, LINE_COMMENT_END, LINE_COMMENT, C_COMMENT
)

fun operators() : TransitionTable {
    return mapOf(
        transition(START, '*', OP_MULTIPLY),
        transition(START, '/', OP_DIVIDE),
        transition(START, '%', OP_MOD),
        transition(START, '+', OP_PLUS),
        transition(START, '-', OP_MINUS),
        transition(START, '<', OP_LESS),
        transition(START, '=', OP_ASSIGN),
        transition(START, '>', OP_GREATER),
        transition(START, '!', OP_NOT),
        transition(START, '&', OP_BIT_AND),
        transition(START, '|', OP_BIT_OR),
        transition(OP_LESS, '=', OP_LESS_EQUAL),
        transition(OP_GREATER, '=', OP_GREATER_EQUAL),
        transition(OP_ASSIGN, '=', OP_EQUAL),
        transition(OP_NOT, '=', OP_NOT_EQUAL),
        transition(OP_BIT_AND, '&', OP_AND),
        transition(OP_BIT_OR, '|', OP_OR),
        transition(OP_PLUS, '+', OP_INCREMENT),
        transition(OP_MINUS, '-', OP_DECREMENT),
        transition(OP_PLUS, '=', OP_INCREMENT_ASSIGN),
        transition(OP_MINUS, '=', OP_DECREMENT_ASSIGN)
    )
}

fun symbols() : TransitionTable {
    return mapOf(
        transition(START, '(', LEFT_PAREN),
        transition(START, ')', RIGHT_PAREN),
        transition(START, '{', LEFT_BRACE),
        transition(START, '}', RIGHT_BRACE),
        transition(START, '[', LEFT_BRACKET),
        transition(START, ']', RIGHT_BRACKET),
        transition(START, ';', SEMICOLON),
        transition(START, ',', COMMA),
        transition(START, '.', DOT)
    )
}

fun keywords() : TransitionTable {
//    recognized keywords: if, int, return, char, double, else, while, for
    return mapOf(
        transition(START, 'i', WORD_I),
        transition(WORD_I, 'f', WORD_IF),
        transition(WORD_I, 'n', WORD_IN),
        transition(WORD_IN, 't', WORD_INT),

        transition(START, 'r', WORD_R),
        transition(WORD_R, 'e', WORD_RE),
        transition(WORD_RE, 't', WORD_RET),
        transition(WORD_RET, 'u', WORD_RETU),
        transition(WORD_RETU, 'r', WORD_RETUR),
        transition(WORD_RETUR, 'n', WORD_RETURN),

        transition(START,'c', WORD_C),
        transition(WORD_C,'h', WORD_CH),
        transition(WORD_CH,'a', WORD_CHA),
        transition(WORD_CHA,'r', WORD_CHAR),

        transition(START,'d', WORD_D),
        transition(WORD_D,'o', WORD_DO),
        transition(WORD_DO,'u', WORD_DOU),
        transition(WORD_DOU,'b', WORD_DOUB),
        transition(WORD_DOUB,'l', WORD_DOUBL),
        transition(WORD_DOUBL,'e', WORD_DOUBLE),

        transition(START,'e', WORD_E),
        transition(WORD_E,'l', WORD_EL),
        transition(WORD_EL,'s', WORD_ELS),
        transition(WORD_ELS,'e', WORD_ELSE),

        transition(START,'w', WORD_W),
        transition(WORD_W,'h', WORD_WH),
        transition(WORD_WH,'i', WORD_WHI),
        transition(WORD_WHI,'l', WORD_WHIL),
        transition(WORD_WHIL,'e', WORD_WHILE),

        transition(START,'f', WORD_F),
        transition(WORD_F,'o', WORD_FO),
        transition(WORD_FO,'r', WORD_FOR),

        transition(WORD_F, 'l', WORD_FL),
        transition(WORD_FL, 'o', WORD_FLO),
        transition(WORD_FLO, 'a', WORD_FLOA),
        transition(WORD_FLOA, 't', WORD_FLOAT)

    )
}

fun whitespaces() : TransitionTable {
    val whitespaces = " \t\r\n"

    val startToWhitespaces = whitespaces
        .map { x -> transition(START, x, WHITESPACE) }
        .toMap()

    val whitespaceStar = whitespaces
        .map { x -> transition(WHITESPACE, x, WHITESPACE) }
        .toMap()

    return startToWhitespaces + whitespaceStar
}

fun identifiers() : TransitionTable {
    val validChars = ('a'..'z').toList() + ('A'..'Z').toList() + "_".toList()
    val digits  = ('0'..'9').toList()

    val fromStart = validChars
        .map { x -> transition(START, x, IDENTIFIER) }
        .toMap()

    val identifierStar = (validChars + digits)
        .map { x -> transition(IDENTIFIER, x, IDENTIFIER) }
        .toMap()

    val fromWords = States.values()
        .filter { s -> s.name.startsWith("WORD") }
        .flatMap { s -> (validChars + digits).map { char -> transition(s, char, IDENTIFIER) } }
        .toMap()

    return fromStart + identifierStar + fromWords
}

fun identifiersAndKeywords() : TransitionTable {
    // The addition is not symmetric here.
    // The keywords transitions take precedence over the identifier ones,
    // so that we keep the already defined keywords and all the other
    // transitions are sent to the identifier state.
    // If swapped, those two will change and the keywords will be lost
    return identifiers() + keywords()
}

fun strings() : TransitionTable {
    val anyCharStar =  ((1..127).toList() - listOf('"'.toInt()))
        .map { x -> x.toChar() }
        .map { x -> transition(STRING_CONTENT, x, STRING_CONTENT)}
        .toMap()

    return mapOf(
        transition(START, '"', STRING_CONTENT),
        transition(STRING_CONTENT, '"', STRING)
    ) + anyCharStar
}

fun comments() : TransitionTable {
    val lineComment = ((1..127).toList() - listOf('\n'.toInt()))
        .map { x -> transition(LINE_COMMENT, x.toChar(), LINE_COMMENT) }
        .toMap() + mapOf(
            transition(OP_DIVIDE, '/', LINE_COMMENT),
            transition(LINE_COMMENT, '\n', LINE_COMMENT_END)
        )

    val cComment = mapOf(
        transition(OP_DIVIDE, '*', C_COMMENT_CONTENT),
        transition(C_COMMENT_CONTENT, '*', C_COMMENT_EXIT_1),
        transition(C_COMMENT_EXIT_1, '/', C_COMMENT)
    ) + ((1..127).toList() - listOf('/'.toInt()))
        .map { x -> transition(C_COMMENT_EXIT_1, x.toChar(), C_COMMENT_CONTENT) }
        .toMap() + ((1..127).toList() - listOf('*'.toInt()))
        .map { x -> transition(C_COMMENT_CONTENT, x.toChar(), C_COMMENT_CONTENT) }
        .toMap()

    return cComment + lineComment
}

fun intConstants() : TransitionTable {
    val digits = "0123456789"
    val nz = "123456789"
    val hex = "0123456789abcdef"
    val oct = "01234567"

    return mapOf(
        transition(START, '0', HEX_OCT),
        transition(HEX_OCT, 'x', HEX_START),
        transition(HEX_OCT, 'X', HEX_START)
    ) + hex.flatMap { h -> listOf(
        transition(HEX_START, h, HEX_CONSTANT),
        transition(HEX_CONSTANT, h, HEX_CONSTANT)
    ) } + oct.flatMap { o -> listOf(
        transition(HEX_OCT, o, OCT_CONSTANT),
        transition(OCT_CONSTANT, o, OCT_CONSTANT)
    ) } + nz.map { d ->
        transition(START, d, INT_CONSTANT)
    } + digits.map { d ->
        transition(INT_CONSTANT, d, INT_CONSTANT)
    }
}

fun testStrings() {
    val dfa = makeDFA(strings())
    val lexer = Lexer(dfa, input("\"asdfasf\"\"asdas\""))
    lexer.tokenize().forEach(::printState)
}

fun testKeywordsAndIdentifiers() {
    val transitions = identifiersAndKeywords() + whitespaces()

    val dfa = makeDFA(transitions)

    val lexer = Lexer(dfa, input("if int else whiley"))

    lexer.tokenize().forEach(::printState)
}

fun testKeywordsPlusIdentifiers() {
    val transitions = identifiers() + keywords()

    val k = TransitionKey(state(WORD_DOUBLE), Input('x'))
    val out = transitions.getValue(k).value
    println(States.values()[out])
//    transitions.forEach { x -> println(x)}
}

fun testOperators () {
    val dfa = makeDFA(operators())

    val lexer = Lexer(dfa, input("*/%+-<<=>>===!=!=&&&|||"))
    lexer.tokenize()
}

fun tokenize(input: InputStream): Sequence<Either<Position, Lexeme>> {
    val lexDfa = makeDFA()
    val lexer = Lexer(lexDfa, input)
    return lexer.tokenize().filter(::shouldSkip)
}

fun shouldSkip(t: Either<Position, Lexeme>): Boolean {
    when (t) {
        is Either.Right<Lexeme> -> return toState(t.value.type) !in IGNORED_STATES
    }
    return true
}

fun toState(s: State): States {
    return States.values()[s.value]
}

//https://rosettacode.org/wiki/Compiler/lexical_analyzer
fun main() {
//    testOperators()
//    testKeywordsAndIdentifiers()
//    testKeywordsPlusIdentifiers()
    testStrings()
}

fun makeDFA(transitions: TransitionTable) : DFA {
    val finals = States.values()
        .filter { s -> s.final }
        .map { s -> State(s.ordinal) }
        .toSet()

    return DFA(transitions, state(START), finals)
}

fun input(s: String) : InputStream {
    return s.map { x -> Input(x) }.toList().listIterator()
}

fun state(s: States): State {
    return State(s.ordinal)
}

fun transition(start: States, input: Char, end: States) : Pair<TransitionKey, State> {
    return Pair(TransitionKey(state(start), Input(input)), state(end))
}

fun printState(state: Either<Position, Lexeme>) {
    when (state) {
        is Either.Right<Lexeme> -> printLexeme(state.value)
        is Either.Left<Position> -> printError(state.value)
    }
}

fun printLexeme(l: Lexeme) {
    println(States.values()[l.type.value].toString() + ": "
            + l.position.line + ", "
            + l.position.col + ", value: "
            + l.value )
}

fun printError(p: Position) {
    println("Eroare la pozitia l=" + p.line + ", c=" + p.col)
}

fun makeDFA() : DFA {
    val transitions = operators() +
            symbols() +
            keywords() +
            whitespaces() +
            identifiersAndKeywords() +
            strings() +
            comments() +
            intConstants()
    return makeDFA(transitions)
}