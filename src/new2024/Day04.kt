package new2024

import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day04(private val fileName: String) {

    private fun readInput(): List<String> {
        return Path("input/$fileName").readLines()
    }

    fun part1(): Int {
        val grid = readInput()
        val rows = grid.size
        val cols = grid[0].length
        val targetWord = "XMAS"
        val directions = listOf(
            Pair(0, 1),
            Pair(0, -1),
            Pair(1, 0),
            Pair(-1, 0),
            Pair(1, 1),
            Pair(1, -1),
            Pair(-1, 1),
            Pair(-1, -1)
        )

        var count = 0
        
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                for ((dx, dy) in directions) {
                    if (isWordPresent(grid, row, col, dx, dy, targetWord)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    private fun isWordPresent(grid: List<String>, startRow: Int, startCol: Int, dx: Int, dy: Int, word: String): Boolean {
        val rows = grid.size
        val cols = grid[0].length
        val wordLength = word.length

        for (i in 0 until wordLength) {
            val newRow = startRow + i * dx
            val newCol = startCol + i * dy

            if (newRow !in 0 until rows || newCol !in 0 until cols) {
                return false
            }

            if (grid[newRow][newCol] != word[i]) {
                return false
            }
        }
        return true
    }

    fun part2(): Int {
        val grid = readInput()
        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        for (row in 1 until rows - 1) {
            for (col in 1 until cols - 1) {
                if (isXMASPattern(grid, row, col)) {
                    count++
                }
            }
        }
        return count
    }

    private fun isXMASPattern(grid: List<String>, row: Int, col: Int): Boolean {
        val rows = grid.size
        val cols = grid[0].length

        if (row - 1 < 0 || row + 1 >= rows || col - 1 < 0 || col + 1 >= cols) {
            return false
        }

        val masVariants = listOf("MAS", "SAM")

        val tlbr = "${grid[row - 1][col - 1]}${grid[row][col]}${grid[row + 1][col + 1]}"

        val trbl = "${grid[row - 1][col + 1]}${grid[row][col]}${grid[row + 1][col - 1]}"

        return tlbr in masVariants && trbl in masVariants
    }
}

fun main() {
    val day04 = Day04("day04.txt")

    val part1Result = day04.part1()
    println("Part 1: Total occurrences of XMAS: $part1Result")

    val part2Result = day04.part2()
    println("Part 2: Total occurrences of X-MAS patterns: $part2Result")
}
