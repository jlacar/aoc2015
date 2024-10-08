package lacar.junilu

/**
 * AoC 2015 - Day 8: Matchsticks
 *
 * https://adventofcode.com/2015/day/8
 */
class Day08(val input: List<String>) {

    fun decodedLengthDiff(): Int = input.sumOf { it.length - it.decoded().length }

    private fun String.decoded(): String = drop(1).dropLast(1)
            .let { allQuotes -> Regex("""\\"""").replace(allQuotes, "?") }
            .let { allBackslashes -> Regex("""\\\\""").replace(allBackslashes, "?") }
            .let { allHexCodes -> Regex("""\\x[0-9a-f]{2}""").replace(allHexCodes, "?") }

    fun encodedLengthDiff(): Int = input.sumOf { it.encoded().length - it.length }

    private fun String.encoded(): String = this
            .let { backslashes -> Regex("""\\""").replace(backslashes, """\\\\""") }
            .let { doubleQuotes -> Regex("\"").replace(doubleQuotes, """\\"""") }
            .let { """"$it"""" }
}