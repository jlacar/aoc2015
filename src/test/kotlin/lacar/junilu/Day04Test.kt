package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day04Test {

    private val puzzleInput = "ckczppom"

    @Nested
    inner class Samples {

        @TestFactory
        fun `Part 1 examples`() = listOf(
            "abcdef" to 609043,
            "pqrstuv" to 1048970
        ).map { (input, expected) ->
            dynamicTest("$input -> $expected") {
                assertEquals(expected, Day04(input).part1())
            }
        }
    }
}