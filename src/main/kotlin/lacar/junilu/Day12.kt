package lacar.junilu

import kotlinx.serialization.json.*

class Day12(val input: String) : Solution<Int>() {

    override fun part1() = """-?\d+""".toRegex().findAll(input)
        .sumOf { matchResult -> matchResult.groupValues.first().toInt() }

    override fun part2(): Int {
        val jsonObject: JsonObject = Json.decodeFromString(input)
        return sumOfNumbers(jsonObject)
    }
}

private fun sumOfNumbers(jsonObject: JsonObject): Int =
    if (jsonObject.containsValue(JsonPrimitive("red"))) 0
    else sumOfNumbers(jsonObject.values)

private fun sumOfNumbers(jsonArray: JsonArray): Int =
    jsonArray.sumOf { deepSumOf(it) }

private fun sumOfNumbers(collection: Collection<JsonElement>): Int =
    collection.sumOf { deepSumOf(it) }

private fun deepSumOf(element: JsonElement) = when (element) {
    is JsonPrimitive -> element.intOrNull ?: 0
    is JsonObject -> sumOfNumbers(element.jsonObject)
    else -> sumOfNumbers(element.jsonArray)
}