import java.util.*

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

data class Lexeme(val type: State, val value: String, val position: Position)

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

    fun tokenize(): Sequence<Either<Position, Lexeme>> = sequence {
        while (inputStream.hasNext()) {
            val newState = advance()
            when (newState) {
                is Option.None -> yield(rollback())
                is Option.Just<State> -> {
                    if (dfa.accepts()) {
                        saveState(newState.value)
                    }
                }
            }
        }
        yield(rollback())
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

    private fun rollback(): Either<Position, Lexeme> {
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

    private fun emmitToken(finalState: Option<State>): Either<Position, Lexeme> {
        val lexemePosition = lastFinalStart
        lastFinalStart = cursor

        return when (finalState) {
            is Option.None -> Either.Left(lexemePosition)
            is Option.Just<State> -> {
                Either.Right(Lexeme(finalState.value, buildToken(), lexemePosition))
            }
        }
    }

    private fun buildToken(): String {
        val builder = StringBuilder()
        while (!buffer.empty()) {
            builder.append(buffer.pop().value)
        }
        return builder.reversed().toString()
    }
}


fun main() {
    //    accepted language: 'a', 'cd'
    val transitions = mapOf(
        TransitionKey(State(0), Input('a')) to State(1),
        TransitionKey(State(0), Input('b')) to State(2),
        TransitionKey(State(0), Input('c')) to State(3),
        TransitionKey(State(3), Input('d')) to State(4)
    )

    val validator = DFA(transitions, State(0), setOf(State(1), State(4)))

    val input : InputStream = listOf('a', 'c', 'd', 'a').map { x -> Input(x) }.listIterator()

    val lexer = Lexer(validator, input)
    lexer.tokenize().forEach(::println)
}