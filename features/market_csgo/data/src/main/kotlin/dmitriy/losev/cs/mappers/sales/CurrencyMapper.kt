package dmitriy.losev.cs.mappers.sales

import dmitriy.losev.cs.dso.sales.CurrencyDSO
import dmitriy.losev.cs.dto.sales.CurrencyDTO
import org.koin.core.annotation.Factory

@Factory
internal class CurrencyMapper {

    fun map(value: CurrencyDSO): CurrencyDTO {
        return CurrencyDTO(
            rub = value.rub,
            usd = value.usd,
            eur = value.eur
        )
    }
}