package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day03")

class Day03Test {
    @Nested
    inner class Samples {

        @Test
        fun `Part 1`() {
            val input = listOf(
                ">" to 2,
                "^>v<" to 4,
                "^v^v^v^v^v" to 2
            )

            input.map { (directions, expectedCount) ->
                dynamicTest("$directions should visit $expectedCount") {
                    assertEquals(expectedCount, Day03(directions).part1())
                }
            }
        }

    }

    @Nested
    inner class Solution {

    }
}