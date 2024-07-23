package lacar.junilu

/**
 * AoC 2015 - Day 10: Elves Look, Elves Say
 */
class Day10(val input: String = "3113322113") : Solution<Int> {
    override fun part1(): Int = lookSay(40)
    override fun part2(): Int = lookSay(50)

    private fun lookSay(n: Int) = (1..n).fold(input) { look, _ -> say(look) }.length

    private val digitSequences = """(.)\1*""".toRegex()

    fun say(input: String) = digitSequences
        .findAll(input, 0)
        .map { matches -> matches.groupValues.first() }
        .map { sequence -> "${sequence.length}${sequence.first()}" }
        .joinToString("")
}