package lacar.junilu

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

/**
 * Recursively calculate permutations of the specified list
 *
 * Adapted from Python: https://inventwithpython.com/recursion/chapter6.html
 */
fun <T> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return listOf(this)

    return tail.permutations()
        .fold(mutableListOf()) { allPerms, perm ->
            (0..perm.size).fold(allPerms) { acc, i ->
                acc.also { it.add(perm.subList(0, i) + head + perm.subList(i, perm.size)) }
            }
        }
}

fun main() {
    fun demo(aList: List<Any>) {
        val perms = aList.permutations()
        println("""${aList}.permutations (size=${perms.size}) = 
            |${perms.joinToString("\n")}""".trimMargin())
        println()
    }
    demo(emptyList())
    demo(listOf("oneThing"))
    demo(listOf('0', '1'))
    demo("abc".toList())
    demo(listOf(1, 2, 3, 4))
}