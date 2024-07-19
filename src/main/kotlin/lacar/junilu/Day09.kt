package lacar.junilu

import java.util.Collections.swap

private typealias SantaRouteSegment = Pair<String, Int>

class Day09(private val segments: List<SantaRouteSegment>) : Solution<Int>() {

    override fun part1() = allPossiblePaths().minOf { eachPath -> distanceThrough(eachPath) }

    override fun part2() = allPossiblePaths().maxOf { eachPath -> distanceThrough(eachPath) }

    private fun distanceThrough(cities: List<String>) = cities.windowed(2)
        .sumOf { (city1, city2) ->
            segments.find { it.first == "$city1 to $city2" || it.first == "$city2 to $city1" }?.second ?: 0
        }

    private fun allCities() = segments.flatMap { it.first.split(" to ").toList() }.distinct()

    private fun allPossiblePaths(): List<List<String>> {
        val paths = mutableListOf<List<String>>()
        permutationsOf(allCities(), 0, paths)
        return paths
    }

    private fun permutationsOf(cities: List<String>, index: Int, soFar: MutableList<List<String>>) {
        if (index == cities.lastIndex) soFar.add(cities.toList())
        for (i in index..cities.lastIndex) {
            swap(cities, index, i)
            permutationsOf(cities, index + 1, soFar)
            swap(cities, i, index)
        }
    }

    companion object {
        fun using(input: List<String>) = Day09(input.map { segment ->
            val (cities, distance) = segment.split(" = ")
            SantaRouteSegment(cities, distance.toInt())
        })
    }
}