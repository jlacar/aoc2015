package lacar.junilu

import kotlin.math.max
import kotlin.math.min

class Day15(private val ingredients: List<Ingredient>, private val teaspoonsTotal: Int) : Solution<Int> {

    override fun part1(): Int {
        val scores = mutableListOf(0)
        val portions = proportions(ingredients.size, teaspoonsTotal).iterator()
        val properties = listOf("capacity", "durability", "flavor", "texture")
        while (portions.hasNext()) {
            scores.add(cookieScore(portions.next(), properties))
        }
        return scores.max()
    }

    override fun part2(): Int {
        val scores = mutableListOf(0)
        val portions = proportions(ingredients.size, teaspoonsTotal).iterator()
        val properties = listOf("capacity", "durability", "flavor", "texture", "calories")
        while (portions.hasNext()) {
            val score = cookieScore(portions.next(), properties) { prop: String, sum: Int ->
                if (prop == properties.last()) sum == 500 else true
            }
            scores.add(score)
        }
        return scores.max()
    }

    private fun cookieScore(
        portions: IntArray,
        properties: List<String>,
        filter: (String, Int) -> Boolean = { _, _ -> true }
    ): Int {
        val portionScores = portions.mapIndexed { i, portionSize -> ingredients[i].scoreFor(portionSize) }
        val propertySums = properties.map { property ->
            portionScores.sumOf { score: Map<String, Int> ->
                score.getOrDefault(property, 0)
            }.let { sum -> if (filter(property, sum)) sum else 0 }
        }
        return propertySums.take(4).fold(1) { acc, sum -> acc * max(0, sum) } *
                min(1, propertySums.last())
    }

    companion object {
        fun using(input: List<String>, teaspoonsTotal: Int): Day15 {
            val ingredients = input.map { line ->
                Ingredient(
                    line.substringAfter(": ").split(", ")
                        .mapTo(mutableListOf()) { prop ->
                            val (name, units) = prop.split(" ")
                            name to units.toInt()
                        }.toMap().also { println("ingredients: $it") }
                )
            }

            return Day15(ingredients, teaspoonsTotal)
        }
    }

    data class Ingredient(val props: Map<String, Int>) {
        fun scoreFor(portionSize: Int) = props.map { (k, v) -> k to (v * portionSize) }.toMap()
    }
}

fun proportions(parts: Int, total: Int): Sequence<IntArray> = sequence {
    val start = if (parts == 1) total else 0

    for (i in (start..total)) {
        val remaining = total - i
        if (parts - 1 > 0) {
            val remProps = proportions(parts - 1, remaining).iterator()
            while (remProps.hasNext()) {
                yield(intArrayOf(i) + remProps.next())
            }
        } else {
            yield(intArrayOf(i))
        }
    }
}
