package lacar.junilu

/**
 * Day 2: I Was Told There Would Be No Math
 */
class Day02(private val boxDimensions: List<IntArray>) : Solution<Int>() {

    override fun part1() = boxDimensions.sumOf { wrapperNeeded(it) }

    private fun wrapperNeeded(dims: IntArray): Int {
        val areas = listOf(dims[0] * dims[1], dims[0] * dims[2], dims[1] * dims[2])
        return areas.sumOf { 2 * it } + areas.min()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}