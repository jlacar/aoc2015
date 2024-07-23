# Day 12: JSAbacusFramework.io

Day 12 revolves around parsing the puzzle input as JSON and getting the sum of all numbers in it.

## Part 1 - Sum of all numbers

I used a regular expression in the [initial solution](https://github.com/jlacar/aoc2015/blob/86cd9e5421c02b9e8bd1a8a23f5aca11ea40e40c/src/main/kotlin/lacar/junilu/Day12.kt).

Kotlin has good support for regular expression through various forms of the [Regex](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-regex/) class. It took me a while to figure out how to iterate over the matches but after some experimentation, I ended up using `MatchResult.groupValues.first()` to access each whole number that was found.

## Part 2 - Skip any JSON object with a "red" property  

Part 2 is very similar to Part 1 except that now you had to skip any numbers in JSON objects that had a property with a value of "red".

This time, a Regex just wouldn't work, so I had to read up on how to deserialize a JSON string. The puzzle input was a very large and deeply nested JSON object. The easiest way to traverse the JSON object to find numbers was with a recursive algorithm. 

My [initial solution](https://github.com/jlacar/aoc2015/blob/9a3210cae84cdc5de1e6ad4e6c48abd9a54d04e2/src/main/kotlin/lacar/junilu/Day12.kt) for this part was a somewhat complex recursive structure.

The first insight that helped me tighten up the logic was that the `deepSumOf()` function should be the entry point for the recursion instead of the `sumOfNumbers(JsonObject)` function. Switching to this, I was able to eliminate the dynamic dispatch via function overloading and take advantage of the [automatic type casting](https://kotlinlang.org/docs/typecasts.html) in a `when` expression instead. 

Also, I needed to declare the first parameter as a `JsonElement` instead of `JsonObject` so that the input string could be a Json array without the outermost curly braces `{...}` to make it a proper JsonObject. This meant that the parsing logic had to be declared with`JsonElement`:

    private val json = Json.decodeFromString<JsonElement>(input)

If I declared it as a `JsonObject` instead, the decoding logic would expect a `JsonObject`. This caused some of the Part 1 examples that weren't full JSON objects to throw a parsing exception.

## Aligning around a common solution

The difference in the solutions of the two parts was a little smelly to me, with one using Regex and the other using recursive traversal of a JSON object.

It seemed like it should be possible to use a common core of logic for both parts, with the only difference between the two parts being the condition to skip any JSON objects that have an element with a value of "red".

### High-order functions to the rescue

There were three key insights that led to a successful refactoring of the logic to find numbers in the JSON.

First, I added the `skip` function parameter to the `deepSumOf` function. This expected a predicate that would be used to check if the current element being processed should be skipped or not. Setting a default of `{ false }` simplified the call to `part1()`.  

Second, I realized that it would be better to declare the first parameter to `deepSumOf()` as a `JsonElement` instead of a `JsonObject`. Using `JsonElement`, allowed me to use the  [automatic type casting](https://kotlinlang.org/docs/typecasts.html) in the `when` expression and simplify the dispatching to the correct logic branch to process the next level of nested elements.

Third, I realized that the new `skip` parameter could be assigned a default value that was essentially a "noSkip" option. The default made the call to `deepSumOf()` in part 1 much cleaner.

I was quite happy with how the [final solution](http s://github.com/jlacar/aoc2015/blob/bdafeb1c0ded859faad2567ce2829524a7bfee46/src/main/kotlin/lacar/junilu/Day12.kt) turn out.

### Finishing touches

I did the last few refactorings to make the code more expressive. This included adding an explaining variable for the JSON parsing expression, and renaming the main recursive function so that calls to it were more readable: 

    private val json = Json.decodeFromString<JsonElement>(input) 

    override fun part1() = deepSumOfNumbersIn(json)

I like using names that echo the language used in the problem statement. This makes it easier to create a mental model of the design as you read through the code and map the parts you're reading to your understanding of the problem.