package lacar.junilu

/**
 * AoC 2015 - Day 16: Aunt Sue
 */
class Day16(private val auntSues: List<Map<String, Int>>) : Solution<Int> {

    private val traceAnalysis =
        """
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
        """.trimIndent().lines()
            .associate { keyValuePair(it, ": ", String::toInt) }

    override fun part1() = auntSues.indexOfFirst { allHerThings ->
        allHerThings.haveMatchingQuantitiesIn(traceAnalysis)
    } + 1

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>) = Day16(auntSuesThingsFrom(input))

        private fun auntSuesThingsFrom(input: List<String>) = input.map { line ->
            toKeyValuePairMap(
                line.substringAfter(": "),
                itemDelimiter = ", ",
                keyValueDelimiter = ": ",
                String::toInt
            )
        }

        private fun Map<String, Int>.haveMatchingQuantitiesIn(traceAnalysis: Map<String, Int>) =
            all { traceAnalysis[it.key]!! == it.value }
    }

    // SCRATCH AREA
    fun scratch() {
        showAnalysis()
    }

    private fun showAnalysis() = println("Analysis $traceAnalysis")

}

fun main() {
    Day16(listOf()).scratch()
}