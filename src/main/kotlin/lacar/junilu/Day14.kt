package lacar.junilu

import kotlin.math.min

/**
 * AoC 2015 - Day 14: Reindeer Olympics
 */
class Day14(private val reindeerStats: List<ReindeerStats>, private val raceTime: Int) : Solution<Int> {

    override fun part1() = reindeerStats.maxOf { it.distanceFlownIn(raceTime) }

    override fun part2(): Int {
        raceByPointSystem()
        return reindeerStats.maxOf { it.score }
    }

    private fun raceByPointSystem() {
        reindeerStats.forEach { it.resetScore() }
        (1..raceTime).forEach { raceSecond ->
            val maxDistance = reindeerStats.maxOf { it.distanceFlownIn(raceSecond) }
            reindeerStats.forEach { it.score(maxDistance, raceSecond) }
        }
    }

    companion object {
        fun using(input: List<String>, raceTime: Int) = Day14(
            input.map { line ->
                val parts = line.split(" ")
                ReindeerStats(speed = parts[3].toInt(), flightTime = parts[6].toInt(), parts[13].toInt())
            },
            raceTime
        )
    }

    data class ReindeerStats(val speed: Int, val flightTime: Int, val restTime: Int) {

        private val cycleTime = flightTime + restTime

        var score = 0

        fun resetScore() { score = 0 }

        fun score(distanceToBeat: Int, raceTime: Int) {
            if (distanceFlownIn(raceTime) >= distanceToBeat) score++
        }

        fun distanceFlownIn(raceTime: Int) = speed * secondsFlyingDuring(raceTime)

        private fun secondsFlyingDuring(raceTime: Int) =
            raceTime / cycleTime * flightTime + min(flightTime, raceTime % cycleTime)
    }
}
