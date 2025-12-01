package dmitriy.losev.cs

/**
 * Конфигурация задачи с индивидуальными ограничениями и зависимостями.
 *
 * @param maxConcurrency      максимальное число параллельных экземпляров задачи
 * @param skipIfRunning       пропускать запуск, если уже достигнут maxConcurrency
 * @param dependsOnOnce       список taskId, которые должны ОДИН РАЗ успешно завершиться ДО запуска данной задачи
 */
data class TaskConfig(
    val maxConcurrency: Int = 8,
    val skipIfRunning: Boolean = false,
    val dependsOnOnce: Set<String> = emptySet()
)
