package lacar.junilu

import java.util.Collections.swap

class Day09(private val segments: List<SantaRouteSegment>) : Solution<Int>() {

    override fun part1(): Int = citiesIn(segments).let { cities ->
        allPathsThrough(cities).minOfOrNull { eachPath -> distanceThrough(eachPath) } ?: 0
    }

    override fun part2(): Int = citiesIn(segments).let { cities ->
        allPathsThrough(cities).maxOfOrNull { eachPath -> distanceThrough(eachPath) } ?: 0
    }

    private fun allPathsThrough(cities: Set<String>): List<List<String>> = permutations(cities.toList())

    private fun distanceThrough(cities: List<String>) = cities.windowed(2)
        .sumOf { (city1, city2) ->
            segments.find { it.first == "$city1 to $city2" || it.first == "$city2 to $city1" }?.second ?: 0
        }

    private fun permutations(input: List<String>): List<List<String>> {
        val solutions = mutableListOf<List<String>>()
        permutationsRecursive(input, 0, solutions)
        return solutions
    }

    private fun permutationsRecursive(input: List<String>, index: Int, answers: MutableList<List<String>>) {
        if (index == input.lastIndex) answers.add(input.toList())
        for (i in index..input.lastIndex) {
            swap(input, index, i)
            permutationsRecursive(input, index + 1, answers)
            swap(input, i, index)
        }
    }

    private fun citiesIn(segments: List<SantaRouteSegment>): Set<String> {
        return segments.fold(mutableSetOf()) { cities, segment ->
            cities.apply {
                val (city1, city2) = segment.first.split(" to ")
                add(city1)
                add(city2)
            }
        }
    }

    companion object {
        fun using(input: List<String>) = Day09(input.map { segment ->
            val (cities, distance) = segment.split(" = ")
            SantaRouteSegment(cities, distance.toInt())
        })
    }

}

typealias SantaRouteSegment = Pair<String, Int>
