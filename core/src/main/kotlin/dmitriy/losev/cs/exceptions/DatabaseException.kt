package dmitriy.losev.cs.exceptions

/**
 * Исключение для ошибок работы с базой данных
 *
 * Примеры использования:
 * - throw DatabaseException("Steam аккаунт с ID $steamId не найден")
 * - throw DatabaseException("Не удалось вставить предмет '$itemName'")
 * - throw DatabaseException("Ошибка при обновлении данных пользователя", cause = sqlException)
 */
class DatabaseException(message: String, cause: Throwable? = null) : BaseException(message, cause)
