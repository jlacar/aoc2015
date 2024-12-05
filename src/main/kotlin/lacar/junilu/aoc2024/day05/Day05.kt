package lacar.junilu.aoc2024.day05

import lacar.junilu.experimental.solution
import lacar.junilu.println

/**
 * AoC 2024 - Day 5: Print Queue
 *
 * https://adventofcode.com/2024/day/5
 */
object Day05 {
    val part1 get() = solution { input ->
        val (orderingRules, updatePages) = parse(input)

        // What do you get if you add up the middle page number from those correctly-ordered updates?
        updatePages.filter { pages ->
                pages.isOrderRight(orderingRules).also { if (it) "right order: $pages".println() }
            }
            .sumOf { it.middleNumber() }
    }

    val part2 get() = solution {
        TODO("Not implemented")
    }

    private fun parse(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val (first, second) = input.joinToString("\n").split("\n\n")

        val pageOrderingRules = first.lines()
            .map { it.split("|").map(String::trim) }
            .map { (left, right) -> Pair(left.toInt(), right.toInt()) }

        val pages = second.lines().map { it.trim().split(",").map(String::toInt) }

        return Pair(pageOrderingRules, pages)
    }
}

// Extensions for page update lists
private fun List<Int>.isOrderRight(orderingRules: List<Pair<Int, Int>>): Boolean {
    val orderedPairs = mapIndexed { index, page ->
        val rest = this.subList(index+1, this.size)
        List(rest.size) { page }.zip(rest)
    }.flatten()
    return orderedPairs.all { orderingRules.contains(it) }
}

private fun List<Int>.middleNumber() = get(lastIndex/2)

