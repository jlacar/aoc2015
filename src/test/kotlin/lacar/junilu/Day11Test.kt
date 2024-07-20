package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day11Test {
    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            "abcdefgh" to "abcdffaa",
            "ghijklmn" to "ghjaabcc",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("Part 1: $input -> $expected") {
                assertEquals(expected, Day11(input).part1())
            }
        }

        @TestFactory
        fun `Rejects invalid passwords`() = listOf(
            "iijklmmn" to "i and l",
            "jjkllmno" to "o",
            "abbceffg" to "no 3 increasing letters",
            "abbcegjk" to "only one pair, bb",
            "abbbcgjk" to "no different overlapping pairs",
            "abbcdbbk" to "no different overlapping pairs",
        ).map { (password, reasonToReject) ->
            DynamicTest.dynamicTest("Reject '$password' because it has $reasonToReject") {
                assertFalse(Day11().isValid(password))
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - `() {
            assertEquals("", Day11().part1())
        }

        @Test
        fun `Part 2 - `() {
            assertEquals("", Day11().part2())
        }
    }
}