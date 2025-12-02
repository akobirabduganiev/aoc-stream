package new2024

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun readInput(fileName: String): List<String> = Path("input/$fileName").readLines()

fun main() {
	
	part1()
	part2()
	
}

// Day 1 - Part 1
fun part1() {
	val lines = readInput("day01.txt")
	val (left, right) = lines.map { line ->
		val first = line.substringBefore(" ").toInt()
		val second = line.substringAfterLast(" ").toInt()
		first to second
	}.unzip()
	
	val result = left.sorted().zip(right.sorted()).sumOf { (first, second) ->
		abs(first - second)
	}
	println(result)
}

// Day 1 - Part 2
fun part2() {
	val lines = readInput("day01.txt")
	val (left, right) = lines.map { line ->
		val first = line.substringBefore(" ").toInt()
		val second = line.substringAfterLast(" ").toInt()
		first to second
	}.unzip()
	
	val rightCounts = right.groupingBy { it }.eachCount()
	
	val similarityScore = left.sumOf { number ->
		number * (rightCounts[number] ?: 0)
	}
	
	println(similarityScore)
}