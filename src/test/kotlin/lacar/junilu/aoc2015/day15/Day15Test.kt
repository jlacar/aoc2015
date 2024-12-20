package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInputDay15 = readPuzzleInput("aoc2015/day15-gm")
private val puzzleInputDay15Gh = readPuzzleInput("aoc2015/day15-gh")

class Day15Test {
    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(222870, Day15.using(puzzleInputDay15, 100).highestCookieScore())
            assertEquals(21367368, Day15.using(puzzleInputDay15Gh, 100).highestCookieScore())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(117936, Day15.using(puzzleInputDay15, 100).highest500CalorieCookieScore())
            assertEquals(1766400, Day15.using(puzzleInputDay15Gh, 100).highest500CalorieCookieScore())
        }
    }

    @Nested
    inner class Examples {

        @Test
        fun `Highest cookie score`() {
            assertEquals(62842880,
                Day15.using(
                    """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3  
                    """.trimIndent().lines()
                    , 100
                ).highestCookieScore()
            )
        }

        @Test
        fun `Highest 500-calorie cookie score`() {
            assertEquals(57600000,
                Day15.using(
                    """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3  
                    """.trimIndent().lines()
                    , 100
                ).highest500CalorieCookieScore()
            )
        }
    }
}