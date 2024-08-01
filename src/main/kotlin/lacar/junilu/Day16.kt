package lacar.junilu

/**
 * AoC 2015 - Day 16: Aunt Sue
 */
class Day16(private val auntSues: List<Map<String, Int>>) : Solution<Int> {

    private val traceAnalysis = """
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
        """.trimIndent().lines().associate { keyValuePair(it, ": ", String::toInt) }

    override fun part1() = auntSues.indexOfFirst { auntSueWhoseList ->
        auntSueWhoseList.hasEqualQuantitiesIn(traceAnalysis)
    } + 1

    override fun part2() = auntSues.indexOfFirst { auntSueWhoseList ->
        auntSueWhoseList.hasQuantitiesConsistentWith(traceAnalysis)
    } + 1

    companion object {
        fun using(input: List<String>) = Day16(auntSuesThingsFrom(input))

        private fun auntSuesThingsFrom(input: List<String>) = input.map { line ->
            toKeyValuePairMap(
                line.substringAfter(": "), itemDelimiter = ", ", keyValueDelimiter = ": ", String::toInt
            )
        }

        private fun Map<String, Int>.hasEqualQuantitiesIn(traceAnalysis: Map<String, Int>) =
            all { (compound, quantity) -> traceAnalysis[compound]!! == quantity }

        private fun Map<String, Int>.hasQuantitiesConsistentWith(traceAnalysis: Map<String, Int>) =
            all { (compound, quantity) ->
                when (compound) {
                    "cats", "trees" -> quantity > traceAnalysis[compound]!!
                    "pomeranians", "goldfish" -> quantity < traceAnalysis[compound]!!
                    else -> traceAnalysis[compound]!! == quantity
                }
            }
    }
}