import java.util.*

class GraphTraversals<T> {
}

data class GraphNode<T>(
    val data: T,
    val adjacent: List<GraphNode<T>>,
    var isVisited: Boolean = false,
    var level: Int = 0
) {
    override fun toString(): String {
        return "($data: $level)"
    }

    /**
     * Use a Stack.
     */
    fun dfs(): List<GraphNode<T>> {
        val result = mutableListOf<GraphNode<T>>()
        val stack = Stack<GraphNode<T>>()

        stack.push(this)

        while (stack.isNotEmpty()) {
            val node = stack.pop()

            node.isVisited = true

            result.add(node)

            val adjacent = node.adjacent

            println("Adj of $node: $adjacent")

            adjacent
                .filterNot { it.isVisited }
                .forEach {
                    stack.push(it)
                    it.level = node.level + 1
                }

        }

        return result
    }


    /**
     * Use a Queue.
     */
    fun bfs(): List<GraphNode<T>> {
        val result = mutableListOf<GraphNode<T>>()
        val queue: Queue<GraphNode<T>> = LinkedList()

        queue.add(this)

        while (queue.isNotEmpty()) {
            val node = queue.remove()
            node.isVisited = true

            val adjacent = node.adjacent

            adjacent
                .filterNot { it.isVisited }
                .forEach {
                    queue.add(it)
                    it.level = node.level + 1
                }

            result.add(node)
        }

        return result
    }

}


fun main() {
    val graphTraversals = GraphTraversals<String>()

    val g = GraphNode("G", emptyList())
    val h = GraphNode("H", emptyList())
    val i = GraphNode("I", emptyList())

    val d = GraphNode("D", emptyList())
    val e = GraphNode("E", emptyList())
    val f = GraphNode("F", emptyList())

    val b = GraphNode("B", listOf(d, e, f))
    val c = GraphNode("C", listOf(g, h, i))
    val a = GraphNode("A", listOf(b, c))

    val nodes = listOf(a, b, c, d, e, f, g, h, i)

    val resultB = a.dfs()

    nodes.forEach {
        it.isVisited = false
        it.level = 0
    }

    val resultA = a.bfs()

    println("Result BFS = $resultA")

    println("Result DFS = $resultB")

}