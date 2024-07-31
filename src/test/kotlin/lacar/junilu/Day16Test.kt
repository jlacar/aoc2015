package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val puzzleInput = readResource("day16")

class Day16Test {
    @Test
    fun `Part 1 - SOLVED`() {
        assertEquals(40, Day16.using(puzzleInput).part1())
    }
}