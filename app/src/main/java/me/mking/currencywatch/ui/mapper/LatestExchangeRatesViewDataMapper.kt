package me.mking.currencywatch.ui.mapper

import me.mking.currencywatch.domain.usecase.GetLatestExchangeRatesResult
import me.mking.currencywatch.ui.LatestExchangeRatesViewData
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class LatestExchangeRatesViewDataMapper @Inject constructor() :
    ViewDataMapper<LatestExchangeRatesViewDataInput, LatestExchangeRatesViewData> {
    override fun map(input: LatestExchangeRatesViewDataInput): LatestExchangeRatesViewData {
        val (baseAmount, latestExchangeRatesResult) = input
        val baseDouble = BigDecimal(baseAmount).setScale(3, BigDecimal.ROUND_HALF_UP).toDouble()
        return LatestExchangeRatesViewData(
            baseCurrency = latestExchangeRatesResult.base,
            baseAmount = baseAmount,
            baseCurrencySymbol = mapToCurrencySymbol(latestExchangeRatesResult.base.code),
            rates = latestExchangeRatesResult.rates.map {
                LatestExchangeRatesViewData.ExchangeRate(
                    name = it.name,
                    rate = String.format("%,.3f", it.rate),
                    symbol = mapToCurrencySymbol(it.name),
                    value = String.format(
                        "%,.3f",
                        BigDecimal(it.rate * baseDouble).setScale(3, BigDecimal.ROUND_HALF_UP)
                            .toDouble()
                    )
                )
            }
        )
    }

    private fun mapToCurrencySymbol(code: String): String {
        return Currency.getInstance(code).getSymbol(Locale.getDefault())
    }
}

data class LatestExchangeRatesViewDataInput(
    val baseAmount: String,
    val latestExchangeRatesResult: GetLatestExchangeRatesResult
) : ViewDataMapperInput