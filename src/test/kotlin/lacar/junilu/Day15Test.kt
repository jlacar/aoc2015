package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day15")

class Day15Test {
    @Nested
    inner class Samples {}

    @Nested
    inner class Solution {

        @Test
        fun `Part 1 -`() {
            assertEquals(0, Day15.using(puzzleInput, 100).part1())
        }
    }
}