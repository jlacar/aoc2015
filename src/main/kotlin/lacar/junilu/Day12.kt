package lacar.junilu

import kotlinx.serialization.json.*

/**
 * AoC 2015 - Day 12: JSAbacusFramework.io
 */
class Day12(val input: String) : Solution<Int> {
    private val json = Json.decodeFromString<JsonElement>(input)

    override fun part1() = deepSumOfNumbersIn(json)

    override fun part2() = deepSumOfNumbersIn(json) { element ->
        element.values.any() { it == JsonPrimitive("red") }
    }
}

private fun deepSumOfNumbersIn(element: JsonElement, skip: (JsonObject) -> Boolean = { false }): Int =
    when (element) {
        is JsonPrimitive -> element.intOrNull ?: 0
        is JsonObject -> if (skip(element)) 0 else element.values.sumOf { deepSumOfNumbersIn(it, skip) }
        is JsonArray -> element.sumOf { deepSumOfNumbersIn(it, skip) }
        else -> 0
    }