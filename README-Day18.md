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

Refactoring for clarity was, as always, the most fun part of this exercise and where I spent the bulk of my time messing around with it.

### Focus on Clarity First

My first goal in refactoring is always to increase the clarity of the code. The code needs to express ideas clearly and succinctly, in a way that makes it easy for the reader to connect the solution to the problem. The more connections you can make, the easier it is to understand why the solution works. I often refer to this as "telling a story" that makes sense to the reader.

### Use words and phrases from the problem domain

Developers are naturally preoccupied with the technical aspects of the solution. This is often reflected in the code as technical terms and jargon from the solution space. While understandable, the problem with having too much technical detail in the code, especially the high-level parts, is that it imposes a cognitive load on anyone who reads it. 

Most readers, particularly those who are unfamiliar with the program, will be more interested in the abstract concepts represented and not so much in the technical details. That level of understanding comes later, after a good high-level understanding of the _intent_ of the program is gained. 

The bigger the gap between the abstract ideas of the problem space and the way these ideas are represented in the code, the harder it will be for the reader to bridge that gap and gain a high-level understanding of the program.

Using words and phrases from the problem space, avoiding technical jargon, and hiding implementation details, especially in the higher levels of code, can greatly reduce that gap and imposes less of a cognitive load on the reader.

In summary, the clearer, simpler, and less technically-oriented code is, the easier it is to relate it to the _intent_ of the program and gain a good understanding of what it does.

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

The only phrase in the problem statement that we can immediately connect to the code is `initialConfiguration`. Other references to `steps`, `nextStep` and `count` functions also hint at a connection but understanding the code as a whole takes a few seconds of parsing and piecing the parts together to form a coherent story in our heads.

Most of the cognitive load, which I often refer to as "friction", is caused by the `fold()` and `flatten()` operations. Both of these sit squarely in the technical solution space rather than the abstract problem space. 

The function `part1()` is a high-level function and as a reader, my main focus is gaining a high-level understanding, not so much the implementation details. I want to be able to connect what I'm reading with my understanding of the problem at a very high-level of abstraction. While they are key parts of the technical solution, `fold()` and `flatten()` have little to no connection to the abstract ideas from the problem statement.

Now, compare that to the refactored version:

    override fun part1(): Int = initialConfiguration
        .animate(steps)
        .howManyAreOn()

The story this code tells is now very clearly a direct reflection of the ideas presented in the problem statement. There is virtually no cognitive load for the reader who is trying to correlate the code to the problem.

#### One way to read the refactored code

> (_Given an_) **initial configuration**, **animate** (_the grid through this many_) **steps** (_and count_) **how many** (_lights_) **are on**.

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

The italicized words in parentheses are what you might reasonably expect your brain to silently "fill in" as you read the code. Our brains naturally do this, often without us being conscious of it.

The effort of silently filling in gaps like that is what basically constitutes cognitive load. The harder your brain has to work to fill in those gaps to formulate a coherent story, the more cognitive load you experience.  

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

The first thing that struck me smelly about this code was, of course, that it was difficult to scan and quickly understand. The friction in `Grid.litAround()` comes mostly from everything after the line with `return` on it. Scanning it, `sumOf`, `count`, and `isValidNeighbor()` are what stand out to me, but it's hard to sift through the rest of the code and get a clear idea of what's going on.

In general, one way to reduce the noise in code is to get rid of temporary variables. The poorly-name `r` and `c` got the axe. A hint that I could eliminate them came from how they were being passed to `isValidNeighbor()` along with `row` and `col`, which are part of their respective values. 

Inlining `r` and `c` gave me this:

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

This tidied up the outer loop a little bit but only shifted noise into the inner loop. Still not clear enough but now I could see a way to reveal intent by extracting the `if-else` expression.

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

The long parameter list for `isLightOnAt` and the redundancy of their values was really starting to bother me. However, it also occurred to me that since there were only eight pairs of offsets to deal with, it might be easier if I just hard-coded those offsets. Here's what I got after a few tweaks:

    private val offsets = listOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
        Pair( 0, -1),              Pair( 0, 1),
        Pair( 1, -1), Pair( 1, 0), Pair( 1, 1)
    )

    private fun Grid.litNeighborsOf(row: Int, col: Int): Int =
        offsets.count { (rOffset, cOffset) ->
            isLightOnAt(row + rOffset, col + cOffset)
        }

IntelliJ IDEA's flexibility in formatting allowed me to format the list of offset pairs to form, creating a visual cue for what they meant. The empty spot in the middle represents the light whose neighbors we're checking. With a few tweaks to the function names, the code is now much easier to read and comprehend.

This is how I read it:

> (_Given the_) **offsets** (_of a light's neighbors_), **count** (_how many of those_) **lights are on**. (That's how many) **lit neighbors of** (_the light at_) **(row, col)** (_has_). 

This refactoring also simplifies the checks I need to make to ensure I'm only checking valid grid coordinates.

    private fun Grid.isLightOnAt(row: Int, col: Int) =
        if (isOnGrid(row, col)) this[row][col] else false

    private fun Grid.isOnGrid(row: Int, col: Int) = 
        row in this.indices && col in this[row].indices

## Function parameters and parameter defaults

