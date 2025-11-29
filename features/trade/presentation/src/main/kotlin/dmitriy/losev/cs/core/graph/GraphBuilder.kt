package dmitriy.losev.cs.core.graph

import dmitriy.losev.cs.core.graph.context.ExecutionContext
import dmitriy.losev.cs.core.graph.dsl.GraphDsl
import dmitriy.losev.cs.core.graph.nodes.Edge
import dmitriy.losev.cs.core.graph.nodes.EdgeCondition
import dmitriy.losev.cs.core.graph.nodes.TradingNode
import dmitriy.losev.cs.core.graph.strategy.ItemSelector
import dmitriy.losev.cs.core.graph.strategy.PriceConstraint
import dmitriy.losev.cs.core.graph.strategy.PriceStrategy
import dmitriy.losev.cs.core.graph.strategy.WaitCondition
import dmitriy.losev.cs.core.market.Market

@GraphDsl
class GraphBuilder {
    private val nodes = mutableMapOf<String, TradingNode>()
    private var graphId = "graph-${System.currentTimeMillis()}"

    fun id(id: String) { graphId = id }

    fun start(vararg nextIds: String) {
        nodes["start"] = TradingNode.StartNode(next = nextIds.map { Edge(it) })
    }

    fun buy(
        id: String,
        market: Market,
        item: ItemSelector,
        maxPrice: PriceConstraint,
        waitForCompletion: Boolean,
        next: String
    ) {
        nodes[id] = TradingNode.BuyNode(id, market, item, maxPrice, waitForCompletion, listOf(Edge(next)))
    }

