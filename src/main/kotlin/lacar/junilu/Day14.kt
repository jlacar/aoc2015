package lacar.junilu

import kotlin.math.min

class Day14(private val reindeerData: List<ReindeerData>, private val raceTime: Int) : Solution<Int> {

    override fun part1() = reindeerData.maxOf { it.distanceFlownIn(raceTime) }

    override fun part2(): Int {
        val scores = raceByPointSystem()
        return scores.values.maxOf { it }
    }

    private fun raceByPointSystem(): Map<ReindeerData, Int> {
        val scores = reindeerData.associateWith { 0 }.toMutableMap()
        val distances = reindeerData.associateWith { it.distancesForEachSecondDuring(raceTime) }
        (0 until raceTime).forEach { i ->
            val maxDistanceSoFar = distances.maxOf { (_, progress) -> progress[i] }
            distances.forEach { (reindeer, distances) ->
                if (distances[i] == maxDistanceSoFar) scores[reindeer] = scores[reindeer]!!.inc()
            }
        }
        return scores
    }

    companion object {
        fun using(input: List<String>, raceTime: Int) = Day14(
            input.map { line ->
                val parts = line.split(" ")
                ReindeerData(speed = parts[3].toInt(), flightTime = parts[6].toInt(), parts[13].toInt())
            },
            raceTime
        )
    }

    data class ReindeerData(val speed: Int, val flightTime: Int, val restTime: Int) {
        private val cycleTime = flightTime + restTime

        fun distanceFlownIn(raceTime: Int) = speed * secondsFlyingDuring(raceTime)

        private fun secondsFlyingDuring(raceTime: Int) =
            raceTime / cycleTime * flightTime + min(flightTime, raceTime % cycleTime)

        fun distancesForEachSecondDuring(raceTime: Int): List<Int> =
            (1..raceTime).map { time -> distanceFlownIn(time) }
    }
}
