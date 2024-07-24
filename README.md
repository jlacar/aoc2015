# Advent of Code 2015 - In Kotlin

I'm actually backtracking to the first AoC set of problems here, having already started solving some of the more recent ones. I've learned a few lessons since then and I'm applying those learnings here. Also taking some inspiration from [Todd Ginsberg](https://github.com/tginsberg) and the way he approaches solving these problems in Kotlin.

## General approach

I'm using the `Solution` interface to define the shape of a solution. The interface defines the `part1()` and `part2()` functions, with the return type specified as a generic parameter. Most of the solutions will be defined as `Solution<Int>` because most of the problem answers are numeric.

A common pattern I'm using is that of the companion object as a builder or factory. I'll usually name the factory method as `using` because it reads very naturally when passed in the input. One example of this is the `Day09` solution:

    Day09.using(puzzleInput).part1()
    ...
    Day09.using(puzzleInput).part2()

Whenever possible, I try to find and extract the core logic that can be used to solve both parts of each problem. 

## Solutions

These are links to notes on each of my solutions to [Advent of Code 2015](https://adventofcode.com/2015) using [Kotlin](https://kotlinlang.org).

* Day 1
* Day 2
* Day 3
* Day 4
* Day 5
* Day 6
* Day 7
* Day 8
* Day 9
* Day 10
* Day 11
* [Day 12](README-Day12.md)
* [Day 13](README-Day13.md)
* [Day 14](README-Day14.md)
* Day 15
* Day 16
* Day 17
* Day 18
* Day 19
* Day 20
* Day 21
* Day 22
* Day 23
* Day 24
* Day 25