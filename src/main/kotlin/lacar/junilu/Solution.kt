package lacar.junilu

import kotlin.io.path.Path
import kotlin.io.path.readLines

abstract class Solution<T> {
    abstract fun part1() : T
    abstract fun part2() : T
}

fun readResource(name: String) = Path("src/main/resources/$name.txt").readLines()