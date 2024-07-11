package lacar.junilu

class Day03(private val directions: String) : Solution<Int>() {
    override fun part1() = housesVisited(directions).distinct().count()

    private fun housesVisited(directions: String) =
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

    override fun part2(): Int {
        val (santaDirections, roboSantaDirections) = splitUp(directions)
        return (listOf(Pair(0,0)) +
                housesVisited(santaDirections) +
                housesVisited(roboSantaDirections))
            .distinct().count()
    }

    private fun splitUp(directions: String): Pair<String, String> =
        Pair(
            directions.filterIndexed { index, _ -> index % 2 == 0 },
            directions.filterIndexed { index, _ -> index % 2 == 1 }
        )
}