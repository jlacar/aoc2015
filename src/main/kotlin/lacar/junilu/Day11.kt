package lacar.junilu

/**
 * AoC 2015 - Day 11: Corporate Policy
 */
class Day11(val password: String = "cqjxjnds") : Solution<String>() {
    override fun part1(): String {
        var newPassword = password
        do { newPassword = newPassword.incr() } while (!isValid(newPassword))
        return newPassword
    }

    override fun part2(): String {
        TODO("Not yet implemented")
    }

    fun nextIncrement(password: String): String = password.incr()

    private fun String.incr() =
        foldRight(StringBuilder()) { c, sb ->
            if (sb.isEmpty() || sb.last() == 'A')
                sb.append(incWrap(c))
            else
                sb.append(c)
        }.reverse().toString().lowercase()

    private fun incWrap(c: Char): Char = if (c == 'z') 'A' else c.inc()

    fun isValid(password: String) =
        password.length == 8 &&
        password.all { it in 'a'..'z' } &&
        hasNoIllegalCharacters(password) &&
        hasValidTrio(password) &&
        hasPairOfDoubleLetters(password)

    companion object {
        private val validTrios = "abcdefghijklmnopqrstuvwxyz".windowed(3).filter { hasNoIllegalCharacters(it) }
        private val doubleLetters = Regex("([a-z])\\1")

        private fun hasValidTrio(password: String) = validTrios.any { trio -> password.contains(trio) }

        private fun hasNoIllegalCharacters(str: String) = str.none { "ilo".contains(it) }

        private fun hasPairOfDoubleLetters(password: String) = doubleLetters
            .findAll(password, 0)
            .map { matches -> matches.groupValues.first() }
            .distinct().count() >= 2

    }
}