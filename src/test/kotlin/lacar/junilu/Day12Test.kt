package lacar.junilu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day12Test {
    @Nested
    inner class Samples {
        @TestFactory
        fun `Part 1`() = listOf(
            """{"a": [1,2,3]}""" to 6,
            """{"a":2,"b":4}""" to 6,
            "[[[3]]]" to 3,
            """{"a":{"b":4},"c":-1}""" to 3,
            """{"a":[-1,1]}""" to 0,
            """[-1,{"a":1}]""" to 0,
            "{}" to 0,
            "[]" to 0,
        ).map { (input, expectedOutput) ->
            DynamicTest.dynamicTest("$input -> $expectedOutput") {
                assertEquals(expectedOutput, Day12(input).part1())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(111754, Day12(readResource("day12").first()).part1())
        }
    }
}