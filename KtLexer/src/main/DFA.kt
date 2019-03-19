inline class State (val value : Int)
inline class Input (val value : Char)

typealias TransitionKey = Pair<State, Input>
typealias TransitionTable = Map<TransitionKey, State>
typealias DeltaFun = Map<TransitionKey, Option<State>>

class DFA (
    transitions: TransitionTable,
    private val initial : State,
    private val finals : Set<State>
) {
    private val delta : DeltaFun
    private var state : Option<State> = Option.Just(initial)

    init {
        delta = HashMap()
        for (t in transitions) {
            val p = TransitionKey(t.key.first, t.key.second)
            delta[p] = Option.Just(t.value)
        }
    }

    fun consume(input: Input) : Option<State> {
        val currentState = state
        when (currentState) {
            is Option.Just<State> -> {
                val key = TransitionKey(currentState.value, input)
                state = delta.getOrDefault(key, Option.None)
            }
        }
        return state
    }

    fun accepts() : Boolean {
        val currentState = state
        return when (currentState) {
            is Option.Just<State> -> finals.contains(currentState.value)
            is Option.None -> false
        }
    }

    fun reset() {
        state = Option.Just(initial)
    }
}

fun validate(dfa: DFA, input: String) : Boolean {
    input.map { x -> Input(x) }.forEach { x -> dfa.consume(x)}

    return dfa.accepts()
}

fun main() {
    val transitions = mapOf(
        Pair(State(0), Input('a')) to State(1),
        Pair(State(0), Input('b')) to State(2),
        Pair(State(0), Input('c')) to State(3),
        Pair(State(3), Input('d')) to State(4)
    )

    val validator = DFA(transitions, State(0), setOf(State(1), State(4)))

    println(validate(validator, "cd"))
    validator.reset()

    println(validate(validator, "asdfg"))
    validator.reset()

    println(validate(validator, "a"))
    validator.reset()
}