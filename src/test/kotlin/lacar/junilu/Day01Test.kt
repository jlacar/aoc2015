package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

private val puzzleInput = readResource("day01")[0]

class Day01Test {

    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1 - @TestFactory`() = listOf(
            "(())" to 0,
            "()()" to 0,
            "(((" to 3,
            "(()(()(" to 3,
            "))(((((" to 3,
            "())" to -1,
            "))(" to -1,
            ")))" to -3,
            ")())())" to -3
        ).map { (input, expectedLevel) ->
            DynamicTest.dynamicTest("$input should give level $expectedLevel") {
                assertEquals(expectedLevel, Day01(input).part1())
            }
        }

        @ParameterizedTest(name = "{0} should give level {1}")
        @CsvSource(
            "(()),     0",
            "()(),     0",
            "(((,      3",
            "(()(()(,  3",
            "))(((((,  3",
            "()),     -1",
            "))(,     -1",
            "))),     -3",
            ")())()), -3",
        )
        fun `Part 1 - @ParameterizedTest`(input: String, expectedLevel: Int) {
            assertEquals(expectedLevel, Day01(input).part1())
        }


        @ParameterizedTest(name = "{0} should give level {1}")
        @CsvSource(
            "),     1",
            "()()), 5",
            "()))())(()), 3",
        )
        fun `Part 2`(input: String, expectedPosition: Int) {
            assertEquals(expectedPosition, Day01(input).part2())
        }
    }

    @Nested
    inner class Solution {

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(280, Day01(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(1797, Day01(puzzleInput).part2())
        }
    }
}