class Solution {
    fun minimumKeypresses(s: String): Int {
        val map = mutableMapOf<Int, MutableList<Char>>()

        for (i in 1..9) {
            map[i] = mutableListOf()
        }

        populateMap(s, map)

        val inputList = s.toCharArray()

        var result = 0

        for (i in inputList) {
            result += lookup(i, map)
        }

        return result
    }

    private fun lookup(letter: Char, map: Map<Int, List<Char>>): Int {
        for ((_, value) in map.entries) {
            if (value.contains(letter)) {
                return value.indexOf(letter) + 1
            }
        }

        return -1
    }

    private fun populateMap(word: String, map: Map<Int, MutableList<Char>>) {
        val allChars = getChars()

        val freqMap = word
            .groupingBy { it }
            .eachCount()

        println("freqMap = $freqMap")

        freqMap
            .toList()
            .sortedByDescending { (_, value) -> value }
            .forEach {
                addItemToMap(it.first, map)
                allChars.remove(it.first)
            }

        println(map)

        allChars.forEach {
            addItemToMap(it, map)
        }

        println(map)
    }

    private fun getChars(): MutableList<Char> {
        val result = mutableListOf<Char>()
        var c = 'a'
        while (c <= 'z') {
            result.add(c)
            ++c
        }
        return result
    }

    private fun addItemToMap(char: Char, map: Map<Int, MutableList<Char>>) {
        for (i in 0..3) {
            insertInMapAtPosition(char, map, i)
        }
    }

    private fun insertInMapAtPosition(char: Char, map: Map<Int, MutableList<Char>>, size: Int) {
        val list: MutableList<Char>? = map
            .filterValues { it.size == size }
            .map { it.value }
            .firstOrNull()

        if (!alreadyContains(char, map)) {
            list?.add(char)
        }
    }

    private fun alreadyContains(char: Char, map: Map<Int, MutableList<Char>>): Boolean {
        return map
            .asSequence()
            .any { it.value.contains(char) }
    }
}

fun main() {
    val solution = Solution()

    listOf(
        "abcdefghijkl",
        "apple",
        "aaaaaaaabcdefgggghijkllllllllllmmmnoppponono"
    ).forEach {
        println("Solution = ${solution.minimumKeypresses(it)}\n\n")
    }

    val x: List<String?>? = listOf("a", "b", "c", null, "d", null)

    val a : List<Int> = x
        ?.mapNotNull { it?.length }
        ?: emptyList()
}