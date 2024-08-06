package lacar.junilu

/**
 * AoC 2015 - Day 17: No Such Thing as Too Much
 */
class Day17(val containers: List<Int>, val liters: Int) : Solution<Int> {

    override fun part1() = waysToDistributeEggnogOver(containers, liters).count()

    private fun waysToDistributeEggnogOver(containers: List<Int>, liters: Int): List<List<Int>> {
        return listOf()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>, liters: Int) = Day17(input.map { it.toInt() }, liters)
    }
}