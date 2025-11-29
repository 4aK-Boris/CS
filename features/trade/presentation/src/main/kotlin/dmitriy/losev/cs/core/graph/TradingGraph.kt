package dmitriy.losev.cs.core.graph

import dmitriy.losev.cs.core.graph.nodes.TradingNode

class TradingGraph(
    val id: String,
    val nodes: Map<String, TradingNode>
) {
    val startNode: TradingNode.StartNode = nodes.values
        .filterIsInstance<TradingNode.StartNode>()
        .firstOrNull() ?: error("Graph must have a StartNode")

    fun getNode(id: String): TradingNode =
        nodes[id] ?: error("Node not found: $id")

    companion object {
        fun build(block: GraphBuilder.() -> Unit): TradingGraph {
            return GraphBuilder().apply(block).build()
        }
    }
}