package lacar.junilu

class Day12(val input: String) : Solution<Int>() {

    val numbers = """-?\d+""".toRegex()

    override fun part1() =
        numbers.findAll(input).sumOf { matchResult -> matchResult.groupValues.first().toInt() }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}