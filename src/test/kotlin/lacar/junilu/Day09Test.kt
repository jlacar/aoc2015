package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("day09")

class Day09Test {
    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
            """.trimIndent() to 605,
        ).map { (segments, expectedShortest) ->
            DynamicTest.dynamicTest("$segments -> shortest route should be $expectedShortest") {
                assertEquals(expectedShortest, Day09.using(segments.lines()).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
            """.trimIndent() to 982,
        ).map { (segments, expectedLongest) ->
            DynamicTest.dynamicTest("$segments -> longest route should be $expectedLongest") {
                assertEquals(expectedLongest, Day09.using(segments.lines()).part2())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(141, Day09.using(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED `() {
            assertEquals(736, Day09.using(puzzleInput).part2())
        }
    }
}