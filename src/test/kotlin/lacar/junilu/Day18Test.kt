package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day18")

class Day18Test {
    @Nested
    inner class Samples {
        @Test
        fun part1() {
            val config =
                """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..
                """.trimIndent().lines()
            assertEquals(4, Day18.using(config, 4).part1())
        }
        @Test
        fun part2() {
            val config =
                """
                ##.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####.#
                """.trimIndent().lines()
            assertEquals(17, Day18.using(config, 5).part2())
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(814, Day18.using(puzzleInput, 100).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(924, Day18.using(puzzleInput, 100).part2())
        }
    }
}