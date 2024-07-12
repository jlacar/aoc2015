package lacar.junilu

import jdk.jfr.Unsigned

class Day07(private val instructions: List<String>) : Solution<Int>() {

    val wires : MutableMap<String, Int> = mutableMapOf("a" to 0)

    override fun part1(): Int {
        instructions.forEach { instruction ->
            Day7Command(instruction).exec(wires)
        }
        return wires["a"]!!
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}

const val DAY7_MAX_SIGNAL = 65536

data class Day7Command(val details: String) {
    val parts = details.split(" -> ")
    val wire = parts.last()
    val expression = parts.first()


    fun exec(wires : MutableMap<String, Int>) {
        val parts = expression.split(" ")

        if (parts.first() == "NOT") {
            wires[wire] = DAY7_MAX_SIGNAL + wires[parts[1]]!!.inv()
            return
        }

        wires[wire] = expression.toInt()
    }
}