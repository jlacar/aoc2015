package lacar.junilu

/**
 * AoC 2015 - Day 1: Not Quite Lisp
 */
class Day01(private val input: String) : Solution<Int>() {
    override fun part1() = input.fold(0) { floor, c -> floor + if (c == '(') 1 else -1 }

    override fun part2() = input
            .runningFold(0) { floor, c -> floor + if (c == '(') 1 else -1 }
            .indexOfFirst { it == -1 }
}
