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
    OP_SHIFT_LEFT,
    OP_SHIFT_RIGHT,
    OP_SHIFT_ASSIGN_LEFT,
    OP_SHIFT_ASSIGN_RIGHT,

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

    WORD_I,
    WORD_IF,
    WORD_IN,
    WORD_INT,

    WORD_R,
    WORD_RE,
    WORD_RET,
    WORD_RETU,
    WORD_RETUR,
    WORD_RETURN,

    WORD_C,
    WORD_CH,
    WORD_CHA,
    WORD_CHAR,

    WORD_D,
    WORD_DO,
    WORD_DOU,
    WORD_DOUB,
    WORD_DOUBL,
    WORD_DOUBLE,

    WORD_E,
    WORD_EL,
    WORD_ELS,
    WORD_ELSE,

    WORD_W,
    WORD_WH,
    WORD_WHI,
    WORD_WHIL,
    WORD_WHILE,

    WORD_F,
    WORD_FO,
    WORD_FOR,

    WORD_FL,
    WORD_FLO,
    WORD_FLOA,
    WORD_FLOAT,

    IDENTIFIER,

    STRING_QUOTE(false),
    STRING_ESCAPE(false),
    STRING_ESCAPE_CARRIAGE(false),
    STRING_CONTENT(false),
    STRING,

    CHAR_START(false),
    CHAR_ESCAPE(false),
    CHAR_CONTENT(false),
    CHAR,

    LINE_COMMENT,
    LINE_COMMENT_END,

    C_COMMENT_CONTENT(false),
    C_COMMENT_EXIT_1(false),
    C_COMMENT,

    ZERO_FIRST,
    X_AFTER_ZERO(false),
    HEX_DIGIT,
    INT_DIGIT,
    DIGIT_AFTER_ZERO(false),
    POINT(false),
    DIGIT_AFTER_POINT,
    E_SEPARATOR(false),
    PLUS_MINUS_AFTER_E(false),
    DIGIT_AFTER_E
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
        transition(OP_LESS, '<', OP_SHIFT_LEFT),
        transition(OP_SHIFT_LEFT, '=', OP_SHIFT_ASSIGN_LEFT),
        transition(OP_GREATER, '=', OP_GREATER_EQUAL),
        transition(OP_GREATER, '>', OP_SHIFT_RIGHT),
        transition(OP_SHIFT_RIGHT, '=', OP_SHIFT_ASSIGN_RIGHT),
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
    val regularChars = ((1..127).toList() - listOf('"'.toInt(), '\n'.toInt(), '\\'.toInt()))
    val allButCarriage = (1..127).toList() - listOf('\r'.toInt())

    val fromQuoteToContent = regularChars
        .map { x -> x.toChar() }
        .map { x -> transition(STRING_QUOTE, x, STRING_CONTENT)}

    val contentLoop = regularChars
        .map { x -> x.toChar() }
        .map { x -> transition(STRING_CONTENT, x, STRING_CONTENT) }

    val fromEscapeToContent = allButCarriage
        .map { x -> x.toChar() }
        .map { x -> transition(STRING_ESCAPE, x, STRING_CONTENT) }

    return mapOf(
        transition(START, '"', STRING_QUOTE),
        transition(STRING_QUOTE, '"', STRING),
        transition(STRING_CONTENT, '"', STRING)
    ) + fromQuoteToContent + contentLoop + mapOf(
        transition(STRING_CONTENT, '\\', STRING_ESCAPE),
        transition(STRING_QUOTE, '\\', STRING_ESCAPE),
        transition(STRING_ESCAPE, '\r', STRING_ESCAPE_CARRIAGE),
        transition(STRING_ESCAPE_CARRIAGE, '\n', STRING_CONTENT)
    ) + fromEscapeToContent
}

fun chars() : TransitionTable {
    val allButEscape = (1..127).toList() - listOf('\\'.toInt())
    return mapOf(
        transition(START, '\'', CHAR_START),
        transition(CHAR_START, '\\', CHAR_ESCAPE),
        transition(CHAR_CONTENT, '\'', CHAR)
    ) + allButEscape.flatMap { x ->
        listOf( transition(CHAR_START, x.toChar(), CHAR_CONTENT), transition(CHAR_ESCAPE, x.toChar(), CHAR_CONTENT) )
    }
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
        transition(C_COMMENT_EXIT_1, '/', C_COMMENT),
        transition(C_COMMENT_EXIT_1, '*', C_COMMENT_EXIT_1)
    ) + ((1..127).toList() - listOf('/'.toInt(), '*'.toInt()))
        .map { x -> transition(C_COMMENT_EXIT_1, x.toChar(), C_COMMENT_CONTENT) }
        .toMap() + ((1..127).toList() - listOf('*'.toInt()))
        .map { x -> transition(C_COMMENT_CONTENT, x.toChar(), C_COMMENT_CONTENT) }
        .toMap()

    return cComment + lineComment
}

