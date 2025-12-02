package new2025

import java.io.File

data class Range(val start: Long, val end: Long)

fun main() {
    val raw = File("input/year25day02.txt").readText().trim().trim(',')
    if (raw.isEmpty()) {
        println(0)
        println(0)
        return
    }
    val ranges = raw.split(",")
        .filter { it.isNotBlank() }
        .map {
            val p = it.split("-")
            Range(p[0].trim().toLong(), p[1].trim().toLong())
        }
        .sortedBy { it.start }
    val merged = mergeRanges(ranges)
    val part1 = solvePart1(merged)
    val part2 = solvePart2(merged)
    println(part1)
    println(part2)
}

fun pow10(n: Int): Long {
    var r = 1L
    var i = 0
    while (i < n) {
        r *= 10L
        i++
    }
    return r
}

fun mergeRanges(ranges: List<Range>): List<Range> {
    if (ranges.isEmpty()) return emptyList()
    val result = ArrayList<Range>()
    for (r in ranges) {
        if (result.isEmpty()) {
            result.add(r)
        } else {
            val last = result[result.size - 1]
            if (r.start <= last.end + 1) {
                result[result.size - 1] = Range(last.start, maxOf(last.end, r.end))
            } else {
                result.add(r)
            }
        }
    }
    return result
}

fun inRanges(value: Long, merged: List<Range>): Boolean {
    var l = 0
    var r = merged.size - 1
    while (l <= r) {
        val m = (l + r) ushr 1
        val rg = merged[m]
        if (value < rg.start) {
            r = m - 1
        } else if (value > rg.end) {
            l = m + 1
        } else {
            return true
        }
    }
    return false
}

fun solvePart1(merged: List<Range>): Long {
    if (merged.isEmpty()) return 0L
    val globalMin = merged.first().start
    val globalMax = merged.last().end
    val maxDigits = globalMax.toString().length
    var sum = 0L
    var len = 2
    while (len <= maxDigits) {
        val halfLen = len / 2
        val powHalf = pow10(halfLen)
        var half = powHalf / 10
        val halfMax = powHalf - 1
        while (half <= halfMax) {
            val candidate = half * powHalf + half
            if (candidate > globalMax) break
            if (candidate >= globalMin && inRanges(candidate, merged)) {
                sum += candidate
            }
            half++
        }
        len += 2
    }
    return sum
}

fun solvePart2(merged: List<Range>): Long {
    if (merged.isEmpty()) return 0L
    val globalMin = merged.first().start
    val globalMax = merged.last().end
    val maxDigits = globalMax.toString().length
    val candidates = HashSet<Long>()
    var patternLen = 1
    while (patternLen < maxDigits) {
        val powPattern = pow10(patternLen)
        var pattern = powPattern / 10
        val patternMax = powPattern - 1
        while (pattern <= patternMax) {
            var candidate = pattern * powPattern + pattern
            var totalLen = patternLen * 2
            if (totalLen > maxDigits || candidate > globalMax) break
            while (candidate <= globalMax && totalLen <= maxDigits) {
                if (candidate >= globalMin && inRanges(candidate, merged)) {
                    candidates.add(candidate)
                }
                if (candidate > Long.MAX_VALUE / powPattern) break
                candidate = candidate * powPattern + pattern
                totalLen += patternLen
            }
            pattern++
        }
        patternLen++
    }
    var sum = 0L
    for (v in candidates) {
        sum += v
    }
    return sum
}
