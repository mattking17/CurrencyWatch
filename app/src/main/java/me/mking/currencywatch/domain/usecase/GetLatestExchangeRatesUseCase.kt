package me.mking.currencywatch.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.mking.currencywatch.domain.entity.CurrencyEntity
import me.mking.currencywatch.domain.entity.ExchangeRateEntity
import me.mking.currencywatch.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetLatestExchangeRatesUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    fun execute(base: CurrencyEntity): Flow<GetLatestExchangeRatesResult> =
        exchangeRateRepository.latest(base).map {
            GetLatestExchangeRatesResult(
                base = base,
                rates = it
            )
        }

}

data class GetLatestExchangeRatesResult(
    val base: CurrencyEntity,
    val rates: List<ExchangeRateEntity>
)