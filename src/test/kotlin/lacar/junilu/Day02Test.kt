package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
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
                assertEquals(expectedArea, Day02(*dimensions).part1())
            }
        }
    }

}
