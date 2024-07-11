package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day04Test {

    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1 examples`() = listOf(
            "abcdef" to 609043,
            "pqrstuv" to 1048970
        ).map { (input, expected) ->
            dynamicTest("$input -> $expected") {
                assertEquals(expected, Day04(input).part1())
            }
        }
    }

    @Nested
    inner class Solution {
        private val puzzleInput = "ckczppom"

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(117946, Day04(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(3938038, Day04(puzzleInput).part2())
        }
    }
}
