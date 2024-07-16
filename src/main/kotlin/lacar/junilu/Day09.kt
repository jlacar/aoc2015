package lacar.junilu

class Day09(val distances: List<Day9Segment>): Solution<Int>() {
    override fun part1(): Int {
//        val cities = distances.mapTo(mutableSetOf<String>()) {
//
//        }
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun parseDistances(input: List<String>) = Day09(
            input.map { line ->
                val (startCity, _, endCity, _, distance) = line.split(" ", " = ")
                Day9Segment(startCity, endCity, distance.toInt())
            }
        )
    }
}

data class Day9Segment(val startCity: String, val endCity: String, val distance: Int)
