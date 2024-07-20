package lacar.junilu

/**
 * AoC 2015 - Day 11: Corporate Policy
 */
class Day11(val password: String = "cqjxjnds") : Solution<String>() {
    override fun part1(): String {
        TODO("Not yet implemented")
    }

    override fun part2(): String {
        TODO("Not yet implemented")
    }

    fun isIllegalCharacter(c: Char) = "ilo".contains(c)

    val trios = "abcdefghijklmnopqrstuvwxyz".windowed(3).filter { it.none { c: Char -> isIllegalCharacter(c) } }

    fun nextIncrement(password: String): String = password.incr()

    private fun String.incr() =
        foldRight(StringBuilder()) { c, sb ->
            if (sb.isEmpty() || sb.last() == 'A')
                sb.append(incWrap(c))
            else
                sb.append(c)
        }.reverse().toString().lowercase()

    private fun incWrap(c: Char): Char = if (c == 'z') 'A' else c.inc()

    fun isValid(password: String): Boolean = true
}