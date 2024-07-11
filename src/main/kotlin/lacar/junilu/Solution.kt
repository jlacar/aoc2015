package lacar.junilu

import kotlin.io.path.Path
import kotlin.io.path.readLines

interface Describable {
    val description: String
}

abstract class Solution : Describable {
    abstract fun part1() : Any
    abstract fun part2() : Any
}

class Checker(val solution: Solution, override val description: String) : Describable {
    fun checkPart1(expected: Any) {
        val desc = "${solution.description}, Part 1 ($description)"

    }

    fun checkPart2(expected: Any) {
        val desc = "${solution.description}, Part 2 ($description)"
    }

    fun check(description: String, expected: Any, partResult: (Solution) -> Any) {
        val actual = partResult(solution)
        check(expected == actual) {
            lazyMessage(description, expected, actual)
        }
    }
}

/**
 * Create a lazyMessage for check failures
 */
fun lazyMessage(description: String, expected: Any?, actual: Any?, extra: Any? = "-"): String =
    """FAILED $description
        |  expected   [$expected]
        |  but got    [$actual]
        |  $extra
    """.trimMargin()

fun readResource(name: String) = Path("src/main/resources/$name.txt").readLines()