# Day 1: Not Quite Lisp

A nice [warm-up puzzle](https://adventofcode.com/2015/day/1) to kick things off. 

This puzzle involves parsing a series of parentheses. Starting from the ground floor (0), each parenthesis in the input tells Santa to go up one floor if it's an open parenthesis, `(`, or down one floor if it's a close parenthesis, `)`.

# Part 1

We need to find the floor Santa ends up on after following the directions. I used `fold()` to solve this. Pretty straightforward.

# Part 2

We need to find the position of the character in the directions that causes Santa to first enter the basement (floor -1). I used the `runningFold()` this time since we needed to find the index of the character.

# Refactoring Notes

As short as the first cut of the program was, I still found opportunity to refactor for clarity. This resulted in more lines of code than the original because I extracted implementation details to private functions. However, the loss in brevity was offset by a gain in clarity in the high-level code, from hiding the implementation details behind more intention-revealing names.

Here's what `part1()` and `part2()` looked like after I extracted implementation details.

    override fun part1() = directions.lastFloor()
    override fun part2() = directions.positionOfFirstTimeInBasement()

At the high level parts of the program, the code has a much stronger connection to how the problem was articulated in plain English. The nitty-gritty details of how the job was done were pushed under the hood, so to speak, into private functions.

For a program this small, it may seem a bit heavy-handed to do this kind of refactoring. However, applying this style of organizing and structuring code in real-world, thousands-of-lines-long and hundreds-of-files-big programs will pave the way for a gentler and more enjoyable experience for readers trying to understand what's going on in the code.