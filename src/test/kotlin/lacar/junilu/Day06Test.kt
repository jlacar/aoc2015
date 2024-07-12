package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("Day06")

class Day06Test {

    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            "turn on 0,0 through 999,999".lines() to 1_000_000,

            """
            turn on 499,0 through 500,999
            turn off 499,499 through 500,500
            turn off 498,0 through 498,999""".trimIndent().lines()
            to 1_996,

            "toggle 0,0 through 1,1".lines() to 4,

            "toggle 0,0 through 999,999".lines() to 1_000_000,

        ).map { (input, expected) ->
            DynamicTest.dynamicTest("Part 1: $input") {
                assertEquals(expected, Day06(input).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            "turn on 0,0 through 0,0".lines() to 1,
            "toggle 0,0 through 0,0".lines() to 2,
            "toggle 0,0 through 999,999".lines() to 2_000_000,

        ).map { (input, expected) ->
            DynamicTest.dynamicTest("Part 1: $input -> $expected") {
                assertEquals(expected, Day06(input).part2())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(543903, Day06(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(14687245, Day06(puzzleInput).part2())
        }
    }
}