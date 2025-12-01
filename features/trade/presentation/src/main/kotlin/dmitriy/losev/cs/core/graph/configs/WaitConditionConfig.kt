package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface WaitConditionConfig {
    @Serializable
    @SerialName("duration")
    data class Duration(val seconds: Long) : WaitConditionConfig

    @Serializable
    @SerialName("trade_lock_expired")
    data class TradeLockExpired(val itemFromNode: String) : WaitConditionConfig

    @Serializable
    @SerialName("item_in_inventory")
    data class ItemInInventory(val itemFromNode: String) : WaitConditionConfig

    @Serializable
    @SerialName("sale_completed")
    data class SaleCompleted(val saleFromNode: String) : WaitConditionConfig

    @Serializable
    @SerialName("funds_unlocked")
    data class FundsUnlocked(
        val saleFromNode: String,
        val market: String,
        val minDaysToWait: Int? = null
    ) : WaitConditionConfig

    @Serializable
    @SerialName("balance_available")
    data class BalanceAvailable(
        val market: String,
        val minAmountCents: Long
    ) : WaitConditionConfig

    @Serializable
    @SerialName("until_datetime")
    data class UntilDateTime(val timestampMillis: Long) : WaitConditionConfig

    @Serializable
    @SerialName("market_cooldown_expired")
    data class MarketCooldownExpired(
        val market: String,
        val operationType: String = "any"
    ) : WaitConditionConfig

    @Serializable
    @SerialName("all")
    data class All(val conditions: List<WaitConditionConfig>) : WaitConditionConfig

    @Serializable
    @SerialName("any")
    data class Any(val conditions: List<WaitConditionConfig>) : WaitConditionConfig

    @Serializable
    @SerialName("custom")
    data class Custom(
        val checkInterval: Long = 60,
        val maxWaitSeconds: Long = 3600,
        val description: String = "Custom wait condition"
    ) : WaitConditionConfig
}