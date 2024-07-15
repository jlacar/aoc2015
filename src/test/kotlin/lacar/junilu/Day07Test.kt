package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("day07")

class Day07Test {

    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            """
            123 -> a
            """.trimIndent().lines() to 123,

            """
            x -> a
            123 -> x
            """.trimIndent().lines() to 123,

            """
            NOT x -> a
            123 -> x
            """.trimIndent().lines() to 65412,

            """
            NOT y -> a
            456 -> y
            """.trimIndent().lines() to 65079,

            """
            x AND y -> a
            123 -> x
            456 -> y
            """.trimIndent().lines() to 72,

            """
            1 AND x -> a
            123 -> x
            """.trimIndent().lines() to 1,

            """
            1 AND x -> a
            456 -> x
            """.trimIndent().lines() to 0,

            """
            x OR y -> a
            123 -> x
            456 -> y
            """.trimIndent().lines() to 507,

            """
            x LSHIFT 2 -> a
            123 -> x
            """.trimIndent().lines() to 492,

            """
            y RSHIFT 2 -> a
            456 -> y
            """.trimIndent().lines() to 114,

            ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input -> $expected") {
                assertEquals(expected, Day07(input).part1())
            }
        }
    }

    @Nested
    inner class Solutions {

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(3176, Day07(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(14710, Day07(puzzleInput).part2())
        }
    }
}