package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day14")

class Day14Test {
    @Nested
    inner class Samples {
        @Test
        fun `Comet wins`() {
            val sample = Day14(listOf(
                Day14.Reindeer(speed = 14, flightTime = 10, restTime = 127),
                Day14.Reindeer(speed = 16, flightTime = 11, restTime = 162)
            ), 1000)
            assertEquals(1120, sample.part1())
        }

        @Test
        fun `Dancer wins`() {
            val sample = Day14(listOf(
                Day14.Reindeer(speed = 14, flightTime = 10, restTime = 127),
                Day14.Reindeer(speed = 16, flightTime = 11, restTime = 162)
            ), 1000)
            assertEquals(689, sample.part2())
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(2660, Day14.using(puzzleInput, 2503).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(1256, Day14.using(puzzleInput, 2503).part2())
        }
    }
}