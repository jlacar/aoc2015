package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day15")

class Day15Test {
    @Nested
    inner class Samples {
        @Test
        fun `Part 1 example`() {
            assertEquals(62842880,
                Day15.using(
                    """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3  
                    """.trimIndent().lines()
                    , 100
                ).part1()
            )
        }

        @Test
        fun `Part 2 example`() {
            assertEquals(57600000,
                Day15.using(
                    """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3  
                    """.trimIndent().lines()
                    , 100
                ).part2()
            )
        }

        @Test
        fun `try proportions`() {
            val props = proportions(3, 10).iterator()
            while (props.hasNext()) {
                println(props.next().joinToString(" "))
            }
        }
    }

    @Nested
    inner class Solution {

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(222870, Day15.using(puzzleInput, 100).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(117936, Day15.using(puzzleInput, 100).part2())
        }
    }
}