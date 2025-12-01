package dmitriy.losev.cs.validation

import io.konform.validation.Constraint
import io.konform.validation.ValidationBuilder

interface BaseValidation {

    fun ValidationBuilder<String>.positiveLong(): Constraint<String> = constrain(hint = "must be a valid positive number") { number ->
        number.toLongOrNull()?.let { num -> num > 0 } ?: false
    }
}
