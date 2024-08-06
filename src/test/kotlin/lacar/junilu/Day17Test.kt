package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day17")

class Day17Test {

    @Nested
    inner class Samples {
        private val containers = listOf(20, 15, 10, 5, 5)

        @Test
        fun part1() {
            assertEquals(4, Day17(containers, 25).part1())
        }

        @Test
        fun part2() {
            assertEquals(3, Day17(containers, 25).part2())
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(1638, Day17.using(puzzleInput, 150).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(17, Day17.using(puzzleInput, 150).part2())
        }
    }
}