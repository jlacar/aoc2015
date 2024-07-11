package lacar.junilu

class Day05(private val strings: List<String>) : Solution<Int>() {
    override fun part1() = strings.count { isNice(it) }

    private fun isNice(s: String) =
        hasEnoughVowels(s) && hasDoubleLetters(s) && hasNoDisallowedLetters(s)

    private fun hasNoDisallowedLetters(s: String) =
        s.windowed(2).none { listOf("ab", "cd", "pq", "xy").contains(it) }

    private fun hasDoubleLetters(s: String) =
        s.windowed(2).any { it.first() == it.last() }

    private fun hasEnoughVowels(s: String): Boolean =
        s.filter { "aeiou".contains(it) }.length >= 3

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}