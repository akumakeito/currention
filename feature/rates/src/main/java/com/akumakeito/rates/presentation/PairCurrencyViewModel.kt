package com.akumakeito.rates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.ErrorType
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.rates.domain.PairCurrency
import com.akumakeito.rates.domain.PairCurrencyRepository
import com.akumakeito.rates.domain.newPair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PairCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository,
    private val currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<PairsScreenModel>(
        PairsScreenModel(
            pairs = emptyList(),
            favoriteCurrencies = emptyList(),
            editingPair = newPair,
            isEditing = false
        )
    )
    val state = _state.asStateFlow()

    init {

        combine(
            pairCurrencyRepository.currencyPairs,
            currencyRepository.getFavoriteCurrencyListFlow(),
        ) { currencyPairs, favoriteCurrencyList ->
            _state.update {
                it.copy(
                    pairs = currencyPairs,
                    favoriteCurrencies = favoriteCurrencyList
                )
            }
        }.launchIn(viewModelScope)

        try {
            updateAllPairsRates()
        } catch (networkException: IOException) {
            _state.update {
                it.copy(isError = ErrorType.NETWORK)
            }
        }
    }

    fun addNewCurrencyPair() = viewModelScope.launch {
        val newAddedPair = pairCurrencyRepository.addNewCurrencyPair(newPair)
        editPair(newAddedPair)
    }

    private fun editPair(pairCurrency: PairCurrency) {
        _state.update {
            it.copy(
                editingPair = pairCurrency,
                isEditing = true
            )
        }
    }

    fun updatePair() = viewModelScope.launch {
        pairCurrencyRepository.updateCurrencyPair(_state.value.editingPair)
        _state.update {
            it.copy(
                editingPair = newPair,
                isEditing = false
            )
        }
    }

    private fun updatePairRates(pairCurrency: PairCurrency) =
        viewModelScope.launch {
            pairCurrencyRepository.updateCurrencyPair(pairCurrency)
        }

    fun updateAllPairsRates() = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
            it.pairs.forEach { pair ->
                updatePairRates(pair)
            }

            it.copy(isLoading = false)
        }
    }

    fun updatePairCurrencyFrom(fromCurrency: FiatCurrency) {
        _state.update {
            it.copy(editingPair = it.editingPair.copy(fromCurrency = fromCurrency))
        }
    }

    fun updatePairCurrencyTo(toCurrency: FiatCurrency) {
        _state.update {
            it.copy(editingPair = it.editingPair.copy(toCurrency = toCurrency))
        }
    }

    private fun deletePairById(id: Int) = viewModelScope.launch {
        pairCurrencyRepository.deletePairById(id)
        _state.update {
            it.copy(isEditing = false)
        }
    }


    fun onSwipeToDelete(pairCurrency: PairCurrency) {
        deletePairById(pairCurrency.id)
    }

    fun onSwipeToEdit(pairCurrency: PairCurrency) {
        editPair(pairCurrency)
    }
}