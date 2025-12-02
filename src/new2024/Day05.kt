package new2024

import java.io.File
import java.util.LinkedList
import java.util.Queue
import kotlin.collections.iterator

fun main() {
    val inputFilePath = "input/day05.txt"
    
    // Read all lines from the input file
    val lines = File(inputFilePath).readLines()
    
    // Separate ordering rules and updates
    val blankLineIndex = lines.indexOfFirst { it.trim().isEmpty() }
    
    if (blankLineIndex == -1) {
        println("Input file must contain a blank line separating rules and updates.")
        return
    }
    
    val ruleLines = lines.subList(0, blankLineIndex)
    val updateLines = lines.subList(blankLineIndex + 1, lines.size)
    
    val rules = mutableListOf<Pair<Int, Int>>()
    for (line in ruleLines) {
        val parts = line.split("|")
        if (parts.size != 2) {
            println("Invalid rule format: $line")
            continue
        }
        val x = parts[0].trim().toIntOrNull()
        val y = parts[1].trim().toIntOrNull()
        if (x != null && y != null) {
            rules.add(Pair(x, y))
        } else {
            println("Invalid numbers in rule: $line")
        }
    }
    
    val updates = mutableListOf<List<Int>>()
    for (line in updateLines) {
        if (line.trim().isEmpty()) continue // Skip empty lines
        val pages = line.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
        if (pages.isNotEmpty()) {
            updates.add(pages)
        } else {
            println("Invalid update line: $line")
        }
    }
    
    fun isUpdateCorrect(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        val pageToIndex = update.withIndex().associate { it.value to it.index }
        
        for ((x, y) in rules) {
            if (x in pageToIndex && y in pageToIndex) {
                if (pageToIndex[x]!! > pageToIndex[y]!!) {
                    // X should come before Y, but it doesn't
                    return false
                }
            }
        }
        return true
    }
    
    fun topologicalSort(pages: List<Int>, rules: List<Pair<Int, Int>>): List<Int>? {
        val adjacency = mutableMapOf<Int, MutableList<Int>>()
        val inDegree = mutableMapOf<Int, Int>()
        
        for (page in pages) {
            adjacency[page] = mutableListOf()
            inDegree[page] = 0
        }
        
        for ((x, y) in rules) {
            if (x in adjacency && y in adjacency) {
                adjacency[x]?.add(y)
                inDegree[y] = inDegree[y]!! + 1
            }
        }
        
        val queue: Queue<Int> = LinkedList()
        for ((page, degree) in inDegree) {
            if (degree == 0) {
                queue.add(page)
            }
        }
        
        val sortedList = mutableListOf<Int>()
        
        while (queue.isNotEmpty()) {
            val current = queue.poll()
            sortedList.add(current)
            
            for (neighbor in adjacency[current]!!) {
                inDegree[neighbor] = inDegree[neighbor]!! - 1
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor)
                }
            }
        }
        
        return if (sortedList.size == pages.size) sortedList else null
    }
    
    fun findMiddlePage(pages: List<Int>): Int {
        val size = pages.size
        val middleIndex = size / 2
        return pages[middleIndex]
    }
    
    val correctlyOrderedUpdates = mutableListOf<List<Int>>()
    val incorrectlyOrderedUpdates = mutableListOf<List<Int>>()
    
    for (update in updates) {
        if (isUpdateCorrect(update, rules)) {
            correctlyOrderedUpdates.add(update)
        } else {
            incorrectlyOrderedUpdates.add(update)
        }
    }
    
    val middlePagesPart1 = correctlyOrderedUpdates.map { findMiddlePage(it) }
    val sumPart1 = middlePagesPart1.sum()
    
    val middlePagesPart2 = mutableListOf<Int>()
    
    for (update in incorrectlyOrderedUpdates) {
        val sorted = topologicalSort(update, rules)
        if (sorted != null) {
            middlePagesPart2.add(findMiddlePage(sorted))
        } else {
            println("Failed to sort update: ${update.joinToString(",")}")
        }
    }
    
    val sumPart2 = middlePagesPart2.sum()
    
    println("Part One:")
    println("Sum of middle page numbers from correctly ordered updates: $sumPart1\n")
    
    println("Part Two:")
    println("Sum of middle page numbers after reordering incorrectly ordered updates: $sumPart2")
}
