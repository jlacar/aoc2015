package lacar.junilu

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
fun <T : Any> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return listOf(this)

    return tail.permutations().fold(mutableListOf()) { acc, perm ->
        (0..perm.size).mapTo(acc) { i ->
            perm.subList(0, i) + head + perm.subList(i, perm.size)
        }
    }
}

fun main() {
    fun demo(aList: List<Any>) {
        val perms = aList.permutations()
        println(perms.size)
    }
//    demo("abc".toList())
//    demo(emptyList())
//    demo(listOf("oneThing"))
//    demo(listOf('0', '1'))
    demo(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
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