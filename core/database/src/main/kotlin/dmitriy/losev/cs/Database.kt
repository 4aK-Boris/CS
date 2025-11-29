package dmitriy.losev.cs

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import java.time.Duration
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabaseConfig
import org.jetbrains.exposed.v1.r2dbc.R2dbcTransaction
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

class Database(private val context: Context) {

    private lateinit var database: R2dbcDatabase

    private lateinit var connectionPool: ConnectionPool

    private fun getConnectionPool(): ConnectionPool {

        val databaseUrl = context.environment.databaseUrl
        val databaseUser = context.credentials.databaseUser
        val databasePassword = context.credentials.databasePassword

        val url = databaseUrl.removePrefix("r2dbc:pool:postgresql://")
        val parts = url.split(":")
        val host = parts[0]
        val portAndDb = parts.getOrNull(1)?.split("/") ?: listOf("5432", "cs")
        val port = portAndDb[0].toIntOrNull() ?: 5432
        val database = portAndDb.getOrNull(1) ?: "cs"

        val connectionFactory = PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .database(database)
                .username(databaseUser)
                .password(databasePassword)
                .build()
        )

        val poolConfig = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxSize(8)
            .initialSize(5)
            .maxIdleTime(Duration.ofMinutes(30))
            .maxAcquireTime(Duration.ofSeconds(30))
            .maxCreateConnectionTime(Duration.ofSeconds(30))
            .validationQuery("SELECT 1")
            .build()

        return ConnectionPool(poolConfig)
    }

    private fun connectToDatabase() {

        if (::connectionPool.isInitialized.not()) {
            connectionPool = getConnectionPool()
        }

        if (::database.isInitialized.not()) {
            database = R2dbcDatabase.connect(
                connectionFactory = connectionPool,
                databaseConfig = R2dbcDatabaseConfig {
                    useNestedTransactions = false
                    explicitDialect = PostgreSQLDialect()
                }
            )
        }

    }

    fun disconnect() {
        if (::connectionPool.isInitialized) {
            connectionPool.disposeLater().block()
        }
    }

    suspend fun <T> suspendTransaction(f: suspend R2dbcTransaction.() -> T): T = withContext(context = context.coroutineContext) {
        connectToDatabase()
        suspendTransaction(db = database) {
            addLogger(StdOutSqlLogger)
            f()
        }
    }
}