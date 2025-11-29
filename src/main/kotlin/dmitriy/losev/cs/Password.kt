package dmitriy.losev.cs

import kotlin.random.Random

private val RNG = Random

private val LOWER  = "abcdefghijklmnopqrstuvwxyz"
private val UPPER  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private val DIGITS = "0123456789"
private val SYMBOL = "!@#\$%^&*()-_=+[]{};:,.?/"

fun generatePassword(minLen: Int = 12, maxLen: Int = 20): String {
    require(minLen >= 4) { "minLen must be >= 4" }
    require(maxLen >= minLen) { "maxLen must be >= minLen" }

    val pools = listOf(LOWER, UPPER, DIGITS, SYMBOL)
    val all   = pools.joinToString("")

    val length = if (minLen == maxLen) minLen else RNG.nextInt(maxLen - minLen + 1) + minLen

    // 1) Гарантируем, что каждый класс представлен хотя бы 1 раз
    val chars = mutableListOf<Char>().apply {
        add(LOWER.random(RNG))
        add(UPPER.random(RNG))
        add(DIGITS.random(RNG))
        add(SYMBOL.random(RNG))
    }

    // 2) Добираем оставшиеся символы с лёгкими ограничениями
    fun cls(c: Char): Int = when {
        c in LOWER  -> 0
        c in UPPER  -> 1
        c in DIGITS -> 2
        else        -> 3
    }

    while (chars.size < length) {
        var next: Char
        var attempts = 0
        do {
            next = all.random(RNG)
            attempts++
            val nCls = cls(next)
            val okNoTripleClass = chars.size < 2 || !(cls(chars[chars.lastIndex]) == nCls && cls(chars[chars.lastIndex - 1]) == nCls)
            val okNoTripleSame  = chars.size < 2 || !(chars.last() == next && chars[chars.lastIndex - 1] == next)
        } while (!(okNoTripleClass && okNoTripleSame) && attempts < 20)
        chars += next
    }

    // 3) Перемешиваем, чтобы обязательные 4 символа не были в начале
    chars.shuffle(RNG)
    return chars.joinToString("")
}