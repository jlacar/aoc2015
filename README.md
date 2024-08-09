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

* [Day 1](README-Day01.md)
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
* [Day 15](README-Day15.md)
* [Day 16](README-Day16.md)
* [Day 17](README-Day17.md)
* [Day 18](README-Day18.md)
* Day 19
* Day 20
* Day 21
* Day 22
* Day 23
* Day 24
* Day 25

## Utilities 

There are some basic operations that come in handy in solving these problems. One such operation is calculating permutations. I looked around for some implementations and I found [one that was workable](https://inventwithpython.com/recursion/chapter6.html), although it was written in Python. No biggie, converting Python code to Kotlin is fairly straightforward. 

This is the Python code, without the informational print statements:

    def getPerms(chars, indent=0):
        if len(chars) == 1:
        return [chars]
    
        # RECURSIVE CASE
        permutations = []
        head = chars[0]
        tail = chars[1:]
        tailPermutations = getPerms(tail, indent + 1)
        for tailPerm in tailPermutations:
            for i in range(len(tailPerm) + 1):
                newPerm = tailPerm[0:i] + head + tailPerm[i:]
                permutations.append(newPerm)
        return permutations

This was easily translated to Kotlin but there were a few changes I made in the process. I kept with the general approach but added one guard clause for an empty list, which I'd expect to return an empty list. The Python implementation is done in an imperative style. I translated this to a more functional style.

The first thing I did was to add a couple of extension properties, `head` and `tail` to `List<T>`. I got these off of a StackOverflow thread I happened upon while looking for permutations solutions in Kotlin, of which there were surprisingly few.

    val <T> List<T>.head: T
        get() = first()
    
    val <T> List<T>.tail: List<T>
        get() = drop(1)

I also made `permutations()` an function of `List<T>`:

    fun <T> List<T>.permutations(): List<List<T>> { ... }

Then I added a check for the "zero" case where the list was empty. I'd expect `permutations()` to return an empty list here. I kept the "one" base case, of course, where a list with only one element returns the list itself as its only permutation.

For the recursive part of the logic, the `head` and `tail` extension properties really helped simplify and clarify the Kotlin logic. 

Using `fold()` operations instead of for-loops allowed me streamline the Kotlin code. Specifically, I eliminated or inlined the temporary variables. The `permutations` variable from the Python code was now the outer `fold()` operation's accumulator variable, initialized in call to `fold()` itself. The `tailPermutations` variable became an inline call `tail.permutations()` which because the receive of the outer `fold()` operation.

The inner for-loop from Python also became a `fold()` operation. With the `+` operator overloaded for `List<T>`, adding the elements of each permutation together was easy. The only thing that looks a little weird is the 

    fun <T> List<T>.permutations(): List<List<T>> {
        if (isEmpty()) return emptyList()
        if (size == 1) return listOf(this)

        return tail.permutations()
            .fold(mutableListOf()) { allPerms, perm ->
                (0..perm.size).fold(allPerms) { acc, i ->
                    acc.add(perm.subList(0, i) + head + perm.subList(i, perm.size))
                    acc
                }
            }
    }