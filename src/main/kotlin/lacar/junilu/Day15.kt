package lacar.junilu

class Day15(val ingredients: List<Ingredient>, val teaspoonsTotal: Int) : Solution<Int> {
    
    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun using(input: List<String>, teaspoonsTotal: Int): Day15 {
            val ingredients = input.map { line ->
                val props = line
                    .split(": ")[1].split(", ")
                    .mapTo(mutableListOf()) { prop ->
                        val (name, units) = prop.split(" ")
                        name to units.toInt()
                    }.toMap()

                Ingredient(
                    capacity = props["capacity"] ?: 0,
                    durability = props["durability"] ?: 0,
                    flavor = props["flavor"] ?: 0,
                    texture = props["texture"] ?: 0,
                    calories = props["calories"] ?: 0
                )
            }

            return Day15(ingredients, teaspoonsTotal)
        }
    }

    data class Ingredient(
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int) {
    }
}

