package lacar.junilu

import java.util.Collections.swap

/**
 * AoC 2015 - Day 13: Knights of the Dinner Table
 */
class Day13(private val happiness: Map<String, Int>) : Solution<Int> {

    private val allAttendees = peopleIn(happiness.keys)

    override fun part1(): Int = allPossibleSeatingArrangements(allAttendees)
        .maxOf { arrangement -> happinessFor(arrangement) }

    override fun part2(): Int = allPossibleSeatingArrangements(allAttendees + "Me")
        .maxOf { arrangement -> happinessFor(arrangement) }

    private fun allPossibleSeatingArrangements(attendees: List<String>): List<List<String>> {
        val paths = mutableListOf<List<String>>()
        permutationsOf(attendees, 0, paths)
        return paths
    }

    private fun permutationsOf(people: List<String>, index: Int, soFar: MutableList<List<String>>) {
        if (index == people.lastIndex) soFar.add(people.toList())
        for (i in index..people.lastIndex) {
            swap(people, index, i)
            permutationsOf(people, index + 1, soFar)
            swap(people, i, index)
        }
    }

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

        fun peopleIn(input: Iterable<String>) = input.map { it.split(" ").first() }.distinct()
    }
}