# Day 1: Not Quite Lisp

A nice [warm-up puzzle](https://adventofcode.com/2015/day/1) to kick things off. 

This puzzle involves parsing a series of parentheses (the puzzle input). Santa is delivering gifts to kids who live in an large apartment building. To find the right floor, he needs to follow the directions he's been given.  

Each parenthesis in the directions tells Santa which way to go: up one floor if it's an open parenthesis, `(`, down one floor if it's a close parenthesis, `)`.

# Part 1

Starting from the ground floor (floor 0), we need to find the floor Santa ends up on after following all the directions. 

I used `fold()` to solve this. Pretty straightforward.

    override fun part1(): Int = directions.fold(0) { floor, direction ->
        floor + if (direction == '(') 1 else -1
    }

# Part 2

We need to find the position of the character in the directions that causes Santa to first enter the basement (floor -1). I used the `runningFold()` this time since we needed to find the index of the character.

    override fun part2(): Int = directions.runningFold(0) { floor, direction -> 
        floor + if (direction == '(') 1 else -1
    }.indexOfFirst { it == -1 }

# Refactoring Notes

As short as the first cut of the program was, I still found opportunity to refactor for clarity. This resulted in quite a few more lines of code than the original because I extracted implementation details to private functions. However, the loss in brevity was offset by a gain in clarity in the high-level code, from hiding the implementation details behind more intention-revealing names.

Here's what `part1()` and `part2()` looked like after I extracted implementation details.

    override fun part1() = directions.lastFloor()
    override fun part2() = directions.positionOfFirstTimeInBasement()

At the high level parts of the program, the code has a much stronger connection to how the problem was articulated in plain English. The nitty-gritty details of how the job was done were pushed under the hood, so to speak, into private functions.

I also extracted the code that calculates the next floor to its own function because it was used in both parts.

    private val nextFloor: (Int, Char) -> Int = { currentFloor, direction ->
        currentFloor + if (direction == '(') 1 else -1
    }

For a program this small, it may seem a bit heavy-handed to do this kind of refactoring. However, applying this style of organizing and structuring code in real-world, thousands-of-lines-long and hundreds-of-files-big programs will pave the way for a gentler and more enjoyable experience for readers trying to understand what's going on in the code.