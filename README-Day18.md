# Day 18: Like a GIF For Your Yard

[This problem](https://adventofcode.com/2015/day/18) is basically [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) (GoL) except with a grid of lights that are either on (alive) or off (dead). The grid does not wrap around itself, so you need to consider  cells on the edges of the grid when calculating the number of neighbors of a cell.

## Part 1

A straightforward GoL to find the state after 100 steps (generations). 

## Part 2

This part adds a rule that the cells at four corners of the grid are always on.

## Notes

Anyone who is familiar with Conway's Game of Life will find this problem relatively easy. Aside from a little snag I ran into in part 2 which I'll explain in more detail below, it didn't take me long to solve both parts. 

Refactoring for clarity was, as always, the most fun part of this exercise where I spent the bulk of my time messing around.

### Focus on Clarity First

My first goal in refactoring is always to increase the clarity of the code. The code needs to express ideas clearly and succinctly, in a way that makes it easy for the reader to connect the solution to the problem, and understand why the solution is correct. I often talk about it as "telling a story that immediately makes sense to the reader."

### Use words and phrases from the problem domain

Developers are naturally steeped in the technical side of the problem and tend to leave artifacts of their mindset in the code. These artifacts come in the form of technical details and jargon from the solution space. 

The problem with this is that it creates cognitive dissonance for the reader of the code. The bigger the gap between the abstract ideas of the problem space and the technical concepts of the solution space, the harder a reader of the code has to work to bridge that gap and gain a good understanding of how the two sides are connected.

### Cognitive Dissonance: An Example from Day 18

To illustrate the cognitive dissonance created by code that is heavily-laden with technical details, let's take a look at the initial solution to Part 1 of Day 18.

    override fun part1(): Int =
        (1..steps).fold(initialConfig) { config, _ ->
            nextStep(config)
        }.flatten().count { it }

This code passed its tests and earned a Gold Star for finding the correct answer for the puzzle input.

However, despite having relatively few lines of code and using a declarative (vs. imperative) style, this code still falls short of telling a clear story that can be connected to the question in the problem statement: "Given your initial configuration, how many lights are on after 100 steps?"

The only phrase in the problem statement that we can immediately connect to the code is the `initialConfig`. The references to  `steps`, `nextStep` and `count` functions also give us cognitive connections but understanding it all takes a few seconds of parsing and piecing the parts together to form a coherent story in our heads.

Compare that to the refactored version of `part1()`:

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

The story this code tells is a directly reflection of the ideas mentioned in the problem statement. There is virtually no cognitive load for the reader.

The refactoring was done in a few incremental steps. At each step, the guiding question was "What is it doing and how can that be expressed in a simpler and more straightforward way?" 

The first expression we applied this question to was `.flatten().count { it }`. I extracted it to an extension function:

    // BEFORE

    override fun part1(): Int =
        (1..steps).fold(initialConfig) { config, _ ->
            nextStep(config)
        }.flatten().count { it }  // [1] Extract

    // AFTER

    override fun part1(): Int =
        (1..steps).fold(initialConfig) { config, _ ->
            nextStep(config)
        }.howManyAreOn()
     
    private fun Grid.howManyAreOn() = flatten().count { it }

To minimize the implementation noise and reveal intent, I also defined `Grid` as a type alias for `List<List<Boolean>>`.

    private typealias Grid = List<List<Boolean>>

Next, I wanted to clarify what the fold operation was doing. Again, I extracted it to an extension function:

    // BEFORE

    override fun part1(): Int =
        (1..steps).fold(initialConfig) { config, _ -> 
            nextStep(config)
        }.howManyAreOn()
     
    private fun Grid.howManyAreOn() = flatten().count { it }

    // AFTER

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()
     
    private fun Grid.animate(steps: Int) = 
        = (1..steps).fold(this) { grid, _ -> grid.nextStep() }

    private fun Grid.howManyAreOn() = flatten().count { it }

I also converted the `nextStep()` function into an extension function of `Grid`.

The refactored code in `part1()` now tells a clear story. You can read it a couple of ways:

> Given an initial configuration (of lights), animate (through) the given (number of) steps and return how many (lights) are on.

or 

> Given an initial configuration (of lights), (count) how many (lights) are on after animating (the grid through) the given (number of) steps.

The phrases in parentheses are what you might reasonably expect your brain to silently "fill in" as you read the code. Our brains naturally do this without us being conscious of it. 

The act of silently filling in the gaps is what basically constitutes cognitive load. The harder the brain has to work to fill in those gaps and create a coherent story, the more cognitive load you experience.  