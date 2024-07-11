package lacar.junilu

class Day01(val input: String) : Solution() {
    override fun part1(): Int = input.count { it == '(' } - input.count { it == ')' }

    private fun delta(c: Char) = if (c == '(') 1 else -1

    override fun part2(): Int =
        input
            .runningFold(0) { acc: Int, c: Char -> acc + delta(c) }
            .indexOfFirst { it == -1 }
}
