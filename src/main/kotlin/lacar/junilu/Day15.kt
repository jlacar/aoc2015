package lacar.junilu

import kotlin.math.max

class Day15(private val ingredients: List<Ingredient>, private val teaspoonsTotal: Int) : Solution<Int> {

    override fun part1(): Int {
        val scores = mutableListOf(0)
        val portions = proportions(ingredients.size, teaspoonsTotal).iterator()
        while (portions.hasNext()) {
            scores.add(cookieScore(portions.next()))
        }
        return scores.max()
    }

    fun cookieScore(portions: IntArray): Int {
        val propertiesPart1 = listOf("capacity", "durability", "flavor", "texture")
        val ingredientScores = portions.mapIndexed { i, teaspoons -> ingredients[i].scoreFor(teaspoons) }
        val propertySums = propertiesPart1.map { property ->
            ingredientScores.sumOf { score: Map<String, Int> ->
                score.getOrDefault(property, 0)
            }
        }
        return propertySums.fold(1) { acc, sum -> acc * max(0, sum) }
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>, teaspoonsTotal: Int): Day15 {
            val ingredients = input.map { line ->
                Ingredient(
                    line.substringAfter(": ").split(", ")
                        .mapTo(mutableListOf()) { prop ->
                            val (name, units) = prop.split(" ")
                            name to units.toInt()
                        }.toMap()
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
