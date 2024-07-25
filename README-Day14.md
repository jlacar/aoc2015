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

In this part, we need to calculate the winner based on points given for having the lead at any point during the race. Reindeer get a point for every second they are in the lead. If multiple reindeer are tied for the lead on any given second, they each get one point. This means we need to calculate the distance flown by each reindeer at every second of the race.

This is where I found out that the `distanceFlownIn()` implementation from part 1 was actually incorrect, despite having earned a gold star for the correct answer. After some analysis, I realized that the formula I was using was based on the number of _full segments_ of flight time rather than the actual number of seconds in flight. I suppose I just got lucky with my puzzle input and got the correct answer anyway. That luck didn't hold out for Part 2 though so I had to figure out what was wrong and fix it.

As mentioned earlier, the mistake was that the calculation I used was based on the number of cycles flown at any given time during the race. This would only work if the eventual winner was at rest at the end of the race. Otherwise, the calculation could potentially give incorrect results, depending on the configurations involved.

The correct approach was to work out how many seconds each reindeer has been flying at the specified point in time. This is the formula that ended up working for both Parts 1 & 2:

        fun distanceFlownIn(raceTime: Int) = speed * secondsFlyingDuring(raceTime)

        private fun secondsFlyingDuring(raceTime: Int) =
            raceTime / cycleTime * flightTime + min(flightTime, raceTime % cycleTime)

Using this formula, I got correct answers for both Part 2 and Part 1. I would later refactor this for clarity. More on that later.

## Refactoring from Imperative to Functional

