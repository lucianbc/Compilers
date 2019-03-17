typealias InputStream = ListIterator<Input>

class Lexer (
    private val dfa: DFA,
    private val inputStream: InputStream
)
{
    private var extractedChars = 0
    private var lastFinal : Option<State> = Option.None

    fun tokenize(): Iterator<Option<State>> = iterator {
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
        return dfa.consume(currentToken)
    }

    private fun saveState(state: State) {
        lastFinal = Option.Just(state)
        extractedChars = 0
    }

    private fun rollback(): Option<State> {
        putInputsBack()
        val finalState = lastFinal
        lastFinal = Option.None
        dfa.reset()
        return finalState
    }

    private fun putInputsBack() {
        while (extractedChars > 0) {
            extractedChars--
            inputStream.previous()
        }
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