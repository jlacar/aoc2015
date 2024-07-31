# Day 15: Science for Hungry People

Learned more about Kotlin Sequences with this one. The core of the solution was calculating the size of each ingredients portion of the 100 teaspoons limitation. 

To be honest, I could not come up with the algorithm to calculate the different ways to apportion the ingredients so I ended up (again) adapting Python code I found on the r/adventofcode SubReddit. Most of the solutions posted used brute force but I finally found someone who posted a generic solution. 

As I suspected, it involved recursion. Apparently, it's closely related to the [Knapsack Problem](https://en.wikipedia.org/wiki/Knapsack_problem). The Python solution used `yield`. Luckily, Kotlin also has a `yield` function that works very similarly, so adapting the Python code to Kotlin was again fairly straightforward.

This is the Python code posted on r/adventofcode by u/What-A-Baller

    def mixtures(n, total):
        start = total if n == 1 else 0

        for i in range(start, total+1):
            left = total - i
            if n-1:
                for y in mixtures(n-1, left):
                    yield [i] + y
            else:
                yield [i]

My Kotlin adaptation was very similar:

    fun proportions(parts: Int, total: Int): Sequence<IntArray> = sequence {
        val start = if (parts == 1) total else 0
        for (portionSize in (start..total)) {
            if (parts <= 1) {
                yield(intArrayOf(portionSize))
            }  
                for (portion in proportions(parts - 1, total - portionSize)) {
                    yield(intArrayOf(portionSize) + portion)
                }
            }
        }
    }

Of course, I changed some of the names, to protect the innocent.

## Solution Base

I used the companion object factory pattern again here, with the `using()` function encapsulating the input parsing and instance creation logic.

There always seems some data in the input that ends up being irrelevant to the solution. In this case, it was the name of the ingredient that seemed to be important but in the end, played no part.

## Part 1 - Find the best cookie

Tightening up the iterations, inlining, expressive code, choosing names from the problem domain to tell a clear, cohesive story

## Part 2 - Find the best cookie with exactly 500 calories 

High-order functions, defaults for function parameters, and calling a function parameter.

## Kotlin Sequences and `yield`

Discuss the `sequence()` function and how `yield` works almost but not quite like `return` - what are the (n-1) level details around these?  