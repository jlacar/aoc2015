package lacar.junilu

/**
 * AoC 2015 - Day 3: Perfectly Spherical Houses in a Vacuum
 */
class Day03(private val directions: String) : Solution<Int> {
    override fun part1() = housesVisited(directions).distinct().count()

    override fun part2() =
        (housesVisited(directions.filterIndexed { index, _ -> index % 2 == 0 }) +
         housesVisited(directions.filterIndexed { index, _ -> index % 2 == 1 }))
        .distinct()
        .count()

    private fun housesVisited(directions: String) =
        directions.runningFold(Pair(0, 0)) { currentHouse, ch ->
            when (ch) {
                '<' -> currentHouse.copy(first = currentHouse.first - 1)
                '>' -> currentHouse.copy(first = currentHouse.first + 1)
                '^' -> currentHouse.copy(second = currentHouse.second + 1)
                else -> currentHouse.copy(second = currentHouse.second - 1)
            }
        }
}