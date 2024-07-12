package lacar.junilu

import kotlin.math.max

typealias Corner = Pair<Int, Int>

/**
 * Day 6: Probably a Fire Hazard
 */
class Day06(private val instructions: List<String>) : Solution<Int>() {
    override fun part1() = executeInstructionsFor(Part1()).howMany()
    override fun part2() = executeInstructionsFor(Part2()).howMany()

    private fun executeInstructionsFor(lights: Day06Part): Day06Part {
        instructions.forEach { instr -> lights.perform(Command(instr)) }
        return lights
    }
}

data class Command(instr: String) {
    val parts = instr.split(" ")
    val action = parts[0]
    val qualifier = parts[1]
    val topCorner = toCorner(parts[parts.lastIndex - 2])
    val bottomCorner = toCorner(parts.last())

    private fun toCorner(s: String): Corner {
        val (row, col) = s.split(",")
        return Pair(row.toInt(), col.toInt())
    }
}

interface Day06Part {
    fun perform(command: Command)
    fun howMany(): Int
}

private class Part1 : Day06Part {
    private val lights = Array(1000) { BooleanArray(1000) }

    override fun howMany(): Int = lights.sumOf { row -> row.count { it } }

    override fun perform(command: Command) {
        val action = actionFor(command.action, command.qualifier)
        for (row in command.topCorner.first..command.bottomCorner.first) {
            for (column in command.topCorner.second..command.bottomCorner.second) {
                lights[row][column] = action(lights[row][column])
            }
        }
    }

    private fun actionFor(action: String, qualifier: String): (Boolean) -> Boolean = when (action) {
        "toggle" -> { state -> !state }
        "turn" -> if (qualifier == "on") { _ -> true } else { _ -> false }
        else -> { state -> state }
    }
}

private class Part2 : Day06Part {
    private val brights = Array(1000) { IntArray(1000) }

    override fun howMany(): Int = brights.sumOf { row -> row.sumOf { it } }

    override fun perform(command: Command) {
        val action = actionFor(command.action, command.qualifier)
        for (row in command.topCorner.first..command.bottomCorner.first) {
            for (column in command.topCorner.second..command.bottomCorner.second) {
                brights[row][column] = action(brights[row][column])
            }
        }
    }

    private fun actionFor(action: String, qualifier: String): (Int) -> Int = when (action) {
        "toggle" -> { intensity -> intensity + 2 }
        "turn" -> if (qualifier == "on") { i -> i + 1 } else { i -> max(i - 1, 0) }
        else -> { intensity -> intensity }
    }
}