package lacar.junilu

/**
 * Aoc 2015 - Day 2: I Was Told There Would Be No Math
 */
class Day02(private val boxDimensions: List<IntArray>) : Solution<Int>() {

    override fun part1() = boxDimensions.sumOf { wrapperNeeded(it) }

    private fun wrapperNeeded(dims: IntArray): Int {
        val (w, l, h) = dims
        val areas = listOf(w * l, w * h, l * h)
        return areas.sumOf { 2 * it } + areas.min()
    }

    override fun part2() = boxDimensions.sumOf { ribbonNeeded(it) }

    private fun ribbonNeeded(dims: IntArray): Int {
        val bow = dims.fold(1) { acc, i -> acc * i }
        val smallestPerimeter = dims.sumOf { 2 * it } - 2 * dims.max()
        return smallestPerimeter + bow
    }
}