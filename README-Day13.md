# Day 13: Knights of the Dinner Table

Day 13 problem is another one that involves calculating permutations. The core of the solution here is very similar to the one for Day 9 but instead of paths through a list of cities, we have to get all the different seating arrangements of people around a round table, hence the title.

## Parsing input

This is another case where I used a companion object with a factory function, `using()`, that creates an instance of the `Day13` solution class initialized with the parsed input.

Parsing the input was fairly straightforward. I used `String.replace()` to delete a big chunk of text in the middle of each line and the "." at the end. I then had to translate "gain" and "lose" to the proper sign for the happiness units. I used `"$person1 $person2"` as the key to the happiness units for each pairing.

Getting the set of attendee names required iterating over the keys of the happiness map, and getting all the distinct values in it. This list of unique names was then passed to function that calculated all possible permutations of the names in the list.

## Part 1

Using well-chosen names, the code reads like a very straightforward telling of the requirement: get the maximum happiness of all possible seating arrangements of all the attendees.

    override fun part1(): Int = allPossibleSeatingArrangements()
        .maxOf { arrangement -> happinessFor(arrangement) }

The `happinessFor()` function had to account for all the consecutive pairs in each list of names it was passed as well as an extra pair for the people at the ends of each list. Hence an additional term was needed to account for the extra pairing:

    private fun happinessFor(arrangement: List<String>): Int =
        arrangement.windowed(2).sumOf { (person1, person2) ->
            netHappinessFor(person1, person2)
        } + netHappinessFor(arrangement.first(), arrangement.last())

## Part 2

Part 2 simply added "Me" to the list of attendees. The rest of the logic used in solving Part 1 was the same. 

The only refactoring I needed to make Part 2 easier to implement was assigning a default value for the argument for `allPossibleSeatingArrangements()`. This default, which was the list of attendees picked out of the happiness map keys, would be used for the Part 1 solution. For Part 2, I'd just have to add "Me" to the original attendee list: `allAttendees + "Me"`

## Finishing touches


