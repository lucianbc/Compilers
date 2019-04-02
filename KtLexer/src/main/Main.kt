import java.io.File

fun readFile(filename: String): InputStream {
    val text = File(filename).readText()
    return text.map(::mappingFun).listIterator()
}

fun mappingFun(x: Char): Input {
    return Input(x)
}

fun main(args: Array<String>) {
    val input = readFile("./data/file1.txt")
//    val input = readFile(args[0])

    val lexDfa = makeDFA()
    val lexer = Lexer(lexDfa, input)

    val tokenSequence = lexer.tokenize()

    loop@ for (t in tokenSequence) {
        when (t) {
            is Either.Right<Token> -> println(t.value)
            is Either.Left<Position> -> {
                printError(t.value)
                break@loop
            }
        }
    }
}