package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day10Test {
    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1`() = listOf(
            "211" to "1221",
            "1" to "11",
            "11" to "21",
            "21" to "1211",
            "1211" to "111221",
            "111221" to "312211"
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input -> $expected") {
                assertEquals(expected, Day10(input).say(input))
            }
        }
    }


    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(329356, Day10().part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(4666278, Day10().part2())
        }
    }
}