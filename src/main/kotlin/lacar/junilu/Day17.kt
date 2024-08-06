package lacar.junilu

/**
 * AoC 2015 - Day 17: No Such Thing as Too Much
 */
class Day17(private val containers: List<Int>, private val liters: Int) : Solution<Int> {

    override fun part1(): Int =
        (1..containers.size).sumOf { n -> waysToFill(n, containers, liters) }

    override fun part2(): Int {
        for (n in 1..containers.size) {
            val ways = waysToFill(n, containers, liters)
            if (ways > 0) return ways
        }
        return 0
    }

    private fun waysToFill(n: Int, containers: List<Int>, liters: Int) = containers
        .combinations(n)
        .count { it.sum() == liters }

    companion object {
        fun using(input: List<String>, liters: Int) = Day17(input.map { it.toInt() }, liters)
    }
}