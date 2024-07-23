package lacar.junilu

import kotlin.io.path.Path
import kotlin.io.path.readLines

interface Solution<T> {
    fun part1() : T
    fun part2() : T
}

fun readResource(name: String) = Path("src/main/resources/$name.txt").readLines()