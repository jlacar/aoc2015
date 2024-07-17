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
            DynamicTest.dynamicTest("$segments -> shortest is $expectedShortest") {
                assertEquals(3, Day09.using(segments.lines()).cities().size)
//                assertEquals(expectedShortest, Day09.using(segments.lines()).part1())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - `() {
            assertEquals(8, Day09.using(puzzleInput).cities().size)
        }

        @Test
        fun `Part 2 - `() {
            assertEquals(0, Day09.using(puzzleInput).part2())
        }
    }
}