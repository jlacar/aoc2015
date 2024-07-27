package lacar.junilu

import java.util.Collections.swap

private typealias SantaRouteSegment = Pair<String, Int>

/**
 * AoC 2015 - Day 9: All in a Single Night
 */
class Day09(private val segments: List<SantaRouteSegment>) : Solution<Int> {

    private val allCities = segments.flatMap { it.first.split(" to ") }.distinct()
    private val allPossiblePaths = allCities.permutations()

    override fun part1() = allPossiblePaths.minOf { eachPath -> distanceThrough(eachPath) }

    override fun part2() = allPossiblePaths.maxOf { eachPath -> distanceThrough(eachPath) }

    private fun distanceThrough(cities: List<String>) = cities.windowed(2)
        .sumOf { (city1, city2) ->
            segments.find { it.first == "$city1 to $city2" || it.first == "$city2 to $city1" }?.second ?: 0
        }

    companion object {
        fun using(input: List<String>) = Day09(input.map { segment ->
            val (cities, distance) = segment.split(" = ")
            SantaRouteSegment(cities, distance.toInt())
        })
    }
}