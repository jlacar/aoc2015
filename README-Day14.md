# Day 14: Reindeer Olympics

The solutions to this problem revolve mostly around integer math. 

Part 1 was fairly straightforward, although it wasn't until my initial solution failed for part 2 that I realized the formula I used was not entirely correct.

My first solution for Part 2 was very imperative and it took quite a few refactorings to get to a more functional version.

## Solution Base

The foundation of the solution lies in the `ReindeerStats` [data class](https://kotlinlang.org/docs/data-classes.html) which encapsulates all the relevant information and basic calculations needed to solve the problems, namely the speed, flying time, and resting time. I excluded the reindeer's name because it was not relevant to any of the solutions.

The solutions use maps quite a bit so the data class is a natural fit because it has built-in `equals()` and `hashCode()` implementations.

## Part 1 - What's the farthest distance flown?

In this part, we're required to calculate the distance flown in a specified time. The tricky part is that each reindeer can only fly for so long (their flying time) before they have to rest (their resting time). Therefore, the total distance they cover in a given number of seconds will be a function of both the flying and resting time. I chose to call the sum of these two times as the "cycle time".

My first cut for calculating the distance covered was [this](https://github.com/jlacar/aoc2015/blob/11649abfa9f33f57575b7f1552b59cc0062d700f/src/main/kotlin/lacar/junilu/Day14.kt):

        fun distanceFlownIn(raceTime: Int): Int {
            val cycles = raceTime / cycleTime
            val remainder = raceTime % cycleTime
            return speed * (cycles + if (remainder >= flightTime) 1 else 0) * flightTime
        }

I put this function in the `ReindeerStats` data class because it has all the information needed to calculate the result. I would later learn that this calculation was not quite right but was good enough for my puzzle input. In short, I incorrectly calculated the correct answer.

## Part 2 - What's the highest score?

In this part, we need to calculate the winner based on points given based on the distance traveled at every second throughout the race. Reindeer get a point for every second they have traveled the farthest, that is, they are in the lead. If multiple reindeer are tied for the lead on any given second, they each get a point. This means we need to calculate the distance flown by each reindeer for every second of the race.

The first surprise I got was finding out that the `distanceFlownIn()` implementation that gave me a correct answer for part 1 was actually incorrect. After some analysis, I realized that the formula I was using was based on the number of _full segments_ of flight time rather than the actual number of seconds in flight. I guess I just got lucky with my puzzle input and got the correct answer anyway. That luck didn't hold out for Part 2 though so I had to analyze what was going on and fix it.

This is the formula that ended up working for both Parts 1 & 2:

        fun distanceFlownIn(raceTime: Int) = speed * secondsFlyingDuring(raceTime)

        private fun secondsFlyingDuring(raceTime: Int) =
            raceTime / cycleTime * flightTime + min(flightTime, raceTime % cycleTime)

I suppose I could refactor `secondsFlyingDuring(raceTime)` to make it clearer

        private fun secondsFlyingDuring(raceTime: Int) =
            secondsInfullFlight(raceTime) + secondsInPartialFlight(raceTime)

        private fun fullFlight(
## Refactoring from Imperative to Functional

TBD