package lacar.junilu

class Day03(private val directions: String) : Solution<Int>() {
    override fun part1() = housesVisited().distinct().count()

    private fun housesVisited() =
        directions.fold(mutableListOf(Pair(0, 0))) { acc, char ->
            acc.apply {
                add(
                    when (char) {
                        '<' -> Pair(last().first - 1, last().second)
                        '>' -> Pair(last().first + 1, last().second)
                        '^' -> Pair(last().first, last().second + 1)
                        else -> Pair(last().first, last().second - 1)
                    }
                )
            }
        }


    override fun part2(): Int = 0
}