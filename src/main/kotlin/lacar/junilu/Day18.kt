package lacar.junilu

/**
 * AoC 2015 - Day 18: Like a GIF For Your Yard
 */

private typealias GridRow = List<Boolean>
private typealias Grid = List<GridRow>

class Day18(private val initialConfiguration: Grid, private val steps: Int) : Solution<Int> {

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

    override fun part2(): Int = initialConfiguration
        .animate(steps) { grid -> grid.turnOnCorners() }
        .howManyAreOn()

    private fun Grid.animate(steps: Int, transform: (Grid) -> Grid = { it }): Grid =
        (1..steps).fold(transform(this)) { grid, _ -> transform(grid.nextStep()) }

    private fun Grid.howManyAreOn() = this.flatten().count { it }

    private fun Grid.nextStep(): Grid =
        mapIndexed { row, rowOfLights ->
            rowOfLights.mapIndexed { col, lightIsOn ->
                isLightOnInNextStep(row, col, lightIsOn)
            }
        }

    private fun Grid.isLightOnInNextStep(row: Int, col: Int, lightIsOn: Boolean): Boolean {
        val neighbors = litNeighborsOf(row, col)
        return if (lightIsOn) {
            neighbors in (2..3)
        } else {
            neighbors == 3
        }
    }

    private fun Grid.turnOnCorners() =
        listOf(turnOnEnds(first())) + subList(1, lastIndex) + listOf(turnOnEnds(last()))

    private fun turnOnEnds(row: GridRow) =
        listOf(true) + row.subList(1, row.lastIndex) + listOf(true)

    private fun Grid.litNeighborsOf(row: Int, col: Int): Int =
        neighborsOf(row, col).count { (neighborRow, neighborCol) -> isLightOnAt(neighborRow, neighborCol) }

    private fun Grid.isLightOnAt(row: Int, col: Int) =
        if (isOnGrid(row, col)) this[row][col] else false

    private fun Grid.isOnGrid(row: Int, col: Int) =
        row in this.indices && col in this[row].indices

    companion object {
        private val neighborOffsets = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1),  /* (0,0) */  Pair(0, 1),
            Pair(1, -1),  Pair(1, 0),  Pair(1, 1)
        )

        private fun neighborsOf(row: Int, col: Int): List<Pair<Int, Int>> =
            neighborOffsets.map { (rOffset, cOffset) -> Pair(row + rOffset, col + cOffset ) }

        fun using(input: List<String>, steps: Int) = Day18(
            input.map { line -> line.map { it == '#' } },
            steps
        )
    }
}