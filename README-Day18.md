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

Developers are naturally preoccupied with the technical aspects of the solution. This is often reflected in the code as technical terms and jargon from the solution space. While understandable, the problem with having too much technical detail bleeding into the code is that it creates cognitive dissonance for the reader of the code. 

Most readers, especially those who are unfamiliar with the code, will be more interested in the abstract concepts represented in the program, not so much in the technical implementation details. That comes later, after gaining a good high-level understanding of the _intent_ of the program. 

The bigger the gap between the abstract ideas of the problem space and the technical details of the solution space, the harder it will be for the reader to bridge that gap and gain a high-level understanding.

Using words and phrases from the problem space and avoiding technical jargon, especially in the higher levels of code, can greatly reduce that gap and imposes less of a cognitive load on the reader.

### Cognitive Dissonance: An Example from Day 18

To illustrate the cognitive load imposed by code that is laden with technical details, let's take a look at my initial solution to Part 1 of Day 18.

To recap the problem, we are given a 100x100 grid of lights. The grid will have an initial configuration in which some lights will be on and others will be off. The grid is animated in such a way that the next state of each light is dependent on its current state and the state of its eight neighboring lights.

The task is to figure out how many lights are on after a given number of animation steps (or generations, as it's referred to in the original GoL problem). The example uses a smaller grid animated through 4 steps.

The code below is my first cut. It passed the tests and earned a gold star for Day 18, part 1.

    // Works but hard to understand 

    override fun part1(): Int =
        (1..steps).fold(initialConfiguration) { config, _ ->
            nextStep(config)
        }.flatten().count { it }


Despite having only a few lines of declarative (vs. imperative) style code, this still doesn't tell a clear story that can be easily connected to the problem statement: "Given an initial configuration (your puzzle input), how many lights are on after 100 steps?"

The only phrase in the problem statement that we can immediately connect the code to is `initialConfiguration`. Other references to `steps`, `nextStep` and `count` functions also hint at a connection but understanding the code as a whole takes a few seconds of parsing and piecing the parts together to form a coherent story in our heads.

A big part of the problem is that `fold()` and `flatten()` both sit squarely in the technical solution space rather than the abstract problem space. While they are key parts of the technical solution, they have little to no connection to the abstract ideas from the problem statement.

Compare that to the refactored version of the code:

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

The story this code tells is now very clearly a direct reflection of the ideas present in the problem statement. There is virtually no cognitive load for the reader who is trying to correlate the code to the problem.

## Step-by-Step Refactoring/Tidying 

The refactoring of `part1()` was accomplished in a few small steps. At each step, the guiding question was "What is this piece of code doing and how can we express it in a way that is simple and straightforward?" 

The first expression I applied the question to was `.flatten().count { it }`. This part is really concerned with counting the number of lights that are on. So, I extracted it to an appropriately-named [extension function](https://kotlinlang.org/docs/extensions.html) for `Grid`:

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

By the way, `Grid` is a type alias I defined for `List<List<Boolean>>` to minimize implementation noise with an intention-revealing name.

    private typealias Grid = List<List<Boolean>>

Next, I wanted to clarify the intent of the `fold` operation. Once again, I did this by extracting to an aptly-named extension function:

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

> (_Given an_) **initial configuration** (_of lights_), **animate** (_through the given number of_) **steps** (_and return_) **how many** (_lights_) **are on**.

or 

> (_Given an_) **initial configuration** (_of lights, count_) **how many** (_lights_) **are on** (_after_) **animating** (_the grid through the given number of_) **steps**.

The italicized words in parentheses are what you might reasonably expect your brain to silently "fill in" as you read the code. Our brains naturally do this, often without us being conscious of it.

The effort of silently filling in gaps like that is what basically constitutes cognitive load. The harder our brain has to work to fill in those gaps to formulate a coherent story, the more cognitive load you experience.  

Notice, too, that the arrangement of the extracted functions follow what Kent Beck refers to in his "_Tidy First?_" book as the "reading order", with the assumption that you read from top to bottom. 

As much as possible, I like to put private methods as close as I can to the place where they are first referenced as I read the code from top to bottom. When done consistently, using proximity and reading/encounter order can make it much easier to see how parts of the code are related to each other.