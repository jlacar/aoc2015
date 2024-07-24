# Day 14: Reindeer Olympics

The solutions to this problem revolve mostly around integer math. 

Part 1 was fairly straightforward, although it wasn't until my initial solution failed for part 2 that I realized the formula I used wasn't entirely correct.

My first solution for Part 2 was very imperative and it took quite a few refactorings to get to a more functional version.

The foundation of the solution is laid out in the `ReindeerStats` data class which encapsulates all the data and the basic calculations needed to solve the problems. I decided to exclude the reindeer names from the class as they were inconsequential to the solutions because we're not asked to name the winning reindeer. I simply relied on the built-in equality logic of a Kotlin data class.

## Part 1 - Which reindeer flies the farthest

## Part 2 - Which reindeer has the highest score

## Refactoring from Imperative to Functional

