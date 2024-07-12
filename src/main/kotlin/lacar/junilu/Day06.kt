package lacar.junilu

import kotlin.math.max

/**
 * Day 6: Probably a Fire Hazard
 */
class Day06(private val instructions: List<String>) : Solution<Int>() {

    private val lights = Array(1000) { BooleanArray(1000) }
    private val brights = Array(1000) { IntArray(1000) }

    override fun part1(): Int {
        instructions.forEach { instr -> perform(instr) }
        return lit(lights)
    }

    private fun perform(instr: String) {
        val parts = instr.split(" ")
        val action: (Boolean) -> Boolean = action(parts[0], parts[1])
        val startPos = pairFrom(parts[parts.lastIndex - 2])
        val endPos = pairFrom(parts.last())

        for (row in startPos.first..endPos.first) {
            for (column in startPos.second..endPos.second) {
                lights[row][column] = action(lights[row][column])
            }
        }
    }

    private fun pairFrom(s: String): Pair<Int, Int> {
        val (row, col) = s.split(",")
        return Pair(row.toInt(), col.toInt())
    }

    private fun action(action: String, qualifier: String): (Boolean) -> Boolean = when (action) {
        "toggle" -> { state -> !state }
        "turn" -> if (qualifier == "on") { _ -> true } else { _ -> false }
        else -> { state -> state }
    }

    private fun lit(lights: Array<BooleanArray>) = lights.sumOf { row -> row.count { it } }

    override fun part2(): Int {
        instructions.forEach { instr -> perform2(instr) }
        return brights.sumOf { row -> row.sumOf { it } }
    }

    private fun perform2(instr: String) {
        val parts = instr.split(" ")
        val action: (Int) -> Int = action2(parts[0], parts[1])
        val startPos = pairFrom(parts[parts.lastIndex - 2])
        val endPos = pairFrom(parts.last())

        for (row in startPos.first..endPos.first) {
            for (column in startPos.second..endPos.second) {
                brights[row][column] = action(brights[row][column])
            }
        }
    }

    private fun action2(action: String, qualifier: String): (Int) -> Int = when (action) {
        "toggle" -> { intensity -> intensity + 2 }
        "turn" -> if (qualifier == "on") { i -> i + 1 } else { i -> max(i - 1, 0) }
        else -> { state -> state }
    }
}