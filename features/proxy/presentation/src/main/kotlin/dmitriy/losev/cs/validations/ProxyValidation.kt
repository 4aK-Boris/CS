package dmitriy.losev.cs.validations

import dmitriy.losev.cs.models.DeleteProxyConfigRequestModel
import dmitriy.losev.cs.models.UpsertProxyConfigRequestModel
import dmitriy.losev.cs.validation.BaseValidation
import io.konform.validation.Validation
import io.konform.validation.constraints.maximum
import io.konform.validation.constraints.minimum
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.pattern
import io.konform.validation.onEach
import org.koin.core.annotation.Singleton

@Singleton
class ProxyValidation: BaseValidation {

    val validateUpsertProxyConfigs = Validation<List<UpsertProxyConfigRequestModel>> {
        onEach {
            UpsertProxyConfigRequestModel::host {
                minLength(1) hint "host is required"
                pattern(pattern = IP_ADDRESS_PATTERN) hint "host must be a valid domain or IP address"
            }
            UpsertProxyConfigRequestModel::port {
                minimum(1) hint "port must be at least 1"
                maximum(65535) hint "port must be at most 65535"
            }
            UpsertProxyConfigRequestModel::login {
                minLength(1) hint "login is required"
            }
            UpsertProxyConfigRequestModel::password {
                minLength(1) hint "password is required"
            }
        }
    }

    val validateDeleteProxyConfig = Validation {
        DeleteProxyConfigRequestModel::host {
            minLength(length = 1) hint "host is required"
            pattern(pattern = IP_ADDRESS_PATTERN) hint "host must be a valid domain or IP address"
        }
        DeleteProxyConfigRequestModel::port {
            minimum(1) hint "port must be at least 1"
            maximum(65535) hint "port must be at most 65535"
        }
    }

    companion object {
        private const val IP_ADDRESS_PATTERN = "^(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?|(?:\\d{1,3}\\.){3}\\d{1,3})$"
    }
}