My [initial solution](https://github.com/jlacar/aoc2015/blob/4f792e594600ae2051bd0137781cf90ba8cbafeb/src/main/kotlin/lacar/junilu/Day14.kt) to simulate a race and assign points to the lead reindeer was very much done in the imperative style. It worked but it seemed a little too long and somewhat complicated to me, especially for a Kotlin program. Here's the code with some markers.

    override fun part2(): Int {
        // [1]
        val scores = raceByPointSystem()

        // [2]
        return scores.values.maxOf { it }
    }

    private fun raceByPointSystem(): Map<ReindeerData, Int> {
        // [3]
        val scores = reindeerData.associateWith { 0 }.toMutableMap() 

        // [4]
        val distances = reindeerData.associateWith { it.distancesForEachSecondDuring(raceTime) }

        // [5]
        (0 until raceTime).forEach { i ->

            // [6]
            val maxDistanceSoFar = distances.maxOf { (_, progress) -> progress[i] }

            // [7]
            distances.forEach { (reindeer, distances) ->

                // [8]
                if (distances[i] == maxDistanceSoFar) scores[reindeer] = scores[reindeer]!!.inc()
            }
        }

        // [9]
        return scores
    }

    ...

    [10]
    // in ReindeerData:
    fun distancesForEachSecondDuring(raceTime: Int): List<Int> =
        (1..raceTime).map { time -> distanceFlownIn(time) }
    
This was my thought process in coming up with this:

[1] Create a `raceByPointSystem()` function to encapsulate the logic for the Part 2 race rules.

[2] The highest score in the collection returned would be the answer to Part 2.  

These two statements made `part2()` a reasonably well-composed function that stated the intent of the code at a very high level of abstraction.

Inside the `raceByPointSystem()` function, 

[3] Create a `Map<ReindeerStats, Int>` to track points earned by each reindeer

[4] Create a `Map<ReindeerStats, List<Int>>` to track distances traveled by each reindeer throughout the race. A good portion of work is done at [10] in the `distancesForEachSecondDuring(raceTime)` function, which called on each reindeer to generate a list of distances it traveled for each second of the race. The function signature clearly states that intent.

[5] Use a range to iterate over the seconds in the race. For each second, do [6] to [8]

[6] Using parameter destructuring, we extract the list of distances into the `progress` variable. We ignore the key value because we don't need it for this step. We use the `progress` variable to get the maximum distance traveled at the point in time represented by the variable `i`. 

That is, at i = 10 for example, the maximum of all the values at index 10 in the lists of distances will be calculated. This is the distance the leading reindeer has traveled at that point in time. We assign that to the `maxDistanceSoFar` variable. 

[7] Having found the lead reindeer's distance traveled, we then iterate over the list of distances again and see which reindeer is/are in the lead at that point. Now we do need the map key and assign it to `reindeer`. 

[8] We use `reindeer` to find the correct reindeer in the `scores` map to increment the score of each one that has traveled `maxDistanceSoFar` at that point.

[9] Finally, we return the accumulated scores to the caller, `part2()`.

When I'm trying to refactor from imperative style to functional style code, I often end up using the `fold()` function which comes with almost every class that can be iterated over in Kotlin. The `fold()` function provides a way to eliminate the need to mutate variables and maintain state.

I created [a branch](https://github.com/jlacar/aoc2015/blob/refactor-day14-pt2-to-functional/src/main/kotlin/lacar/junilu/Day14.kt) to capture the series of refactoring moves I made to transform this into functional-style code. 

I think the main things I look for when considering a refactoring toward a more functional style are:
1. Is there iteration involved? Can it be replaced by some kind of map/reduce operation?
2. Is there some kind of accumulation of calculated values involved? Can we use a fold operation instead?
3. Is there some kind of selection involved? Can we use functions that take predicates?

In this case, it was a 'Yes' to all three. This strongly suggested that a `fold()` operation might help simplify the logic and make the code more functional. That's exactly how I ended up with this implementation:

    override fun part2() = racePoints().maxOf { it.value }

    private fun racePoints(): Map<ReindeerStats, Int> =
        (1..raceTime).fold(reindeerStats.associateWith { 0 }) { points, second ->
            val distances = reindeerStats.associateWith { it.distanceFlownAt(second) }
            val leadersDistance = distances.values.max()
            points.map { (reindeer, points) ->
                reindeer to if (distances[reindeer] == leadersDistance) points + 1 else points
            }.toMap()
        }

    // ...

    data class ReindeerStats(val speed: Int, val flightTime: Int, val restTime: Int) {
        private val cycleTime = flightTime + restTime
        fun distanceFlownAt(raceTime: Int) = speed * secondsFlyingAt(raceTime)

        private fun secondsFlyingAt(raceTime: Int) = fullFlightTime(raceTime) + partialFlightTime(raceTime)
        private fun fullFlightTime(raceTime: Int) = raceTime / cycleTime * flightTime
        private fun partialFlightTime(raceTime: Int) = min(flightTime, raceTime % cycleTime)
        
    }

Now `part2()` consists of a single expression to calculate the race points and get the maximum value.

In `racePoints()`, we use `fold()` on the range of seconds in the race, `(1..raceTime)`, to do the distance and point awarding calculations for every second. 

We initialize`fold()` with a map created with `reindeerStats.associatedWith { 0 }` which is essentially a scoreboard for all the reindeer with each starting with a score of `0`. 

We use the name `points` for the fold accumulator variable and `second` for the iteration element variable.

The first statement in the `fold()` body creates a map of reindeer associated with their respective distances flown at the given second of the race. The we capture maximum of those distances in `leadersDistance`. 

Next, we iterate over the scoreboard and award a point to any reindeer that is at the lead distance.

The `.toMap()` at the end of the `points.map { ... }` expression is necessary because the last statement in the fold body must evaluate to the same type as the expression used to initialize the `fold()` operation.

In the `ReindeerStats` data class, I extracted the expressions that represent the full flight segment time and the partial flight segment time that goes into calculating the result of `secondsFlyingAt()`. 

We need to consider that at any given second, a reindeer could be in the middle of flying but hasn't yet reached their maximum `flightTime`. For example, if a reindeer had a `flightTime` of `10` and a `restTime` of `30`, then at 45 seconds into the race, they would be in the "flying" part of their second fly/rest cycle. That is, they would have flown for 10 seconds, rested for 30 seconds, then flown for 5 seconds with another 5 seconds of flying left. 

Extracting the expressions for full flight time and partial flight time makes `secondsFlyingAt()` a fully composed function which I think improves the readability of the code.

## To refactor or not to refactor?

Some people might look at the resulting code and think that I went to far in refactoring/extracting functions/methods. I disagree.

My focus as a developer is to write expressive and readable that is easy to understand and easy to change. Code whose signal is obscured by the details of the implementation tends to slow down anyone reading and trying to understand it. This creates [friction in the development process](https://learning.oreilly.com/library/view/managing-technical-debt/9780135646052/ch01.xhtml) and adds to the cost of the software. The more your code can tell a story at a high level of abstraction, the easier it is to understand what it's trying to do and the easier it will be to adapt to changes in requirements.

Another common objection is around performance. I have met developers who argue that having many function/method calls results in a deeper call stack and thus impacts the program's performance. To this I say again, our primary goal as developers is to write code that is readable and understandable.

As a developer, I have wasted more time reading code poorly-factored code than I care to account for. In my opinion, the cost of working with hard-to-understand code far eclipses the cost of a few milliseconds overhead caused by a few extra frames on the call stack.

If you're really worried about performance, use a profiler to identify the actual bottlenecks in your code. When you use a profiler instead of your gut to find performance bottlenecks, you'll usually find out that the bottlenecks aren't really where you thought were and they're usually not the bottlenecks your gut/intuition told you they would be.

Additionally, a good optimizing compiler has a much better chance of automatically improving performance by inlining small methods/functions at the points of call. If you focus on extracting code to small units, you'll actually be helping the compiler find more of those kinds of opportunities than if you leave big chunks of complicated and nested code alone because your gut/intuition tells you that additional function/method calls will create too much overhead. 

Again, we developers generally suck at optimizing with our gut and intuition alone and when we do, we're usually wrong.

Case in point, I refactored `racePoints()` once more by extracting the logic for awarding points to the leaders to its own function, giving me this:

    override fun part2() = racePoints().values.max()

    private fun racePoints(): Map<Reindeer, Int> =
        (1..raceTime).fold(reindeer.associateWith { 0 }) { points, second ->
            award(points, reindeer.associateWith { it.distanceFlownAt(second) })
        }

    private fun award(points: Map<Reindeer, Int>, distances: Map<Reindeer, Int>): Map<Reindeer, Int>
    {
        val leadersDistance = distances.values.max()
        return points.map { (reindeer, leaderPoints) ->
            reindeer to if (distances[reindeer] == leadersDistance) leaderPoints + 1 else leaderPoints
        }.toMap()
    }

Now I have the code really layers the story:

Layer 1: (highest level abstraction): Get the maximum of race point values

Layer 2: (still high-level, with a few more details): Starting with zero points for all reindeer, for each second in the race, award points based on the distances flown at that each second

Layer 3: Get the maximum distance flown and award a point to any reindeer who have flown that far

Can an optimizing compiler automatically inline this if it sees that it will significantly improve performance? I'm sure it could. Is it easier to read/digest? I think it is because each function has a singular intent that I can quickly map and reconcile with its implementation. Reading this kind of code makes me trust it more and have more confidence that it is correct. If tests reveal there's a bug, I'll be able to zero in on the bug much faster.