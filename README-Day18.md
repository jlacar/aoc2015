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

My first goal in refactoring is always to increase the clarity of the code. The code needs to express ideas clearly and succinctly, in a way that makes it easy for the reader to connect the solution to the problem. The more connections you can make, the easier it is to understand why the solution works. I often refer to this as "telling a story" that makes sense to the reader.

### Use words and phrases from the problem domain

Developers are naturally preoccupied with the technical aspects of the solution. This tendency tends to leave its mark on the code the form of technical terms and jargon from the solution space. 

While understandable, the problem with having too much technical detail bleeding into the code is that it creates cognitive dissonance for the reader of the code. Most readers, especially those who are unfamiliar with the code, will be more interested in the abstract concepts embodied in the code, and not so much the technical implementation details. That comes later, after a good high-level understanding of the intent of the code is formed. 

The bigger the gap between the abstract ideas of the problem space and the technical details of the solution space, the harder it will be for the reader to bridge that gap and gain a high-level understanding of abstract concepts involved.

Using words and phrases from the problem space and avoiding technical jargon, especially in the higher levels of code, can greatly reduce that gap and reduce the amount of cognitive load placed on the reader.

### Cognitive Dissonance: An Example from Day 18

To illustrate the cognitive dissonance created by code that is heavily-laden with technical details, let's take a look at the initial solution to Part 1 of Day 18.

To recap the problem, we are given a finite grid, say 100x100, of lights. The grid will have an initial configuration in which some lights will be on and others will be off. The grid is animated in such a way that the next state of each light is dependent on its current state and the state of its eight neighboring lights.

The task is to figure out how many lights are on after a given number of animation steps (or generations, as it's referred to in the original GoL problem).

The code below is my first cut. It passed the tests and earned a Gold Star for part 1.

    // Works but hard to understand 

    override fun part1(): Int =
        (1..steps).fold(initialConfiguration) { config, _ ->
            nextStep(config)
        }.flatten().count { it }


However, despite having only few lines of code and using a declarative (vs. imperative) style, this code still falls a bit short of telling a clear story that can be easily  connected to the problem statement: "Given your initial configuration, how many lights are on after 100 steps?"

The only phrase in the problem statement that we can immediately connect the code to is `initialConfiguration`. Other references to `steps`, `nextStep` and `count` functions also hint at a connection but understanding the code as a whole takes a few seconds of parsing and piecing the parts together to form a coherent story in our heads.

A big part of the problem is that `fold()` and `flatten()` both sit squarely in the technical solution space rather than the abstract problem space.

Compare that to the refactored version of the code:

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

The story this code tells is now very clearly a direct reflection of the concepts mentioned in the problem statement. There is virtually no cognitive load for the reader who is trying to correlate the code to the problem.

## Step-by-Step Refactoring/Tidying 

The refactoring of `part1()` was accomplished in a few small steps. At each step, the guiding question was "What is this piece of code doing and how we express that in a simpler and more straightforward way?" 

The first expression I applied this question to was `.flatten().count { it }`. This part is responsible for coming up with the number of lights that are on so I extracted it to an appropriately named extension function:

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

Next, I wanted to clarify the intent of the `fold` operation. Once again, this was accomplished by extracting to an aptly-named extension function:

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

Notice, too, the arrangement of the extracted function. This is what Kent Beck refers to as "Reading Order" in his "Tidy First?" book. As much as possible, I like to put private methods as close as possible to the place where they are first referenced as you read the code from top to bottom. When done consistently, using proximity and encounter/reading order can make it much easier to see how different parts of the code are related to each other.