package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("Day05")

class Day05Test {
    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1 - nice`() = listOf(
            "ugknbfddgicrmopn",
            "aaa"
        ).map { sample ->
            DynamicTest.dynamicTest("$sample is nice") {
                assertEquals(1, Day05(listOf(sample)).part1())
            }
        }

        @TestFactory
        fun `Part 1 - naughty`() = listOf(
            "jchzalrnumimnmhp",
            "haegwjzuvuyypxyu",
            "dvszwmarrgswjxmb"
        ).map { sample ->
            DynamicTest.dynamicTest("$sample is naughty") {
                assertEquals(0, Day05(listOf(sample)).part1())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(258, Day05(puzzleInput).part1())
        }
    }
}