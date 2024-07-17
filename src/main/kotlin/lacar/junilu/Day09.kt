package lacar.junilu

class Day09(val segments: List<SantaRouteSegment>): Solution<Int>() {

    override fun part1(): Int = citiesIn(segments).let { allCities ->
        distancesOfPathsThrough(allCities).min()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    private fun distancesOfPathsThrough(allCities: Set<String>): List<Int> {
        val distances = listOf<Int>()
        return distances
    }

    fun cities() = citiesIn(segments)

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
        fun using(input: List<String>) = Day09(
            input.map { segment ->
                val (cities, distance) = segment.split(" = ")
                SantaRouteSegment(cities, distance.toInt())
            }
        )
    }

}

typealias SantaRouteSegment = Pair<String, Int>
