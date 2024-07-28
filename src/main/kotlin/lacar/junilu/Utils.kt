package lacar.junilu

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

/**
 * Recursively calculate permutations of the specified list
 *
 * Adapted from Python: https://inventwithpython.com/recursion/chapter6.html and feedback
 * from https://www.reddit.com/r/Kotlin/comments/1edxill/seeing_some_strange_behavior_with_apply_scope/
 */
fun <T> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return listOf(this)
    val allPerms = mutableListOf<List<T>>()
    return tail.permutations()
        .forEach { perm ->
            (0..perm.size).mapTo(allPerms) { i ->
                perm.subList(0, i) + head + perm.subList(i, perm.size)
            }
        }.let { allPerms }
}

fun main() {
    fun demo(aList: List<Any>) {
        val perms = aList.permutations()
        println(
            """${aList}.permutations (size=${perms.size}) = 
            |${perms.joinToString("\n")}""".trimMargin()
        )
        println()
    }
    demo(emptyList())
    demo(listOf("oneThing"))
    demo(listOf('0', '1'))
    demo("abc".toList())
    demo(listOf(1, 2, 3, 4))
}