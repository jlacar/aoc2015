//package lacar.junilu
//
//class Day09(val graph: List<Day9Segment>): Solution<Int>() {
//    fun permutationsOf(cities: Set<String>): List<List<String>> {
//        return listOf()
//    }
//
//    override fun part1(): Int {
//        TODO("Not yet implemented")
////        val cities = mutableSetOf<String>()
////        graph.forEach { segment -> with(cities) { add(segment.startCity); add(segment.endCity) }
////        permutationsOf(cities).windowed(2).map { (startCity, endCity) -> graph }
//    }
//
//    override fun part2(): Int {
//        TODO("Not yet implemented")
//    }
//
//    companion object {
//        fun using(input: List<String>) = Day09(
//            input.map { line ->
//                val (startCity, _, endCity, _, distance) = line.split(" ", " = ")
//                Day9Segment(startCity, endCity, distance.toInt())
//            }
//        )
//    }
//}
//
//data class Day9Segment(val startCity: String, val endCity: String, val distance: Int) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other == null || javaClass != other.javaClass) return false
//        other as Day9Segment
//        return startCity == other.startCity && endCity == other.endCity ||
//               startCity == other.endCity && endCity == other.startCity
//    }
//
//    override fun hashCode(): Int =
//        31 * (startCity.hashCode() + endCity.hashCode()) + distance
//    }
//}
