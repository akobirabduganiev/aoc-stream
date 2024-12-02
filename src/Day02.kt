import kotlin.math.abs

fun main() {
	val lines = readInput("day02.txt")
	val reports = lines.map { line -> line.split(" ").map { it.toInt() } }
	
	val safeCount = reports.count { isSafe(it) }
	val safeCountWithDampener = reports.count { isSafeWithDampener(it) }
	
	println(safeCount)
	println(safeCountWithDampener)
}

// Day 2 - Part 1
fun isSafe(report: List<Int>): Boolean {
	val isIncreasing = report.zipWithNext().all { (a, b) -> b > a }
	val isDecreasing = report.zipWithNext().all { (a, b) -> b < a }
	
	val validDifferences = report.zipWithNext().all { (a, b) -> (1..3).contains(abs(b - a)) }
	
	return (isIncreasing || isDecreasing) && validDifferences
}

// Day 2 - Part 2
fun isSafeWithDampener(report: List<Int>): Boolean {
	// If the report is already safe, return true
	if (isSafe(report)) return true
	
	for (i in report.indices) {
		val modifiedReport = report.filterIndexed { index, _ -> index != i }
		if (isSafe(modifiedReport)) {
			return true // Found a safe configuration by removing one level
		}
	}
	
	return false // No safe configuration found
}

