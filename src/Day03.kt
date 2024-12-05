import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day03(private val fileName: String) {
    
    private val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    private val doRegex = Regex("""\bdo\(\)""")
    private val dontRegex = Regex("""\bdon't\(\)""")
    
    private fun readInput(): String {
        return Path("input/$fileName").readLines().joinToString("")
    }
    
    fun part1(): Int {
        val input = readInput()
        return mulRegex.findAll(input).sumOf { matchResult ->
            val (x, y) = matchResult.destructured
            x.toInt() * y.toInt()
        }
    }
    
    fun part2(): Int {
        val input = readInput()
        val instructions = mutableListOf<Pair<Int, String>>()
        
        mulRegex.findAll(input).forEach { instructions.add(it.range.first to it.value) }
        doRegex.findAll(input).forEach { instructions.add(it.range.first to "do") }
        dontRegex.findAll(input).forEach { instructions.add(it.range.first to "don't") }
        
        instructions.sortBy { it.first }
        
        var enabled = true
        var totalSum = 0
        
        for ((_, instruction) in instructions) {
            when {
                instruction == "do" -> enabled = true
                instruction == "don't" -> enabled = false
                instruction.startsWith("mul") && enabled -> {
                    val match = mulRegex.matchEntire(instruction)!!
                    val (x, y) = match.destructured
                    totalSum += x.toInt() * y.toInt()
                }
            }
        }
        
        return totalSum
    }
}

fun main() {
    val day03 = Day03("day03.txt")
    
    val part1Result = day03.part1()
    println("Part 1: Total sum of all valid multiplications: $part1Result")
    
    val part2Result = day03.part2()
    println("Part 2: Total sum of enabled multiplications: $part2Result")
}
