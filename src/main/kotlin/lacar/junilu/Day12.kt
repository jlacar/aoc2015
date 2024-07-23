package lacar.junilu

import kotlinx.serialization.json.*

/**
 * AoC 2015 - Day 12: JSAbacusFramework.io
 */
class Day12(val input: String) : Solution<Int>() {
    override fun part1() = deepSumOf(Json.decodeFromString<JsonElement>(input))

    override fun part2() = deepSumOf(Json.decodeFromString<JsonElement>(input)) { element ->
        element.jsonObject.containsValue(JsonPrimitive("red"))
    }
}

private fun deepSumOf(element: JsonElement, skip: (JsonElement) -> Boolean =
    { _ -> false }): Int = when (element) {
    is JsonPrimitive -> element.intOrNull ?: 0
    is JsonObject -> if (skip.invoke(element)) 0
                     else element.values.sumOf { deepSumOf(it, skip) }
    is JsonArray -> element.sumOf { deepSumOf(it, skip) }
    else -> 0
}