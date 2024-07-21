package lacar.junilu

class Day12(val input: String) : Solution<Int>() {

    val numbers = """-?\d+""".toRegex()

    override fun part1() = sumNumbers(input)

    private fun sumNumbers(input: String) = numbers.findAll(input)
        .sumOf { matchResult -> matchResult.groupValues.first().toInt() }

    val removeRedObjects = """\{.*"red".*}""".toRegex()

    override fun part2(): Int =
        removeRedObjects.replace(input, "").let { sumNumbers(it) }
}