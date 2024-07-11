package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day01Test {

    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1`() = listOf(
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
                assertEquals(expectedLevel, Day01(input, "sample input").part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            ")" to 1,
            "()())" to 5
        ).map { (input, expectedPosition) ->
            DynamicTest.dynamicTest("$input should give position $expectedPosition") {
                assertEquals(expectedPosition, Day01(input, "sample input").part2())
            }
        }
    }

    @Nested
    inner class GoogleAccountData {
        val input = readResource("day01")[0]

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(280, Day01(input, "Using google account data").part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            val position = Day01(input, "Using google account data").part2()

            assertEquals(1797, position)
        }
    }

}