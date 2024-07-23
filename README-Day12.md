# Day 12: JSAbacusFramework.io

Day 12 revolves around parsing JSON and getting the sum of all numbers in it.

## Part 1 - Sum of all numbers

Used Regular Expression in the [initial solution](https://github.com/jlacar/aoc2015/blob/86cd9e5421c02b9e8bd1a8a23f5aca11ea40e40c/src/main/kotlin/lacar/junilu/Day12.kt).

It took a while to figure out how to iterate over the matches but after some experimentation, I ended up using `MatchResult.groupValues.first()` to access each whole number that was found.

## Part 2 - Skip any JSON object with a "red" property  

My [initial solution](https://github.com/jlacar/aoc2015/blob/9a3210cae84cdc5de1e6ad4e6c48abd9a54d04e2/src/main/kotlin/lacar/junilu/Day12.kt) for the second part had a somewhat complex recursive structure.

The first insight that helped me tighten up the logic was that the `deepSumOf()` function should be the entry point for the recursion instead of the `sumOfNumbers(JsonObject)` function. Doing this, I was able to eliminate the dynamic dispatch via function overloading and take advantage of the [automatic type casting](https://kotlinlang.org/docs/typecasts.html) in a `when` expression instead. 

Also, I needed to declare the first parameter as a `JsonElement` instead of `JsonObject` so that the input string could be a Json array without the outermost curly braces `{...}` to make it a proper JsonObject. This meant that the parsing logic had to be declared with`JsonElement`:

    private val json = Json.decodeFromString<JsonElement>(input)

If I used `JsonObject` as the generic type instead, the input string would be decoded as a `JsonObject` which caused some of the Part 1 examples that weren't JSON objects to throw a parsing exception.

## Aligning around a common solution

The difference in the solutions of the two parts was a little smelly to me. It seemed like it should be possible to use a common core of logic for both parts, with the only difference between the two parts being the condition to skip any JSON objects that have an element with a value of "red".

### High-order functions to the rescue

There were three key insights that led to a successful refactoring of the logic to use a common function.

First, I added the `skip` function parameter to the `deepSumOf` function. This expected a predicate that would be used to check if the current element being processed should be skipped or not. Setting a default of `{ false }` simplified the call to `part1()`.  

Second, I realized that it's better to declare the `element` parameter to `deepSumOf()` as a `JsonElement` instead of a `JsonObject`. By using the superclass type, I could let the  [automatic type casting](https://kotlinlang.org/docs/typecasts.html) in the `when` expression simplify the dispatching to the correct logic branch for further processing of the element.

Third, I realized that the new `skip` parameter could be assigned a default value that was essentially a "noSkip" option so that the call the `deepSumOf()` in part 1 could be cleaner.

Was quite happy with the [final solution](https://github.com/jlacar/aoc2015/blob/bdafeb1c0ded859faad2567ce2829524a7bfee46/src/main/kotlin/lacar/junilu/Day12.kt) I got to.