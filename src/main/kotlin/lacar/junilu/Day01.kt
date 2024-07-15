package lacar.junilu

/**
 * AoC 2015 - Day 1: Not Quite Lisp
 */
class Day01(private val input: String) : Solution<Int>() {
    override fun part1() = input.count { it == '(' } - input.count { it == ')' }

    private fun delta(c: Char) = if (c == '(') 1 else -1

    override fun part2() =
        input
            .runningFold(0) { acc: Int, c: Char -> acc + delta(c) }
            .indexOfFirst { it == -1 }
}
