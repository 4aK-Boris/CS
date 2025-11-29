package dmitriy.losev.cs.processor

fun main() {
    println(0UL.convert())
}

fun ULong.convert(): Long {
    return (this + Long.MIN_VALUE.toULong()).toLong()
}