import java.util.*
import java.util.function.Consumer

internal class TopoSorter<T>(
    private val graph: Graph<T>
) {
    fun sort(): List<T> {
        val result: Deque<T> = ArrayDeque()
        val stack: Deque<T> = ArrayDeque()
        val visited: MutableSet<T> = HashSet()
        graph.nodes().forEach { node: T -> visit(node, visited, stack, result) }
        return result.toList()
    }

    private fun visit(
        node: T,
        visited: MutableSet<T>,
        stack: Deque<T>,
        result: Deque<T>
    ) {
        if (visited.add(node)) {
            stack.push(node)
            graph.adjacentNodes(node).forEach { child: T -> visit(child, visited, stack, result) }
            stack.pop()
            result.addLast(node)
        } else require(!stack.contains(node)) {
            "Cycle detected: " + node + " -> " + graph.adjacentNodes(node)
        }
    }
}

internal class Graph<T>(
    private val nodes: Set<T>,
    private val edges: Map<T, Set<T>>
) {

    fun nodes() = nodes

    fun edges() = edges

    fun adjacentNodes(u: T) = edges[u] ?: emptySet()

    operator fun contains(u: T): Boolean = nodes.contains(u)
}

/**
 * A --> B
 * B --> C
 * C --> D
 * D --> E
 * E --> F
 * F --> NULL
 */
fun main() {
    val nodes = mutableSetOf("A", "B", "C", "D", "E", "F", "NULL")
    val edges = mutableMapOf(
        "A" to setOf("B", "C"),
        "B" to setOf("NULL"),
        "C" to setOf("D"),
        "D" to setOf("E", "F"),
        "E" to setOf("NULL"),
        "F" to setOf("NULL"),
    )

    val graph = Graph(nodes, edges)

    val topoSorter = TopoSorter(graph)

    println("Result after sorting = ${topoSorter.sort()}")
}