package lacar.junilu

import kotlin.math.min

/**
 * AoC 2015 - Day 14: Reindeer Olympics
 */
class Day14(private val reindeerStats: List<ReindeerStats>, private val raceTime: Int) : Solution<Int> {

    override fun part1() = reindeerStats.maxOf { it.distanceFlownAt(raceTime) }

    override fun part2() = racePoints().maxOf { it.value }

    private fun racePoints(): Map<ReindeerStats, Int> =
        (1..raceTime).fold(reindeerStats.associateWith { 0 }) { points, second ->
            val leadDistance = reindeerStats.maxOf { it.distanceFlownAt(second) }
            points.map { (reindeer, currentScore) ->
                reindeer to currentScore + (if (reindeer.distanceFlownAt(second) == leadDistance) 1 else 0)
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
        fun distanceFlownAt(raceTime: Int) = speed * secondsFlyingAt(raceTime)

        private fun secondsFlyingAt(raceTime: Int) = fullFlightTime(raceTime) + partialFlightTime(raceTime)
        private fun fullFlightTime(raceTime: Int) = raceTime / cycleTime * flightTime
        private fun partialFlightTime(raceTime: Int) = min(flightTime, raceTime % cycleTime)

        fun isFlyingAt(raceTime: Int) = (raceTime % cycleTime) in (1..flightTime)
    }

    fun showFlightStatusThrough(time: Int) {
        for (t in 1..time) {
            println("After $t seconds,")
            showFlyingOrRestingAt(t)
        }
    }

    fun showFlyingOrRestingAt(tSeconds: Int) {
        reindeerStats.forEach {
            val status = if (it.isFlyingAt(tSeconds)) "flying" else "resting"
            println("\t$it is $status and has flown ${it.distanceFlownAt(tSeconds)} kms")
        }
    }
}

fun main() {
    Day14.using(readResource("day14"), 2503)
        .showFlyingOrRestingAt(2503)
//        .showFlightStatusThrough(2503)

    Day14(
        listOf(
            Day14.ReindeerStats(speed = 14, flightTime = 10, restTime = 127),
            Day14.ReindeerStats(speed = 16, flightTime = 11, restTime = 162)
        ), 1000)
//      .showFlyingOrRestingAt(11)
      .showFlightStatusThrough(140)
}