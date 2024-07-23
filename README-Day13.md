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

In retrospect, there is one thing missing from the story that the code tells: the "all attendees" part is hidden in the implementation of the `allPossibleSeatingArrangements()`. Later on, Part 2 will show that this design choice created tighter coupling with the input data. 

The `happinessFor()` function had to account for all the consecutive pairs in each list of names it was passed as well as an extra pair for the people at the ends of each list. Hence an additional term was needed to account for the extra pairing:

    private fun happinessFor(arrangement: List<String>): Int =
        arrangement.windowed(2).sumOf { (person1, person2) ->
            netHappinessFor(person1, person2)
        } + netHappinessFor(arrangement.first(), arrangement.last())

## Part 2

Everything in Part 2 is the same as in Part 1 except now we have to include "Me" in the attendee list.

This now shows that the choice to reference `allAttendees` directly from `allPossibleSeatingArrangements()` created tight coupling in the design.

We need to loosen up this coupling so we introduce an `attendees` parameter. Now `part1()` can be called with `allAttendees` and `part2()` can be called with `allAttendees + "Me"`. 

Initially, however, I made `allAttendees` the default value for the `attendees` parameter. This kept the call to `part1()` unchanged.

## Finishing touches

The problem with using a default parameter value was that the calls to `part1()` and `part2()` were not symmetrical. That is, `part1()` was called without a parameter whereas `part2()` was called with one. The story told by the `part2()` call seemed more complete and comprehensible  than the story told by the `part1()` call:

    override fun part1(): Int = allPossibleSeatingArrangements()
        .maxOf { arrangement -> happinessFor(arrangement) }

    override fun part2(): Int = allPossibleSeatingArrangements(allAttendees + "Me")
        .maxOf { arrangement -> happinessFor(arrangement) }

So I decided to remove the default parameter value and explicitly pass it at the point of call. Having it in the actual call to `part()` improves the symmetry between the two parts and it makes the story told by the code more complete and explicit.

    override fun part1(): Int = allPossibleSeatingArrangements(allAttendees)
        .maxOf { arrangement -> happinessFor(arrangement) }

    override fun part2(): Int = allPossibleSeatingArrangements(allAttendees + "Me")
        .maxOf { arrangement -> happinessFor(arrangement) }

Here's the [final solution](https://github.com/jlacar/aoc2015/blob/c86224fdb525ef98f11d61fd49d08cb1e55dbcbb/src/main/kotlin/lacar/junilu/Day13.kt) as of this writing.
