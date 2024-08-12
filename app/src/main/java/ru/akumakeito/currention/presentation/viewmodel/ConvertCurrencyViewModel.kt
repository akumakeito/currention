package ru.akumakeito.currention.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.repository.PairCurrencyRepository
import ru.akumakeito.currention.domain.state.ErrorType
import ru.akumakeito.currention.domain.state.StateModel
import ru.akumakeito.currention.presentation.util.Constants.Companion.defaultConvertingCurrency
import ru.akumakeito.currention.presentation.util.format
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository
) : ViewModel() {

    private val _convertingCurrencyState = MutableStateFlow(defaultConvertingCurrency)
    val convertingCurrencyState = _convertingCurrencyState.asStateFlow()

    private val sum = MutableStateFlow("")

    private val _uiState = MutableStateFlow(StateModel())
    val uiState = _uiState.asStateFlow()

    init {
        try {
            _uiState.update {
                it.copy(isLoading = true)
            }
            convertForOne(
                _convertingCurrencyState.value.firstCurrency,
                _convertingCurrencyState.value.secondCurrency,
            )
            convertForOne(
                _convertingCurrencyState.value.secondCurrency,
                _convertingCurrencyState.value.firstCurrency,

                )

            _uiState.update {
                StateModel()
            }
        } catch (networkException: IOException) {
            _uiState.update {
                it.copy(isError = ErrorType.NETWORK)
            }
        }


    }

    fun clearAmount() {
        sum.value = ""
        _convertingCurrencyState.value =
            _convertingCurrencyState.value.copy(amount = null, convertedAmount = 0.0)
    }

    fun changeAmount(amount: String) {
        sum.value = amount

        Log.d("CurrencyConverterScreen", "changeAmount: ${_convertingCurrencyState.value.amount}")

    }

    fun convert() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            _convertingCurrencyState.update {
                it.copy(amount = parseAmount(sum.value))
            }
            val result =
                pairCurrencyRepository.convert(
                    _convertingCurrencyState.value.firstCurrency,
                    _convertingCurrencyState.value.secondCurrency,
                    _convertingCurrencyState.value.amount ?: 0.0
                )

            val convertedAmount = result.value.format(2)


            _convertingCurrencyState.update {
                it.copy(
                    convertedAmount = convertedAmount.toDouble()
                )
            }

            _uiState.update {
                it.copy(isLoading = false)
            }

        }
    }


    private fun convertForOne(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                pairCurrencyRepository.convert(currencyFrom, currencyTo, 1.0)
            _convertingCurrencyState.update {
                if (result.from == it.firstCurrency.shortCode) {
                    it.copy(
                        rateFromFirstToSecond = result.value,
                    )
                } else {
                    it.copy(
                        rateFromSecondToFirst = result.value,
                    )
                }
            }
        }
    }

    private fun parseAmount(input: String): Double? {
        return try {
            var sanitizedInput = if (input.contains(",")) input.replace(",", ".") else input
            Log.d("CurrencyConverterScreen", "sanitizedInput: $sanitizedInput")
            sanitizedInput = sanitizedInput.replace("..", ".")
            Log.d("CurrencyConverterScreen", "sanitizedInput after regex: $sanitizedInput")

            sanitizedInput.toDoubleOrNull()
        } catch (e: NumberFormatException) {
            null // Возвращаем null в случае ошибки
        }
    }

    private fun updateRateForOne() {
        convertForOne(
            _convertingCurrencyState.value.firstCurrency,
            _convertingCurrencyState.value.secondCurrency,
        )
        convertForOne(
            _convertingCurrencyState.value.secondCurrency,
            _convertingCurrencyState.value.firstCurrency,

            )
    }

    fun updatePairCurrencyFrom(fromCurrency: FiatCurrency) {
        _convertingCurrencyState.update {
            it.copy(firstCurrency = fromCurrency)
        }

        updateRateForOne()
        convert()
    }

    fun updatePairCurrencyTo(toCurrency: FiatCurrency) {
        _convertingCurrencyState.update {
            it.copy(secondCurrency = toCurrency)
        }

        updateRateForOne()
        convert()
    }

}