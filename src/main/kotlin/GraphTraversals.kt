import java.util.*
import kotlin.collections.ArrayList

class GraphTraversals<T> {

    /**
     * Use a Stack.
     */
    fun dfs(root: GraphNode<T>): List<GraphNode<T>> {
        val result = mutableListOf<GraphNode<T>>()
        val stack = Stack<GraphNode<T>>()

        stack.push(root)
        root.level = 0

        while (stack.isNotEmpty()) {
            val node = stack.pop()

            node.isVisited = true

            val adjacent = node.adjacent

            adjacent
                .filterNot { it.isVisited }
                .forEach {
                    stack.push(it)
                    it.level = node.level + 1
                }

            result.add(node)
        }

        return result
    }


    /**
     * Use a Queue.
     */
    fun bfs(root: GraphNode<T>): List<GraphNode<T>> {
        val result = mutableListOf<GraphNode<T>>()
        val queue: Queue<GraphNode<T>> = LinkedList()

        queue.add(root)
        root.level = 0

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

data class GraphNode<T>(
    val data: T,
    val adjacent: List<GraphNode<T>>,
    var isVisited: Boolean = false,
    var level:Int = 0
) {
    override fun toString(): String {
        return "($data, $level)"
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

    val resultB = graphTraversals.dfs(a)

    nodes.forEach {
        it.isVisited = false
        it.level = 0
    }

    val resultA = graphTraversals.bfs(a)

    println("Result BFS = $resultA")

    println("Result DFS = $resultB")

}