package dmitriy.losev.cs.core.graph.configs

enum class ConditionType {
    ALWAYS,
    ON_SUCCESS,
    ON_FAILURE,
    EXPRESSION  // для сложных условий
}