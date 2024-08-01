# Day 16: Aunt Sue

This was a relatively easy problem to solve. The main operation in the [Day 16](https://adventofcode.com/2015/day/16) solution was comparing the values in one map (a list of Aunt Sue's things) to the values in another (the trace analysis). Part 2 adds a few more ways to check the quantities to determine a match.

The only tricky part to this problem was that the list items (all the different Aunt Sue lists) were assigned a 1-based identification number. The value of this number for the matching Aunt Sue is the answer in both parts. 

Since Kotlin has 0-based indices, I needed to adjust for that, hence the `+ 1` term at the end of the expressions in both parts.

This is one of the few Advent of Code problems I've seen that doesn't have any example scenarios, so the test for this likewise does not have the usual examples tests.

# Part 1 - Exact matches for quantities found in the trace report

This part asks you to find the index of the first Aunt Sue whose list of things exactly match the quantities that showed up in the trace analysis report. 

The most convenient function I could think of to use here was the [`all`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/all.html) function, which takes a predicate which must evaluate to true for all items in a collection.

Choosing good names so the code will tell the story clearly, I had this expression:

    auntSues.indexOfFirst { auntSueWhoseList ->
        auntSueWhoseList.hasEqualQuantitiesIn(traceAnalysis)
    } + 1

As mentioned above, the `+ 1` at the end adjusts for a 1-based identification number.

I created an extension function for `Map<String, Int>`, `haveQuantitiesThatMatch()`, to explain what the code in it was doing. This makes the code very declarative and intention-revealing.

# Part 2 - Additional match logic for inexact matches

In this part, we're given additional criteria. I used the same construct as before and added another extension function for `Map<String, Int>` so I could write this expression:

    auntSues.indexOfFirst { auntSueWhoseList ->
        auntSueWhoseList.hasQuantitiesConsistentWith(traceAnalysis)
    } + 1

In `hasQuantitiesConsistentWith()`, I used a `when` expression to handle the different ways of checking the quantities for a match. 

One thing that puzzles me is the null-safety checks. I thought Kotlin would see that an expression had already been checked for null and that any code after that would "know" that it was null-safe. However, IntelliJ kept flagging the code so I had to use the `!!` operator to get rid of the compiler warnings.

# Input parsing utility functions are paying off

The new input parsing utility functions are proving to be very useful in keeping the noise in the input parsing code to a minimum. I'm glad I spent a few minutes to create those.