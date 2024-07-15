package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("day08")

class Day08Test {
    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            """
            ""
            """.trimIndent() to (2 - 0),

            """
            "v\xfb\"lgs\"kvjfywmut\x9cr"
            """.trimIndent() to (28 - 18),

            """
            "h\\"
            """.trimIndent() to (5 - 2),

            """
            "abc"
            """.trimIndent() to (5 - 3),

            """
            "aaa\"aaa"
            "aaa\"aa\"aa"
            """.trimIndent() to (10 - 7 + 13 - 9),

            """
            "\x27"
            """.trimIndent() to (6 - 1),

            """
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
            """.trimIndent() to 12

        ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input -> $expected") {
                assertEquals(expected, Day08(input.lines()).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            """
            ""
            """.trimIndent() to (6 - 2),

            """
            "abc"
            """.trimIndent() to (9 - 5),

            """
            "aaa\"aaa"
            """.trimIndent() to (16 - 10),

            """
            "\x27"
            """.trimIndent() to (11 - 6),

        ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input -> $expected") {
                assertEquals(expected, Day08(input.lines()).part2())
            }
        }

    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(1342, Day08(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(2074, Day08(puzzleInput).part2())
        }
    }

}