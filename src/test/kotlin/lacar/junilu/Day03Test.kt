package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("day03").first()

class Day03Test {
    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1`() = listOf(
            ">" to 2,
            "^>v<" to 4,
            "^v^v^v^v^v" to 2
        ).map { (directions, expectedCount) ->
            dynamicTest("$directions should visit $expectedCount houses") {
                assertEquals(expectedCount, Day03(directions).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            "^v" to 3,
            "^>v<" to 3,
            "^v^v^v^v^v" to 11
        ).map { (directions, expectedCount) ->
            dynamicTest("$directions should visit $expectedCount houses") {
                assertEquals(expectedCount, Day03(directions).part2())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(2565, Day03(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(2639, Day03(puzzleInput).part2())
        }
    }
}