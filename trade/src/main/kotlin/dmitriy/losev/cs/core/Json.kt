package dmitriy.losev.cs.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint = true                    // Читаемый JSON в ответах
    isLenient = true                      // Мягкий парсинг
    ignoreUnknownKeys = true              // Игнорировать неизвестные поля
    encodeDefaults = true                 // Включать поля с дефолтными значениями
    explicitNulls = false                 // Не включать null поля
    coerceInputValues = true              // Приводить некорректные значения к дефолтным
    namingStrategy = JsonNamingStrategy.SnakeCase
}
