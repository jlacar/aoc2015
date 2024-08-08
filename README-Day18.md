# Day 18: Like a GIF For Your Yard

[This problem](https://adventofcode.com/2015/day/18) is basically [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) (GoL) except with a grid of lights that are either on (alive) or off (dead). The grid does not wrap around itself, so you need to consider  lights on the edges of the grid when checking the neighbors around a light.

## Part 1

Pretty straightforward GoL implementation: find the state after 100 steps (generations). The bulk of the computing is in counting how many neighbors around a light are on.

My first approach was to iterate over the possible offsets using an `IntRange` of `-1..1`. This required nested loops to go through the permutations of row and column offsets. I also needed some checks to ignore off-grid and the "self" coordinates with offsets of `(0, 0)`. 

Later, I realized it would be simpler to just iterate through a list of offsets, since there were only eight neighbors. This allowed me to eliminate the check for the `(0, 0)` offset, leaving just the on-grid check.

See the ["Sometimes a little judicious hard-coding is better"](#sometimes-a-little-judicious-hard-coding-is-better) section below for details of the refactoring.

## Part 2

This part adds a rule that the lights at the four corners of the grid are always on. Wanting to avoid duplication, I added a `transform` parameter to the `animate()` function. This would be used to decorate the grid with the four lit corners for `part2()`. I used an identity lambda as the default transformation so that it also worked without changing the call in `part1()`.

See notes below for details of the refactoring.

## Refactoring Notes

Anyone who is familiar with Conway's Game of Life will find this problem relatively easy to solve. Aside from a little snag I ran into in part 2 which I'll explain in more detail below, it didn't take me long to earn gold stars for both parts. 

Refactoring for clarity was, as always, the most fun part of this exercise and where I spent the bulk of my time messing around.

### Focus on Clarity First

My first goal in refactoring is always to increase the clarity of the code. The code needs to express ideas clearly and succinctly, in a way that makes it easy for the reader to connect the solution to the problem. The more connections you can make, the easier it is to understand why the solution works. I often refer to this as "telling a story" that makes sense to the reader.

### Use words and phrases from the problem domain

The clearer, simpler, and less technically-oriented code is, the easier it is to relate to its _intent_ and gain a good understanding of what it does. Developers, however, are naturally preoccupied with the technical aspects of the solution and this is often reflected in the code as technical terms and jargon. While understandable, code that has too much of a technical focus can impose a heavy cognitive load on the reader and can be an impediment to understanding. 

Most readers, particularly those who are unfamiliar with the program, will be more interested in the abstract concepts represented in the code as they start familiarizing themselves with it. Understanding the technical details comes later, after a good high-level understanding of the _intent_ of the program is gained. The bigger the gap between the abstract ideas of the problem space and the way these ideas are represented in the code, the harder it will be for the reader to bridge that gap and gain a high-level understanding of the program.

Using words and phrases from the problem space, avoiding technical jargon, and pushing technical details down to the lower layers of abstraction can greatly reduce the cognitive load imposed on the reader. Diligent and judicious refactoring/tidying of the code is one of the best ways I know of keeping that gap as small as it can be.

### Cognitive Load: An Example from Day 18

To illustrate the cognitive load imposed by code that is laden with technical details, let's take a look at my initial solution to Part 1 of Day 18.

To recap the problem we're trying to solve, we are given a 100x100 grid of lights. The grid will have an initial configuration in which some lights will be on and others will be off. The grid is animated in steps from one configuration to the next in such a way that the next state of each light is dependent on its current state and that of its eight neighboring lights.

The task is to figure out how many lights are on after a given number of animation steps (or generations, as it's referred to in the original GoL problem). The example uses a smaller grid animated through 4 steps.

The code below is my first cut solution. It passed the tests and earned a gold star for Day 18, part 1.

    // Works but hard to understand 

    override fun part1(): Int =
        (1..steps).fold(initialConfiguration) { config, _ ->
            nextStep(config)
        }.flatten().count { it }


Despite having only a few lines of declarative (vs. imperative) style code, this still doesn't tell a clear story that can be easily connected to the problem statement: "Given an initial configuration (your puzzle input), how many lights are on after 100 steps?"

The only thing in the code that we can immediately connect to the problem statement is `initialConfiguration`. Other references like `steps`, `nextStep`, and `count` also hint at the problem statement but understanding how those bits and pieces fit together as a whole takes a few seconds of parsing and piecing together to form a coherent story in our head.

The `part1()` function is a high-level function. As a reader trying to understand what the program does, my focus is on understanding _intent_, not so much the implementation details. I want to be able to connect what I'm reading with my understanding of the problem at a very high-level of abstraction. 

Most of the cognitive load, which I often refer to as "friction" (the [Carl von Clausewitz](https://clausewitzstudies.org) kind), is caused by the `fold()` and `flatten()` operations. While they are key parts of the technical solution, introducing them at this level of the program creates more noise than signal for the reader.

Compare that to the refactored version below:

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

This code tells a story that is easily a direct reflection of the problem statement. There is virtually no cognitive load for the reader who is trying to understand the program's intent and how it relates to the problem it solves.

#### One way to read the refactored code

This is how I would read the refactored code: 

> (_Given an_) **initial configuration**, **animate** (_the grid through this many_) **steps** (_and count_) **how many** (_lights_) **are on**.

The italicized words in parentheses are what you might reasonably expect your brain to silently "fill in" as you read the code. Our brains naturally do this, often without us being conscious of it. The amount of effort it takes your brain to fill in gaps like that is what basically constitutes cognitive load. The harder your brain has to work to formulate a coherent story, the more cognitive load you experience.

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
        (1..steps).fold(this) { grid, _ -> grid.nextStep() }

    private fun Grid.howManyAreOn() = flatten().count { it }

I also converted the `nextStep()` function into an extension function of `Grid`. That was it: a few extract to functions later, I had a well-composed function that revealed intent instead of drowning out the signal with a lot of implementation noise.

## Make the code tell a clear and coherent story

The refactored `part1()` code tells a clear story that can be easily mapped to the problem statement. There are a few ways you can read it, [one of which I showed earlier](#one-way-to-read-the-refactored-code). Here's another way:

> (_Given an_) **initial configuration** (_of lights, count_) **how many** (_lights_) **are on** (_after_) **animating** (_the grid through the given number of_) **steps**.

The point is that the words you're reading have very little to do with the implementation details of the solution but rather with the _intent_ of the program. 

### Tidying for reading order also helps

Notice, too, that the arrangement of the extracted functions follow what Kent Beck refers to in his "_Tidy First?_" book as the "reading order", with the assumption that you read the code from top to bottom. 

As much as possible, I like to put private methods as close as I can to the place where they are first referenced. When done consistently and using proximity and reading/encounter order, I can make the experience that of a gradual and progressive building of understanding. 

Non-trivial programs are inherently difficult to understand. Being sensitive to what other people might experience as they read the code can really help you see good ways to make the code clearer, cleaner, and easier to work with.

## Sometimes a little judicious hard-coding is better

As mentioned earlier, my first approach to counting how many neighboring lights were on was to use an `IntRange` and nested loops. 

    private fun Grid.litAround(row: Int, col: Int): Int {
        val offsets = -1..1
        return offsets.sumOf { rowOffset ->
            val r = row + rowOffset                 
            offsets.count { colOffset ->
                val c = col + colOffset
                if (isValidNeighbor(r, c, row, col)) 
                    this[r][c]
                else 
                    false
            }
        }
    }

    private fun Grid.isValidNeighbor(
        r: Int,
        c: Int,
        row: Int,
        col: Int
    ) = r in this.indices && c in this[row].indices && (r != row || c != col)

The first thing that struck me smelly about this code was that it was difficult to scan and quickly understand. Scanning the code, `sumOf`, `count`, and `isValidNeighbor()` stood out to me but it was hard to sift through the rest of it and get a clear idea of what was going on. There was a lot of implementation noise.

Getting rid of temporary variables can be a good way to reduce noise in code. In this case, `r` and `c` were noisy. When you see single-letter variables like these, ask yourself why they don't have better names. Sometimes (more often than not?) developers will just use these kinds of short, cryptic names because they can't think of any good ones. That can be a sign that the expression related to it is either in the wrong place or it's redundant.

That was exactly the case here. I had struggled with what to name these variables and it only dawn on me later that since they represented the neighbor's position on the grid, `neighborRow` and `neighborCol` would be more appropriate names for them.

Anyway, inlining `r` and `c` gave me this:

    private fun Grid.litAround(row: Int, col: Int): Int {
        val offsets = -1..1
        return offsets.sumOf { rowOffset ->
            offsets.count { colOffset ->
                if (isValidNeighbor(row + rowOffset, col + colOffset, row, col))
                    this[row + rowOffset][col + colOffset]
                else
                    false
            }
        }
    }

This tidied up the outer loop a little but only shifted noise into the inner loop. The code still wasn't clear enough for my eyes to just glide over it. However, I could now see a way to reveal intent by extracting the `if-else` expression. 

Again, the guiding question "What does this piece of code do and how can we make it say that in a simpler, more straightforward way?"

    private fun Grid.litAround(row: Int, col: Int): Int {
        val offsets = -1..1
        return offsets.sumOf { rowOffset ->
            offsets.count { colOffset ->
                isLightOnAt(row, rowOffset, col, colOffset)
            }
        }

    private fun isLightOnAt(row: Int, rowOffset: Int, col: Int, colOffset: Int) =
        if (isValidNeighbor(row + rowOffset, col + colOffset, row, col)
            this[row + rowOffset][col + colOffset]
        else
            false

The long parameter list for `isLightOnAt` and the redundancy of their values was really starting to bother me now. However, it also occurred to me that since there were only eight pairs of offsets to deal with, it might be easier if I just hard-coded those offsets. 

Here's what I got after a few tweaks and renames:

    private val offsets = listOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
        Pair( 0, -1),              Pair( 0, 1),
        Pair( 1, -1), Pair( 1, 0), Pair( 1, 1)
    )

    private fun Grid.litNeighborsOf(row: Int, col: Int): Int =
        offsets.count { (rOffset, cOffset) ->
            isLightOnAt(row + rOffset, col + cOffset)
        }

That looked pretty clean. As a sanity check, I retell the story in my head to see how well it maps to the code:

> Count how many neighbors of the light at (row, col) are lit.

While I had managed to trim the code down quite a bit, I still sensed some friction from the offsets list. As I read the code, I noticed my eyes bouncing back and forth between the `count` body and the `offsets` list declaration. It seems my brain is trying to figure osut the relationship between the addition the offsets in the `isLightOnAt()` call and the offsets list.

This made me realize that the `litNeighborsOf()` function was doing two things: counting the lit neighbors and calculating the neighboring light's positions. These two concerns should probably be separated. 

To do that, I created the `neighborsOf()` function. Moving the calculation of neighbors to a separate function leaves `litNeighborsOf()` to be focused only on counting its lit neighbors, just like its name suggests.

    private fun Grid.litNeighborsOf(row: Int, col: Int): Int = neighborsOf(row, col)
        .count { (neighborRow, neighborCol) -> isLightOnAt(neighborRow, neighborCol) }

    private fun neighborsOf(row: Int, col: Int) =
        neighborOffsets.map { (rOffset, cOffset) -> Pair(row + rOffset, col + cOffset) }

Now reading and understanding the code is straightforward:

> (_Get the_) **neighbors of** (_the light at_) **(row, column)** (_and_) **count** (_the_) **lights that are on**. 

This refactoring also simplifies the checks needed to make sure only valid grid coordinates are checked.

    private fun Grid.isLightOnAt(row: Int, col: Int) =
        if (isOnGrid(row, col)) this[row][col] else false

    private fun Grid.isOnGrid(row: Int, col: Int) = 
        row in this.indices && col in this[row].indices

### When you do good things to the code, good things tend to happen in the code 

As I read the code again, I notice that the `isLightOnAt()` function nicely encapsulates the rule in the problem statement about missing neighbors:

> Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".

While I didn't do this intentionally, it felt good to see the logic simply fell into its rightful place as a natural consequence of diligent and judicious tidying. It seems almost karmic but then again, if you do good things _to_ the code, good things tend to happen _in_ the code.

### Using companion object to hold static things

One additional refactoring I did was to give better names to the things related to neighbors.

First, I renamed `offsets` to `neighborOffsets`. I also used `neighborRow` and `neighborCol` so it's clear what they represent.

Then, I moved the offset list into the Day18 companion object. There is no point in creating the list more than once and the companion object is the perfect place to put this kind of stuff. 

I thought about moving `isOnGrid()` in there as well but decided not to because it had a dependency on the grid itself and rightly belonged in the `Day18` class that encapsulated it.

## Function parameters and parameter defaults

