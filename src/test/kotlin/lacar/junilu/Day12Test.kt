package lacar.junilu

import kotlinx.serialization.json.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

private val puzzleInput = readResource("day12").first()

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

            """
            { "a": [1, 2, 3], 
              "b": 4, 
              "c": "red"
            }
            """.trimIndent() to 10,

            """
            { "a": [1, 2, 3], 
              "b": 4, 
              "c": "yellow",
              "d": {
                "e": "red",
                "f": 5,
                "g": 6
              },
              "h": {
                "b": 7, 
                "c": "yellow",
                "d": [0, 1, 3]
              }
            }
            """.trimIndent() to 32,

        ).map { (input, expectedOutput) ->
            DynamicTest.dynamicTest("$input -> $expectedOutput") {
                assertEquals(expectedOutput, Day12(input).part1())
            }
        }

        @TestFactory
        fun `Part 2`() = listOf(
            """
            { "description": "has 'red' object value", 
              "a": [1, 2, 3], 
              "b": 4, 
              "c": "red"
            }
            """.trimIndent() to 0,

            """
            { "description": "no 'red' object value",
              "a": [1, 2, 3], 
              "b": 4, 
              "c": "yellow"
            }
            """.trimIndent() to 10,

            """
            { "description": "has 'red' in array, and in objects",
              "a": ["these should be summed", 1, 2, 3, "red"], 
              "b": 4, 
              "c": "yellow",
              "d": {
                "description": "these should not be summed",
                "e": "red",
                "f": 5,
                "g": 6
              },
              "h": {
                "b": 7, 
                "c": "yellow",
                "d": ["description: these should be summed", 0, 1, 3, "red"],
                "e": {
                  "description": "these should not be summed",
                  "i": 1,
                  "j": 55,
                  "k": "red"
                }
              }
            }
            """.trimIndent() to 21,

            ).map { (jsonString: String, expectedOutput) ->
            DynamicTest.dynamicTest("$jsonString -> $expectedOutput") {
                assertEquals(expectedOutput, Day12(jsonString).part2())
            }
        }
    }

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - SOLVED`() {
            assertEquals(111754, Day12(puzzleInput).part1())
        }

        @Test
        fun `Part 2 - SOLVED`() {
            assertEquals(65402, Day12(puzzleInput).part2())
        }
    }
}
