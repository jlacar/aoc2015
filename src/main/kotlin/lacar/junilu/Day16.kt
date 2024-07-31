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

    override fun part1() = auntSues.indexOfFirst { allHerThings ->
        allHerThings.haveQuantitiesThatMatch(traceAnalysis)
    } + 1

    override fun part2() = auntSues.indexOfFirst { allHerThings ->
        allHerThings.haveQuantitiesConsistentWith(traceAnalysis)
    } + 1

    companion object {
        fun using(input: List<String>) = Day16(auntSuesThingsFrom(input))

        private fun auntSuesThingsFrom(input: List<String>) = input.map { line ->
            toKeyValuePairMap(
                line.substringAfter(": "), itemDelimiter = ", ", keyValueDelimiter = ": ", String::toInt
            )
        }

        private fun Map<String, Int>.haveQuantitiesThatMatch(traceAnalysis: Map<String, Int>) =
            all { (key, value) -> traceAnalysis[key]!! == value }

        private fun Map<String, Int>.haveQuantitiesConsistentWith(traceAnalysis: Map<String, Int>) =
            all { (key, value) ->
                when (key) {
                    "cats", "trees" -> value > traceAnalysis[key]!!
                    "pomeranians", "goldfish" -> value < traceAnalysis[key]!!
                    else -> traceAnalysis[key]!! == value
                }
            }
    }
}