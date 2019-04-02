import java.util.*
import kotlin.collections.HashMap

typealias InputStream = ListIterator<Input>

private const val START_LINE = 1
private const val START_COL = 1

data class Position(val line: Int = START_LINE, val col: Int = START_COL)

private fun Position.nextCol(): Position {
    return Position(line, col + 1)
}

private fun Position.nextLine() : Position {
    return Position(line + 1, START_COL)
}

data class Token(val type: State, val value: Int, val position: Position, private val lexer: Lexer) {
    override fun toString(): String {
        return StringBuilder()
            .append(States.values()[this.type.value].name())
            .append(": ")
            .append(this.lexer.valueOf(this))
            .append(" ")
            .append(this.position)
            .toString()
    }
}

class Lexer (
    private val dfa: DFA,
    private val inputStream: InputStream
)
{
    private var extractedChars = 0
    private var lastFinal : Option<State> = Option.None
    private var lastFinalEnd = Position()

    private var lastFinalStart = Position()
    private var cursor = Position()

    private val buffer = Stack<Input>()

    private val stringTable = HashMap<Int, String>()

    fun tokenize(): Sequence<Either<Position, Token>> = sequence {
        while (inputStream.hasNext()) {
            val newState = advance()
            when (newState) {
                is Option.None -> { yield(rollback()) }
                is Option.Just<State> -> {
                    if (dfa.accepts()) {
                        saveState(newState.value)
                    }
                }
            }
        }
        yield(rollback())
    }.filter { t -> shouldSkip(t) }

    private fun shouldSkip(t: Either<Position, Token>): Boolean {
        when (t) {
            is Either.Right<Token> -> return toState(t.value.type) !in IGNORED_STATES
        }
        return true
    }

    private fun toState(s: State): States {
        return States.values()[s.value]
    }

    private fun advance() : Option<State> {
        val currentToken = inputStream.next()
        extractedChars += 1
        advanceCursorWith(currentToken)
        buffer.push(currentToken)
        return dfa.consume(currentToken)
    }

    private fun advanceCursorWith(currentToken: Input) {
        cursor = if (currentToken.value == '\n') cursor.nextLine() else cursor.nextCol()
    }

    private fun saveState(state: State) {
        lastFinal = Option.Just(state)
        lastFinalEnd = cursor
        extractedChars = 0
    }

    private fun rollback(): Either<Position, Token> {
        putInputsBack()
        val finalState = lastFinal
        cursor = lastFinalEnd
        lastFinal = Option.None
        dfa.reset()
        return emmitToken(finalState)
    }

    private fun putInputsBack() {
        while (extractedChars > 0) {
            extractedChars--
            inputStream.previous()
            buffer.pop()
        }
    }

    private fun emmitToken(finalState: Option<State>): Either<Position, Token> {
        val lexemePosition = lastFinalStart
        lastFinalStart = cursor

        return when (finalState) {
            is Option.None -> Either.Left(lexemePosition)
            is Option.Just<State> -> {
                Either.Right(buildLexeme(finalState.value, lexemePosition))
            }
        }
    }

    public fun valueOf(l: Token): String {
        return stringTable[l.value]!!
    }

    private fun buildLexeme(state: State, pos: Position): Token {
        val token = buildToken()

        for (e in stringTable.entries){
            if (e.value == token) {
                return Token(state, e.key, pos, this)
            }
        }

        val newKey = stringTable.size
        stringTable[newKey] = token
        return Token(state, newKey, pos, this)
    }

    private fun buildToken(): String {
        val builder = StringBuilder()
        while (!buffer.empty()) {
            builder.append(buffer.pop().value)
        }
        return builder.reversed().toString()
    }
}
