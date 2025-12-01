package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.RSAKeyDSO
import dmitriy.losev.cs.dto.RSAKeyDTO
import org.koin.core.annotation.Factory

@Factory
class RSAKeyMapper {

    fun map(value: RSAKeyDSO): RSAKeyDTO {
        return RSAKeyDTO(
            success = value.success,
            publickeyModulus = value.publickeyModulus,
            publickeyExponent = value.publickeyExponent,
            timeStamp = value.timeStamp
        )
    }
}
