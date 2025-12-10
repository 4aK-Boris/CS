package dmitriy.losev.cs.proxy

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.MobileProxyDeviceConfig
import dmitriy.losev.cs.clients.MobileProxyServiceNetworkClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Singleton
class MobileProxyManager(
    @Provided private val context: Context,
    private val mobileProxyServiceNetworkClient: MobileProxyServiceNetworkClient
) {

    private val logger = LoggerFactory.getLogger(MobileProxyManager::class.java)

    private val mutex = Mutex()

    private val proxyConfigs: List<MobileProxyDeviceConfig> = context.mobileProxyConfig.mobileProxyDeviceConfigs

    private val steamIdToDeviceId = ConcurrentHashMap<Long, String>()

    private val proxyStats = ConcurrentHashMap<String, MobileProxyStats>()

    private val deviceUsageCount = ConcurrentHashMap<String, AtomicLong>()

    init {
        proxyConfigs.forEach { config ->
            proxyStats[config.deviceId] = MobileProxyStats(deviceId = config.deviceId, deviceName = config.deviceName)
            deviceUsageCount[config.deviceId] = AtomicLong(0)
        }
    }

    suspend fun getProxyConfigForSteamId(steamId: Long): ProxyConfig.Default {
        return mutex.withLock {
            val deviceId = steamIdToDeviceId.getOrPut(steamId) {
                selectLeastUsedDevice()
            }
            deviceUsageCount[deviceId]?.incrementAndGet()
            getProxyConfigByDeviceId(deviceId)
        }
    }

    suspend fun swapProxy(steamId: Long): ProxyConfig.Default {
        return mutex.withLock {
            val currentDeviceId = steamIdToDeviceId[steamId]

            currentDeviceId?.let { deviceId ->
                deviceUsageCount[deviceId]?.decrementAndGet()
            }

            val availableDevices = proxyConfigs
                .map(transform = MobileProxyDeviceConfig::deviceId)
                .filter { deviceId -> deviceId != currentDeviceId }
                .filter { deviceId -> proxyStats[deviceId]?.isHealthy != false }

            val newDeviceId = if (availableDevices.isNotEmpty()) {
                availableDevices.minByOrNull { deviceId -> deviceUsageCount[deviceId]?.get() ?: 0 } ?: selectLeastUsedDevice()
            } else {
                selectLeastUsedDevice()
            }

            steamIdToDeviceId[steamId] = newDeviceId
            deviceUsageCount[newDeviceId]?.incrementAndGet()

            getProxyConfigByDeviceId(newDeviceId)
        }
    }

    fun recordSuccess(steamId: Long) {
        val deviceId = steamIdToDeviceId[steamId] ?: return
        proxyStats[deviceId]?.recordSuccess()
    }

    fun recordFailure(steamId: Long, error: String? = null) {
        val deviceId = steamIdToDeviceId[steamId] ?: return
        proxyStats[deviceId]?.recordFailure(error)
    }

    fun getStats(): List<MobileProxyStats> {
        return proxyStats.values.toList()
    }

    fun getStatsForSteamId(steamId: Long): MobileProxyStats? {
        val deviceId = steamIdToDeviceId[steamId] ?: return null
        return proxyStats[deviceId]
    }

    fun getCurrentDeviceId(steamId: Long): String? {
        return steamIdToDeviceId[steamId]
    }

    fun getCurrentDeviceName(steamId: Long): String? {
        val deviceId = steamIdToDeviceId[steamId] ?: return null
        return proxyConfigs.find { it.deviceId == deviceId }?.deviceName
    }

    fun isProxyHealthy(steamId: Long): Boolean {
        val deviceId = steamIdToDeviceId[steamId] ?: return true
        return proxyStats[deviceId]?.isHealthy ?: true
    }

    fun getUnhealthyProxies(): List<MobileProxyStats> {
        return proxyStats.values.filterNot(predicate = MobileProxyStats::isHealthy)
    }

    fun getBlockedProxies(): List<MobileProxyStats> {
        return proxyStats.values.filter(predicate = MobileProxyStats::isBlocked)
    }

    fun blockProxy(deviceId: String) {
        proxyStats[deviceId]?.block()
    }

    fun unblockProxy(deviceId: String) {
        proxyStats[deviceId]?.unblock()
    }

    fun isProxyBlocked(deviceId: String): Boolean {
        return proxyStats[deviceId]?.isBlocked ?: false
    }

    suspend fun healProxy(deviceId: String, healAction: suspend () -> Unit) {
        blockProxy(deviceId)
        try {
            healAction()
            proxyStats[deviceId]?.resetStats()
        } finally {
            unblockProxy(deviceId)
        }
    }

    suspend fun healProxy(deviceId: String) {
        logger.info("Healing proxy $deviceId...")
        blockProxy(deviceId)
        try {
            val response = mobileProxyServiceNetworkClient.rotate(deviceId)
            if (response.success) {
                logger.info("Proxy $deviceId healed successfully")
                proxyStats[deviceId]?.resetStats()
            } else {
                logger.error("Failed to heal proxy $deviceId: ${response.error}")
            }
        } catch (e: Exception) {
            logger.error("Exception while healing proxy $deviceId: ${e.message}")
        } finally {
            unblockProxy(deviceId)
        }
    }

    suspend fun healAllUnhealthyProxies(healAction: suspend (deviceId: String) -> Unit) {
        getUnhealthyProxies()
            .filterNot(predicate = MobileProxyStats::isBlocked)
            .forEach { stats ->
                healProxy(stats.deviceId) {
                    healAction(stats.deviceId)
                }
            }
    }

    suspend fun healAllUnhealthyProxies() {
        getUnhealthyProxies()
            .filterNot(predicate = MobileProxyStats::isBlocked)
            .forEach { stats ->
                healProxy(stats.deviceId)
            }
    }

    fun removeAssignment(steamId: Long) {
        val deviceId = steamIdToDeviceId.remove(steamId)
        deviceId?.let {
            deviceUsageCount[deviceId]?.decrementAndGet()
        }
    }

    private fun selectLeastUsedDevice(): String {
        val healthyDevices = proxyConfigs
            .filter { proxyConfig -> proxyStats[proxyConfig.deviceId]?.isHealthy != false }

        val candidates = healthyDevices.ifEmpty { proxyConfigs }

        return candidates.minByOrNull { proxyConfig -> deviceUsageCount[proxyConfig.deviceId]?.get() ?: 0 }?.deviceId ?: proxyConfigs.first().deviceId
    }

    private fun getProxyConfigByDeviceId(deviceId: String): ProxyConfig.Default {

        val config = proxyConfigs.find { proxyConfig -> proxyConfig.deviceId == deviceId } ?: error("Proxy config for device $deviceId not found")

        return ProxyConfig.Default(
            host = context.environment.mobileProxyHost,
            port = config.port,
            login = config.login,
            password = config.password
        )
    }
}
