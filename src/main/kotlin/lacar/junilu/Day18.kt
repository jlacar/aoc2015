package lacar.junilu

/**
 * AoC 2015 - Day 18: Like a GIF For Your Yard
 */
class Day18(private val initialConfig: List<List<Boolean>>, private val steps: Int) : Solution<Int> {
    override fun part1(): Int = initialConfig.stepThrough(steps).howManyAreLit()

    private fun List<List<Boolean>>.howManyAreLit() = this.flatten().count { it }

    private fun List<List<Boolean>>.stepThrough(steps: Int): List<List<Boolean>> =
        (1..steps).fold(this) { config, _ -> nextStep(config) }

    private fun List<List<Boolean>>.litAround(row: Int, col: Int): Int {
        val offsets = -1..1
        return offsets.sumOf { rowOffset ->
            val r = row + rowOffset
            offsets.count { colOffset ->
                val c = col + colOffset
                if (isValidNeighbor(r, c, row, col))
                    this[r][c]
                else
                    false
            }
        }
    }

    private fun List<List<Boolean>>.isValidNeighbor(
        r: Int,
        c: Int,
        row: Int,
        col: Int
    ) = r in this.indices && c in this[row].indices && (r != row || c != col)

    private fun nextStep(config: List<List<Boolean>>): List<List<Boolean>> =
        config.mapIndexed { row, line ->
            line.mapIndexed { col, on ->
                val neighbors = config.litAround(row, col)
                if (on) {
                    neighbors in (2..3)
                } else {
                    neighbors == 3
                }
            }
        }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>, steps: Int) = Day18(
            input.map { line -> line.map { it == '#' } },
            steps
        )
    }
}