package lacar.junilu

class Day10(val input: String = "3113322113") : Solution<Int>() {
    override fun part1(): Int = lookSay(40)
    override fun part2(): Int = lookSay(50)

    private fun lookSay(n: Int): Int = (1..n).fold(input) { look: String, _: Int -> say(look) }.length

    private val digitSequences = """(.)\1*""".toRegex()

    fun say(input: String) = digitSequences
        .findAll(input, 0)
        .map { matches -> matches.groupValues.first() }
        .map { sameDigits -> "${sameDigits.length}${sameDigits.first()}" }
        .joinToString("")
}

/**
 * Ugly implementation

//        val firstDigit = input.first()
//        val sequences = input.drop(1).fold(mutableListOf(firstDigit.toString())) { acc, c ->
//            if (acc.last().startsWith(c)) {
//                val seq = acc.removeLast()
//                acc.add(seq + c)
//            } else {
//                acc.add(c.toString())
//            }
//            acc
//        }
//        // .also { println(it) }
//        return sequences.map { "${it.length}${it.first()}"}.joinToString("")

 */