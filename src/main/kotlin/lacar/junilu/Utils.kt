package lacar.junilu

import java.math.BigInteger
import java.security.MessageDigest

fun Any?.println() = println(this)

fun Any?.print() = print(this)

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

/**
 * Calculate permutations of the specified list.
 *
 * Adapted from Python: https://inventwithpython.com/recursion/chapter6.html and feedback
 * from https://www.reddit.com/r/Kotlin/comments/1edxill/seeing_some_strange_behavior_with_apply_scope/
 *
 * @return a List of all permutations of the elements of this list.
 */
fun <T> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return listOf(this)

    return tail.permutations().fold(mutableListOf()) { acc, perm ->
        (0..perm.size).mapTo(acc) { i ->
            perm.subList(0, i) + this@permutations.head + perm.subList(i, perm.size)
        }
    }
}

/**
 * Generate the combinations of this iterable, taken k-elements at a time,
 * as a sequence.
 *
 * @param k the number of elements in each combination
 */
fun <T> Iterable<T>.combinations(k: Int): Sequence<List<T>> =
    sequence {
        val pool = this@combinations as? List<T> ?: toList()
        val n = pool.size
        if (k > n) return@sequence
        val indices = IntArray(k) { it }
        while (true) {
            yield(indices.map { pool[it] })
            var i = k
            do {
                if (i-- == 0) return@sequence
            } while (indices[i] == i + n - k)
            indices[i]++
            for (j in i + 1 until k) indices[j] = indices[j - 1] + 1
        }
    }

/**
 * Converts a string representing a key-value pair into a Pair.
 *
 * Use this as a convenient way to create map entries whose values need to be converted
 *
 * Example:
 * <code>
 *      keyValuePair("a: 2", ": ", String::toInt)   // Pair("a", 2)
 * </code>
 */
fun <R> keyValuePair(item: String, delimiters: String, transform: (String) -> R): Pair<String, R> {
    val (name, value) = item.split(delimiters)
    return name to transform(value)
}

fun <R> toKeyValuePairMap(
    list: String,
    itemDelimiter: String,
    keyValueDelimiter: String,
    transform: (String) -> R
): Map<String, R> =

    list.split(itemDelimiter)
        .associate { keyValuePair(it, keyValueDelimiter, transform) }

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun comboDemo() {
    val combos = listOf(1, 2, 3, 4).combinations(2)
    combos.forEach { println("yielded : $it") }
}

fun main() {
     comboDemo()
    // md5Demo()
}