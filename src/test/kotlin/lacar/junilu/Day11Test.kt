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
            "aabccmno" to "o",
            "abbceffg" to "no 3 increasing letters",
            "abbcefgx" to "only one pair, bb",
            "abbbcxyz" to "no different overlapping pairs",
            "abbcdbbk" to "no different overlapping pairs",
            "abbcdbbA" to "uppercase letter 'A'",
        ).map { (password, reasonToReject) ->
            DynamicTest.dynamicTest("Reject '$password' because it has $reasonToReject") {
                assertFalse(Day11().isValid(password))
            }
        }

        @TestFactory
        fun `Calculates next password increment correctly`() = listOf(
            "abcdefgh" to "abcdffaa",
            "ghijklmn" to "ghjaabcc",
        ).map { (password, expectedNext) ->
            DynamicTest.dynamicTest("$password incr is $expectedNext") {
                assertEquals(expectedNext, Day11(password).part1())
            }
        }

        @TestFactory
        fun `Accepts valid passwords`() = listOf(
            "bbjjqqrs",
        ).map { password ->
            DynamicTest.dynamicTest("'$password' is valid") {
                assertTrue(Day11().isValid(password))
            }
        }

        @TestFactory
        fun `Increments password`() = listOf(
            "a" to "b",
            "z" to "a",
            "czz" to "daa",
            "wxyzz" to "wxzaa",
            "azwzzz" to "azxaaa"
        ).map { (password, expected) ->
            DynamicTest.dynamicTest("'$password' incr to '$expected'") {
                assertEquals(expected, Day11().nextIncrement(password))
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals("cqjxxyzz", Day11().part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals("cqkaabcc", Day11("cqjxxyzz").part1())
        }
    }
}