fun numberConstants() : TransitionTable {
    val digits = "0123456789"
    val nz = "123456789"
    val hex = "0123456789abcdef"

    return mapOf(
        transition(START, '0', ZERO_FIRST),
        transition(ZERO_FIRST, 'x', X_AFTER_ZERO),
        transition(DIGIT_AFTER_ZERO, '.', POINT),
        transition(DIGIT_AFTER_POINT, 'e', E_SEPARATOR),
        transition(DIGIT_AFTER_POINT, 'E', E_SEPARATOR),
        transition(INT_DIGIT, 'e', E_SEPARATOR),
        transition(INT_DIGIT, 'E', E_SEPARATOR),
        transition(E_SEPARATOR, '+', PLUS_MINUS_AFTER_E),
        transition(E_SEPARATOR, '-', PLUS_MINUS_AFTER_E),
        transition(INT_DIGIT, '.', DOT)
    ) + nz.map { d ->
        transition(START, d, INT_DIGIT)
    } + digits.flatMap { d -> listOf(
        transition(INT_DIGIT, d, INT_DIGIT),
        transition(ZERO_FIRST, d, DIGIT_AFTER_ZERO),
        transition(DIGIT_AFTER_ZERO, d, DIGIT_AFTER_ZERO),
        transition(POINT, d, DIGIT_AFTER_POINT),
        transition(DOT, d, DIGIT_AFTER_POINT),
        transition(E_SEPARATOR, d, DIGIT_AFTER_E),
        transition(PLUS_MINUS_AFTER_E, d, DIGIT_AFTER_E),
        transition(DIGIT_AFTER_E, d, DIGIT_AFTER_E)
    ) } + hex.flatMap { h -> listOf(
        transition(X_AFTER_ZERO, h, HEX_DIGIT),
        transition(HEX_DIGIT, h, HEX_DIGIT)
    ) }
}

//https://rosettacode.org/wiki/Compiler/lexical_analyzer

fun makeDFA(transitions: TransitionTable) : DFA {
    val finals = States.values()
        .filter { s -> s.final }
        .map { s -> State(s.ordinal) }
        .toSet()

    return DFA(transitions, state(START), finals)
}

fun state(s: States): State {
    return State(s.ordinal)
}

fun transition(start: States, input: Char, end: States) : Pair<TransitionKey, State> {
    return Pair(TransitionKey(state(start), Input(input)), state(end))
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
            chars() +
            comments() +
            numberConstants()
    return makeDFA(transitions)
}

// States renaming
val renaming = mapOf(
    HEX_DIGIT to "CONSTANT",
    INT_DIGIT to "CONSTANT",
    DIGIT_AFTER_POINT to "CONSTANT",
    DIGIT_AFTER_E to "CONSTANT",
    WORD_I to "IDENTIFIER",
    WORD_IN to "IDENTIFIER",

    WORD_R to "IDENTIFIER",
    WORD_RE to "IDENTIFIER",
    WORD_RET to "IDENTIFIER",
    WORD_RETU to "IDENTIFIER",
    WORD_RETUR to "IDENTIFIER",

    WORD_C to "IDENTIFIER",
    WORD_CH to "IDENTIFIER",
    WORD_CHA to "IDENTIFIER",

    WORD_D to "IDENTIFIER",
    WORD_DO to "IDENTIFIER",
    WORD_DOU to "IDENTIFIER",
    WORD_DOUB to "IDENTIFIER",
    WORD_DOUBL to "IDENTIFIER",

    WORD_E to "IDENTIFIER",
    WORD_EL to "IDENTIFIER",
    WORD_ELS to "IDENTIFIER",

    WORD_W to "IDENTIFIER",
    WORD_WH to "IDENTIFIER",
    WORD_WHI to "IDENTIFIER",
    WORD_WHIL to "IDENTIFIER",

    WORD_F to "IDENTIFIER",
    WORD_FO to "IDENTIFIER",

    WORD_FL to "IDENTIFIER",
    WORD_FLO to "IDENTIFIER",
    WORD_FLOA to "IDENTIFIER",

    ZERO_FIRST to "CONSTANT"
)

fun States.name() : String {
    return renaming.getOrDefault(this, this.name)
}