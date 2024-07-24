package lacar.junilu

import kotlin.math.min

/**
 * AoC 2015 - Day 14: Reindeer Olympics
 */
class Day14(private val reindeerStats: List<ReindeerStats>, private val raceTime: Int) : Solution<Int> {

    override fun part1() = reindeerStats.maxOf { it.distanceFlownIn(raceTime) }

    override fun part2() = racePoints().maxOf { it.value }

    private fun racePoints(): Map<ReindeerStats, Int> =
        (1..raceTime).fold(reindeerStats.associateWith { 0 }) { points, second ->
            val leadDistance = reindeerStats.maxOf { it.distanceFlownIn(second) }
            points.map { (reindeer, currentScore) ->
                reindeer to currentScore + (if (reindeer.distanceFlownIn(second) == leadDistance) 1 else 0)
            }.toMap()
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

        fun distanceFlownIn(raceTime: Int) = speed * secondsFlyingDuring(raceTime)

        private fun secondsFlyingDuring(raceTime: Int) =
            raceTime / cycleTime * flightTime + min(flightTime, raceTime % cycleTime)
    }
}
