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
    val lexDfa = makeDFA()
    val lexer = Lexer(lexDfa, input)
    lexer.tokenize().forEach(::printState)
}