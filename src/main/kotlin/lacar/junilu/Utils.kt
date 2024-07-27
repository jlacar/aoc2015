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
            (0..perm.size).fold(allPerms) { subPerms, i ->
                subPerms.add(perm.subList(0, i) + head + perm.subList(i, perm.size))
                subPerms
            }
        }
}

fun main() {
    println(listOf("oneThing").permutations().also { println("Permutations (${it.size})") })
    println(listOf(1, 2, 3, 4, 5).permutations().also { println("Permutations (${it.size}):") })
    println("abc".toList().permutations().also { println("Permutations (${it.size}):") })
    println(emptyList<Int>().permutations().also { println("Permutations (${it.size}):") })
}