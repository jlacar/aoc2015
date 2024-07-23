package lacar.junilu

/**
 * AoC 2015 - Day 13: Knights of the Dinner Table
 */
class Day13(input: Map<String, Int>) : Solution<Int> {
    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>) = Day13(
            input.map { line ->
                val (name1, _, sign, units, name2) = line
                    .replace("happiness units by sitting next to ", "")
                    .split(" ")
                "$name1 $name2" to (if (sign == "gain") 1 else -1) * units.toInt()
            }.toMap()
        )

        fun peopleIn(input: List<String>) = input.map { it.split(" ").first() }.distinct()
    }
}