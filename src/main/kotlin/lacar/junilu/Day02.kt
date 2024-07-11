package lacar.junilu

/**
 * Day 2: I Was Told There Would Be No Math
 */
class Day02(private val boxDimensions: List<IntArray>) : Solution<Int>() {

    override fun part1() = boxDimensions.sumOf { wrapperNeeded(it) }

    private fun wrapperNeeded(dims: IntArray): Int {
        val (w, l, h) = dims
        val areas = listOf(w * l, w * h, l * h)
        return areas.sumOf { 2 * it } + areas.min()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}