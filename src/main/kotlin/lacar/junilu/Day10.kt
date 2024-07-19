package lacar.junilu

class Day10(val input: String = "3113322113") : Solution<Int>() {
    override fun part1(): Int {
        TODO("Not implemented yet")
    }

    override fun part2(): Int {
        TODO("Not implemented yet")
    }

    fun say(input: String): String {
        val firstDigit = input.first()
        val sequences = input.drop(1).fold(mutableListOf(firstDigit.toString())) { acc, c ->
            if (acc.last().startsWith(c)) {
                val seq = acc.removeLast()
                acc.add(seq + c)
            } else {
                acc.add(c.toString())
            }
            acc
        }.also { println(it) }
        return sequences.map { "${it.length}${it.first()}"}.joinToString("")
    }
}