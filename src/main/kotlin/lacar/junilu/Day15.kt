package lacar.junilu

import kotlin.math.max
import kotlin.math.min

/**
 * AoC 2015 Day 15: Science for Hungry People
 */
class Day15(private val ingredients: List<Ingredient>, private val teaspoonsTotal: Int) : Solution<Int> {

    override fun part1(): Int {
        val properties = listOf("capacity", "durability", "flavor", "texture")
        return proportions(ingredients.size, teaspoonsTotal)
            .maxOf { portion ->
                cookieScore(portion, properties)
            }
    }

    override fun part2(): Int {
        val properties = listOf("capacity", "durability", "flavor", "texture", "calories")
        return proportions(ingredients.size, teaspoonsTotal)
            .maxOf { portion ->
                cookieScore(portion, properties) { prop: String, sum: Int ->
                    if (prop == properties.last()) sum == 500 else true
                }
            }
    }

    private fun cookieScore(
        portions: IntArray,
        properties: List<String>,
        select: (String, Int) -> Boolean = { _, _ -> true }
    ): Int {
        val portionScores = portions.mapIndexed { i, portionSize -> ingredients[i].scoreFor(portionSize) }
        val propertySums = properties.map { property ->
            portionScores.sumOf { score: Map<String, Int> ->
                score.getOrDefault(property, 0)
            }.let { sum -> if (select(property, sum)) sum else 0 }
        }
        return propertySums.take(4).fold(1) { acc, sum -> acc * max(0, sum) } *
                min(1, propertySums.last())
    }

    companion object {
        fun using(input: List<String>, teaspoonsTotal: Int) = Day15 (
            input.map { Ingredient(properties(it)) },
            teaspoonsTotal
        )

        private fun properties(line: String) = toKeyValuePairMap(
            line.substringAfter(": "),
            itemDelimiter = ", ",
            keyValueDelimiter = " ",
            transform = String::toInt
        )
    }

    data class Ingredient(val props: Map<String, Int>) {
        fun scoreFor(portionSize: Int) = props.map { (k, v) -> k to (v * portionSize) }.toMap()
    }
}

fun proportions(parts: Int, total: Int): Sequence<IntArray> = sequence {
    val start = if (parts == 1) total else 0
    for (portionSize in (start..total)) {
        if (parts <= 1) {
            yield(intArrayOf(portionSize))
        }  else {
            for (portion in proportions(parts - 1, total - portionSize)) {
                yield(intArrayOf(portionSize) + portion)
            }
        }
    }
}
