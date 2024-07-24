package lacar.junilu

class Day14(private val reindeerData: List<ReindeerData>, private val raceTime: Int) : Solution<Int> {

    override fun part1() = reindeerData.maxOf { it.distanceFlownIn(raceTime) }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun printReindeer() {
        reindeerData.forEach { println(it.distanceFlownIn(1000)) }
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
        fun distanceFlownIn(raceTime: Int): Int {
            val cycles = raceTime / cycleTime
            val remainder = raceTime % cycleTime
            return speed * (cycles + if (remainder >= flightTime) 1 else 0) * flightTime
        }
    }
}
