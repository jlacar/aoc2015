package lacar.junilu

/**
 * AoC 2015 - Day 1: Not Quite Lisp
 *
 * https://adventofcode.com/2015/day/1
 */
class Day01(private val input: String) : Solution<Int> {
    override fun part1() = input.fold(0, nextFloor)

    override fun part2() = input.asSequence().runningFold(0, nextFloor).indexOfFirst { it == -1 }

    private val nextFloor: (Int, Char) -> Int = { currentFloor, direction ->
        currentFloor + if (direction == '(') 1 else -1
    }
}