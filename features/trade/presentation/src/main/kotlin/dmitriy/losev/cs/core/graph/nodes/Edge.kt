package dmitriy.losev.cs.core.graph.nodes

data class Edge(
    val targetNodeId: String,
    val condition: EdgeCondition = EdgeCondition.Always
)