package new2025

import java.io.File

fun main() {
    val lines = File("input/year25day01.txt").readLines()

    val part1 = countZeroAtEnd(lines)
    val part2 = countZeroClicks(lines)

    println("Part 1: $part1")
    println("Part 2: $part2")
}

fun countZeroAtEnd(lines: List<String>): Int {
    var position = 50
    var zeroCount = 0

    for (raw in lines) {
        val line = raw.trim()
        if (line.isEmpty()) continue

        val direction = line[0]
        val d = line.substring(1).trim().toLong()

        val step = ((d % 100L).toInt() % 100 + 100) % 100

        position = when (direction) {
            'R' -> (position + step) % 100
            'L' -> (position - step + 100) % 100
            else -> position
        }

        if (position == 0) zeroCount++
    }

    return zeroCount
}

fun countZeroClicks(lines: List<String>): Long {
    var position = 50
    var zeroClicks = 0L

    for (raw in lines) {
        val line = raw.trim()
        if (line.isEmpty()) continue

        val direction = line[0]
        val d = line.substring(1).trim().toLong()

        val hitsZero = when (direction) {
            'R' -> {
                val p = position.toLong()
                (p + d) / 100L
            }
            'L' -> {
                val pPrime = ((100 - position) % 100).toLong()
                (pPrime + d) / 100L
            }
            else -> 0L
        }

        zeroClicks += hitsZero

        val step = ((d % 100L).toInt() % 100 + 100) % 100

        position = when (direction) {
            'R' -> (position + step) % 100
            'L' -> (position - step + 100) % 100
            else -> position
        }
    }

    return zeroClicks
}