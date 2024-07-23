package lacar.junilu

import kotlin.math.max

typealias Corner = Pair<Int, Int>

/**
 * AoC 2015 - Day 6: Probably a Fire Hazard
 */
class Day06(private val instructions: List<String>) : Solution<Int> {
    override fun part1() = Part1().perform(instructions).totalOf { row -> row.count { it } }

    override fun part2() = Part2().perform(instructions).totalOf { row -> row.sum() }
}

data class Day6Instruction(val details: String) {
    private val parts = details.split(" ")
    private val topCorner = toCorner(parts[parts.lastIndex - 2])
    private val bottomCorner = toCorner(parts.last())

    val action = parts[0]
    val qualifier = parts[1]

    fun rowRange() = topCorner.first..bottomCorner.first
    fun columnRange() = topCorner.second..bottomCorner.second

    private fun toCorner(s: String): Corner {
        val (row, col) = s.split(",")
        return Pair(row.toInt(), col.toInt())
    }
}

sealed interface Day06Part<T> {
    val grid: Array<Array<T>>

    fun perform(instructions: List<String>): Day06Part<T> {
        instructions.forEach { instruction ->
            with (Day6Instruction(instruction)) {
                val action = actionFor(action, qualifier)
                for (row in rowRange()) {
                    for (column in columnRange()) {
                        grid[row][column] = action(grid[row][column])
                    }
                }
            }
        }
        return this
    }

    fun actionFor(action: String, qualifier: String): (T) -> T

    fun totalOf(op: (Array<T>) -> Int): Int = grid.sumOf { row -> op(row) }
}

private class Part1 : Day06Part<Boolean> {
    override val grid = Array(1000) { Array(1000) { false } }

    override fun actionFor(action: String, qualifier: String): (Boolean) -> Boolean = when (action) {
        "toggle" -> { state -> !state }
        "turn" -> if (qualifier == "on") { _ -> true } else { _ -> false }
        else -> { state -> state }
    }
}

private class Part2 : Day06Part<Int> {
    override val grid = Array(1000) { Array(1000) { 0 } }

    override fun actionFor(action: String, qualifier: String): (Int) -> Int = when (action) {
        "toggle" -> { intensity -> intensity + 2 }
        "turn" -> if (qualifier == "on") { i -> i + 1 } else { i -> max(i - 1, 0) }
        else -> { intensity -> intensity }
    }
}