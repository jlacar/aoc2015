package lacar.junilu

import kotlinx.serialization.json.*

/**
 * AoC 2015 - Day 12: JSAbacusFramework.io
 */
class Day12(val input: String) : Solution<Int>() {
    override fun part1() = """-?\d+""".toRegex().findAll(input)
        .sumOf { matchResult -> matchResult.groupValues.first().toInt() }

    override fun part2() = deepSumOf(Json.decodeFromString<JsonElement>(input))
}

private fun deepSumOf(element: JsonElement): Int = when (element) {
    is JsonPrimitive -> element.intOrNull ?: 0
    is JsonObject -> if (element.containsValue(JsonPrimitive("red"))) 0
                     else element.values.sumOf { deepSumOf(it) }
    is JsonArray -> element.sumOf { deepSumOf(it) }
    else -> 0
}
