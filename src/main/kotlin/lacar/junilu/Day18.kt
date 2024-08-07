package lacar.junilu

/**
 * AoC 2015 - Day 18: Like a GIF For Your Yard
 */

private typealias GridRow = List<Boolean>
private typealias Grid = List<GridRow>

class Day18(private val initialState: Grid, private val steps: Int) : Solution<Int> {

    override fun part1(): Int = initialState.animate(steps).howManyAreOn()

    override fun part2(): Int = initialState
        .animate(steps) { grid ->
            val firstRow = listOf(turnOnEnds(grid.first()))
            val lastRow = listOf(turnOnEnds(grid.last()))
            firstRow + grid.subList(1, grid.lastIndex) + lastRow
        }
        .howManyAreOn()

    private fun turnOnEnds(row: GridRow) = listOf(true) + row.subList(1, row.lastIndex) + listOf(true)

    private fun Grid.animate(steps: Int, transform: (Grid) -> Grid = { it }): Grid
        = (1..steps).fold(transform(this)) { grid, _ -> transform(grid.nextStep()) }

    private fun Grid.howManyAreOn() = this.flatten().count { it }

    private fun Grid.litNeighborsOf(row: Int, col: Int): Int {
        val offsets = -1..1
        return offsets.sumOf { rOffset ->
            offsets.count { cOffset ->
                if (rOffset != 0 || cOffset != 0) stateOf(row + rOffset, col + cOffset) else false
            }
        }
    }

    private fun Grid.stateOf(row: Int, col: Int): Boolean {
        return if (isOnGrid(row, col)) this[row][col] else false
    }

    private fun Grid.isOnGrid(row: Int, col: Int) = row in this.indices && col in this[row].indices

    private fun Grid.nextStep(): Grid =
        mapIndexed { row, rowOfLights ->
            rowOfLights.mapIndexed { col, lightIsOn ->
                val neighbors = litNeighborsOf(row, col)
                if (lightIsOn) {
                    neighbors in (2..3)
                } else {
                    neighbors == 3
                }
            }
        }

    companion object {
        fun using(input: List<String>, steps: Int) = Day18(
            input.map { line -> line.map { it == '#' } },
            steps
        )
    }
}