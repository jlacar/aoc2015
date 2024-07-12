package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

class Day07Test {

    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            """
            123 -> a
            433 -> a
            """.trimIndent().lines() to 433,

            """
            123 -> x
            NOT x -> a
            """.trimIndent().lines() to 65412,

            """
            456 -> y
            NOT y -> a
            """.trimIndent().lines() to 65079

        ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input -> $expected") {
                assertEquals(expected, Day07(input).part1())
            }
        }
    }

    @Nested
    inner class Solutions {

    }
}