class Spotify {

    /**
     * Space Complexity:  O(size of message)
     *
     * Time Complexity: O(Size of message string + Size of document)
     */
    fun canMakeMessage(message: String, doc: String): Boolean {
        val map = createMap(message).toMutableMap()
        println(map)

        doc.forEach {
            if (!map.contains(it)) return@forEach

            val countForChar = map[it] ?: 0

            when (countForChar > 1) {
                true -> map[it] = countForChar - 1
                else -> map.remove(it)
            }
        }

        println(map)

        return map.isEmpty()
    }

    fun createMap(message: String): Map<Char, Int> {
        return createMap(
            message.toList()
        )
    }

    fun <T : Any> createMap(list: List<T>): Map<T, Int> {
        return list
            .groupingBy { it }
            .eachCount()
    }
}

fun main() {
    val solution = Spotify()

    val document = "A quick brown fox jumped over a really really lazy dog blah blah yellow yellow"

    println("map of words = ${solution.createMap(document.split(" "))}")
    println("map of chars = ${solution.createMap(document)}")

    println("====== BEGIN ====== ")

    listOf(
        "" to true,
        "              " to false,
        "quick brown fox" to true,
        "brown fox" to true,
        "yellow tiger" to false,
        "blue lagoon" to true,
        "hello world" to false,
        "xyzabc" to true,
    ).forEachIndexed { index, item ->
        println("# = $index")
        val actual = solution.canMakeMessage(item.first, document)
        val expected = item.second
        val failed = if (actual == expected) "✅" else "⛔"
        println("Solution = $failed")
    }

    println("====== END ====== ")
}