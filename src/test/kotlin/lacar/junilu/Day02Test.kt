package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day02Test {

    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1`() = listOf(
            intArrayOf(2, 3, 4) to 58,
            intArrayOf(1, 1, 10) to 43
        ).map { (dimensions, expectedArea) ->
            dynamicTest("${dimensions.joinToString("x")} should have area $expectedArea") {
                assertEquals(expectedArea, Day02(listOf(dimensions)).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            intArrayOf(2, 3, 4) to 34,
            intArrayOf(1, 1, 10) to 14
        ).map { (dimensions, expectedArea) ->
            dynamicTest("${dimensions.joinToString("x")} should have ribbon $expectedArea") {
                assertEquals(expectedArea, Day02(listOf(dimensions)).part2())
            }
        }

    }

    @Nested
    inner class Solution {
        private val boxDimensions = readResource("day02").map { line ->
            line.split("x").map { it.toInt() }.toIntArray()
        }

        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(1588178, Day02(boxDimensions).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(3783758, Day02(boxDimensions).part2())
        }
    }

}
