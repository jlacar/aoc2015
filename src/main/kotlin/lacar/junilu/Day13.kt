package lacar.junilu

import java.util.Collections.swap

/**
 * AoC 2015 - Day 13: Knights of the Dinner Table
 */
class Day13(private val happiness: Map<String, Int>) : Solution<Int> {

    private val allAttendees = happiness.keys.map { it.split(" ").first() }.distinct()

    override fun part1(): Int = allAttendees.permutations()
        .maxOf { arrangement -> happinessFor(arrangement) }

    override fun part2(): Int = (allAttendees + "Me").permutations()
        .maxOf { arrangement -> happinessFor(arrangement) }

    private fun happinessFor(arrangement: List<String>): Int =
        arrangement.windowed(2).sumOf { (person1, person2) ->
            netHappinessFor(person1, person2)
        } + netHappinessFor(arrangement.first(), arrangement.last())

    private fun netHappinessFor(person1: String, person2: String) =
        (happiness["$person1 $person2"] ?: 0) + (happiness["$person2 $person1"] ?: 0)

    companion object {
        fun using(input: List<String>) = Day13(
            input.map { line ->
                val (name1, _, sign, units, name2) = line
                    .replace("happiness units by sitting next to ", "")
                    .replace(".", "")
                    .split(" ")
                "$name1 $name2" to (if (sign == "gain") 1 else -1) * units.toInt()
            }.toMap()
        )
    }
}