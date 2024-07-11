package lacar.junilu

import kotlin.io.path.Path
import kotlin.io.path.readLines

interface Describable {
    val description: String
}

abstract class Solution : Describable {
    abstract fun part1() : Any
    abstract fun part2() : Any
}

fun readResource(name: String) = Path("src/main/resources/$name.txt").readLines()