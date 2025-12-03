package new2025

import java.io.FileReader
import java.io.BufferedReader

fun maxSubsequenceNumber(line: String, k: Int): String {
    val s = line.trim()
    val n = s.length
    if (n <= k) return s
    val keep = k
    val stack = StringBuilder()
    var toRemove = n - keep

    for (ch in s) {
        while (stack.isNotEmpty() && toRemove > 0 && stack[stack.length - 1] < ch) {
            stack.deleteCharAt(stack.length - 1)
            toRemove--
        }
        stack.append(ch)
    }

    return if (stack.length > keep) stack.substring(0, keep) else stack.toString()
}

fun solvePart1(lines: List<String>): Long {
    var total = 0L
    for (raw in lines) {
        val s = raw.trim()
        if (s.length < 2) continue
        val numStr = maxSubsequenceNumber(s, 2)
        total += numStr.toLong()
    }
    return total
}

fun solvePart2(lines: List<String>): Long {
    var total = 0L
    val k = 12
    for (raw in lines) {
        val s = raw.trim()
        if (s.isEmpty()) continue
        val numStr = if (s.length <= k) s else maxSubsequenceNumber(s, k)
        total += numStr.toLong()
    }
    return total
}

fun readInputLines(): List<String> {
    val reader = BufferedReader(FileReader("input/year25day03.txt"))
    val result = mutableListOf<String>()
    while (true) {
        val line = reader.readLine() ?: break
        if (line.isNotBlank()) result.add(line)
    }
    reader.close()
    return result
}

fun main() {
    val lines = readInputLines()
    val part1 = solvePart1(lines)
    val part2 = solvePart2(lines)
    println(part1)
    println(part2)
}
