package lacar.junilu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class UtilTest {

    @TestFactory
    fun `MD5 Hash`() = listOf(
            "frog" to   "938c2cc0dcc05f2b68c4287040cfcf71",
            "Junilu" to "c3463e3c960bf463e79d2ab04e3758a6",
            "Cow Chicken Horse Duck" to "51561dcfe0c5defd6d0663c29726ce96",
            "123 Main St." to "ce964d4193453e65ebf2d746db301507"

        ).map { (s, expected) ->
            DynamicTest.dynamicTest("MD5 hash of $s ($expected)") {
                assertEquals(expected, s.md5())
            }
        }

    @TestFactory
    fun `permutations examples`() = listOf(
        emptyList<String>() to 0,
        listOf("oneThing") to 1,
        listOf('0', '1') to 2,
        "abc".toList() to 6,
        listOf(1, 2, 3, 4, 5) to 120,
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) to 3_628_800
    ).map { (input, expectedSize) ->
        DynamicTest.dynamicTest("$input should have $expectedSize permutations") {
            assertEquals(expectedSize, input.permutations().size)
        }
    }

    @TestFactory
    fun `combinations examples`() = listOf(
        listOf(1, 2, 3, 4, 5) to 31,
    ).map { (aList, expectedSize) ->
        DynamicTest.dynamicTest("$aList should have $expectedSize combinations") {
            assertEquals(expectedSize, allCombinations(aList).also { it.println() }.size)
        }
    }

    private fun allCombinations(aList: List<Any>) =
        (1..aList.size).flatMap { chunkSize -> aList.combinations(chunkSize) }
}