    fun buy(id: String, block: BuyNodeBuilder.() -> Unit) {
        val builder = BuyNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    fun sell(
        id: String,
        market: Market,
        itemFrom: String,
        price: PriceStrategy,
        next: String
    ) {
        nodes[id] = TradingNode.SellNode(
            id, market,
            itemFrom,
            price,
            true,
            listOf(Edge(next))
        )
    }

    fun sell(id: String, block: SellNodeBuilder.() -> Unit) {
        val builder = SellNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    fun wait(id: String, condition: WaitCondition, next: String) {
        nodes[id] = TradingNode.WaitNode(id, condition, listOf(Edge(next)))
    }

    fun wait(id: String, block: WaitNodeBuilder.() -> Unit) {
        val builder = WaitNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    fun condition(id: String, block: ConditionNodeBuilder.() -> Unit) {
        val builder = ConditionNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    // Batch operations support
    fun batchBuy(id: String, block: BatchBuyNodeBuilder.() -> Unit) {
        val builder = BatchBuyNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    fun batchSell(id: String, block: BatchSellNodeBuilder.() -> Unit) {
        val builder = BatchSellNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    fun itemPool(id: String, block: ItemPoolNodeBuilder.() -> Unit) {
        val builder = ItemPoolNodeBuilder(id).apply(block)
        nodes[id] = builder.build()
    }

    // Generic node method for direct node construction (used in examples)
    fun node(node: TradingNode) {
        nodes[node.id] = node
    }

    fun end() {
        nodes["end"] = TradingNode.EndNode()
    }

    fun build() = TradingGraph(graphId, nodes)
}

// DSL Builders для каждого типа узла

@GraphDsl
class BuyNodeBuilder(private val id: String) {
    var market: Market? = null
    var itemSelector: ItemSelector? = null
    var maxPrice: PriceConstraint? = null
    private val edges = mutableListOf<Edge>()

    fun onSuccess(nodeId: String) {
        edges.add(Edge(nodeId, EdgeCondition.OnSuccess()))
    }

    fun onFailure(nodeId: String, retryCount: Int = 0) {
        edges.add(Edge(nodeId, EdgeCondition.OnFailure(retryCount)))
    }

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.BuyNode {
        return TradingNode.BuyNode(
            id = id,
            market = market ?: error("Market is required"),
            itemSelector = itemSelector ?: error("Item selector is required"),
            maxPrice = maxPrice ?: error("Max price is required"),
            next = edges
        )
    }
}

@GraphDsl
class SellNodeBuilder(private val id: String) {
    var market: Market? = null
    var itemRef: String? = null
    var priceStrategy: PriceStrategy? = null
    private val edges = mutableListOf<Edge>()

    fun onSuccess(nodeId: String) {
        edges.add(Edge(nodeId, EdgeCondition.OnSuccess()))
    }

    fun onFailure(nodeId: String, retryCount: Int = 0) {
        edges.add(Edge(nodeId, EdgeCondition.OnFailure(retryCount)))
    }

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.SellNode {
        return TradingNode.SellNode(
            id = id,
            market = market ?: error("Market is required"),
            itemRef = itemRef ?: error("Item reference is required"),
            priceStrategy = priceStrategy ?: error("Price strategy is required"),
            next = edges
        )
    }
}

@GraphDsl
class WaitNodeBuilder(private val id: String) {
    var condition: WaitCondition? = null
    private val edges = mutableListOf<Edge>()

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.WaitNode {
        return TradingNode.WaitNode(
            id = id,
            condition = condition ?: error("Wait condition is required"),
            next = edges
        )
    }
}

@GraphDsl
class ConditionNodeBuilder(private val id: String) {
    var check: ((ExecutionContext) -> Boolean)? = null
    var onTrue: String? = null
    var onFalse: String? = null

    fun build(): TradingNode.ConditionNode {
        return TradingNode.ConditionNode(
            id = id,
            check = check ?: error("Check function is required"),
            onTrue = onTrue ?: error("OnTrue node is required"),
            onFalse = onFalse ?: error("OnFalse node is required")
        )
    }
}

// Batch operation builders

@GraphDsl
class BatchBuyNodeBuilder(private val id: String) {
    var market: Market? = null
    var itemSelector: ItemSelector? = null
    var maxPrice: PriceConstraint? = null
    var executionMode: dmitriy.losev.cs.core.graph.nodes.ExecutionMode = dmitriy.losev.cs.core.graph.nodes.ExecutionMode.SEQUENTIAL
    var continuousBuying: Boolean = false
    var poolId: String = "default"
    private val edges = mutableListOf<Edge>()

    fun onSuccess(nodeId: String) {
        edges.add(Edge(nodeId, EdgeCondition.OnSuccess()))
    }

    fun onFailure(nodeId: String, retryCount: Int = 0) {
        edges.add(Edge(nodeId, EdgeCondition.OnFailure(retryCount)))
    }

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.BatchBuyNode {
        return TradingNode.BatchBuyNode(
            id = id,
            market = market ?: error("Market is required"),
            itemSelector = itemSelector ?: error("Item selector is required"),
            maxPrice = maxPrice ?: error("Max price is required"),
            executionMode = executionMode,
            continuousBuying = continuousBuying,
            poolId = poolId,
            next = edges
        )
    }
}

@GraphDsl
class BatchSellNodeBuilder(private val id: String) {
    var market: Market? = null
    var itemPoolRef: String? = null
    var priceStrategy: PriceStrategy? = null
    var executionMode: dmitriy.losev.cs.core.graph.nodes.ExecutionMode = dmitriy.losev.cs.core.graph.nodes.ExecutionMode.SEQUENTIAL
    var sellCondition: dmitriy.losev.cs.core.graph.nodes.SellCondition = dmitriy.losev.cs.core.graph.nodes.SellCondition.All
    private val edges = mutableListOf<Edge>()

    fun onSuccess(nodeId: String) {
        edges.add(Edge(nodeId, EdgeCondition.OnSuccess()))
    }

    fun onFailure(nodeId: String, retryCount: Int = 0) {
        edges.add(Edge(nodeId, EdgeCondition.OnFailure(retryCount)))
    }

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.BatchSellNode {
        return TradingNode.BatchSellNode(
            id = id,
            market = market ?: error("Market is required"),
            itemPoolRef = itemPoolRef ?: error("Item pool reference is required"),
            priceStrategy = priceStrategy ?: error("Price strategy is required"),
            executionMode = executionMode,
            sellCondition = sellCondition,
            next = edges
        )
    }
}

@GraphDsl
class ItemPoolNodeBuilder(private val id: String) {
    var poolId: String? = null
    var operation: dmitriy.losev.cs.core.graph.nodes.PoolOperation? = null
    var condition: dmitriy.losev.cs.core.graph.nodes.PoolCondition? = null
    private val edges = mutableListOf<Edge>()

    fun next(nodeId: String) {
        edges.add(Edge(nodeId))
    }

    fun build(): TradingNode.ItemPoolNode {
        return TradingNode.ItemPoolNode(
            id = id,
            poolId = poolId ?: error("Pool ID is required"),
            operation = operation ?: error("Pool operation is required"),
            condition = condition,
            next = edges
        )
    }
}