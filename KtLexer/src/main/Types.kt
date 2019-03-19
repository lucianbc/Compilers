sealed class Either<out A, out B> {
    class Left<A>(val value: A): Either<A, Nothing>()
    class Right<B>(val value: B): Either<Nothing, B>()
}

sealed class Option<out A> {
    object None : Option<Nothing>()
    data class Just<out A> (val value : A) : Option<A>()
}