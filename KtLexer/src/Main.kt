import java.io.File

fun readFile(filename: String): InputStream {
    val text = File(filename).readText()
    return text.map(::mappingFun).listIterator()
}

fun mappingFun(x: Char): Input {
    return Input(x)
}

fun main() {
    val input = readFile("./data/file1.txt")
    val tokenSequence = tokenize(input)

    loop@ for (t in tokenSequence) {
        when (t) {
            is Either.Right<Lexeme> -> printLexeme(t.value)
            is Either.Left<Position> -> {
                printError(t.value)
                break@loop
            }
        }
    }
}