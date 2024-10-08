package lacar.junilu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

private val puzzleInput = readResource("day03").first()

class
Day03Test {

    @Nested
    inner class Solution {
        @Test
        fun part1() {
            assertEquals(2565, Day03(puzzleInput).housesSantaVisited())
        }

        @Test
        fun part2() {
            assertEquals(2639, Day03(puzzleInput).housesSantaOrRobotSantaVisited())
        }
    }

    @Nested
    inner class Examples {

        @ParameterizedTest(name = "{0} should visit {1} houses")
        @CsvSource(
            ">, 2",
            "^>v<, 4",
            "^v^v^v^v^v, 2",
        )
        fun part1(directions: String, expectedCount: Int) {
            assertEquals(expectedCount, Day03(directions).housesSantaVisited())
        }

        @ParameterizedTest(name = "{0} should visit {1} houses")
        @CsvSource(
            "^v, 3",
            "^>v<, 3",
            "^v^v^v^v^v, 11",
        )
        fun part2(directions: String, expectedCount: Int) {
            assertEquals(expectedCount, Day03(directions).housesSantaOrRobotSantaVisited())
        }
    }
}