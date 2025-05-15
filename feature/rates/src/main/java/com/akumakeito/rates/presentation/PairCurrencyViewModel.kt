package com.akumakeito.rates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.ErrorType
import com.akumakeito.commonui.presentation.StateModel
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.rates.domain.PairCurrency
import com.akumakeito.rates.domain.PairCurrencyRepository
import com.akumakeito.rates.domain.newPair
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel
class PairCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository,
    private val currencyRepository: CurrencyRepository,
    private val convertRepository: ConvertRepository,
) : ViewModel() {

    private val _currencyPairs = pairCurrencyRepository.currencyPairs
    val currencyPairs = _currencyPairs


    private val _editPairCurrency = MutableStateFlow(newPair)
    val editPairCurrency = _editPairCurrency.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    private val _uiState = MutableStateFlow(StateModel())
    val uiState = _uiState.asStateFlow()


    private val _fiatCurrencies = currencyRepository.fiatCurrencies

    val fiatCurrencies = _fiatCurrencies.map { currencies ->
        currencies.filter { it.isPopular }  //TODO заменить на is Favorite
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )

    init {
        viewModelScope.launch {
            currencyRepository.getInitialFiatCurrencyList()
        }

        try {
            _uiState.update {
                it.copy(isLoading = true)
            }
            updateAllPairsRates()

            _uiState.update {
                StateModel()
            }
        } catch (networkException: IOException) {
            _uiState.update {
                it.copy(isError = ErrorType.NETWORK)
            }
        }


    }

    fun addNewCurrencyPair() = viewModelScope.launch{
        val newAddedPair = pairCurrencyRepository.addNewCurrencyPair(newPair)
        editPair(newAddedPair)
    }

    private fun editPair(pairCurrency: PairCurrency) {
        _editPairCurrency.update { pairCurrency }
        _isEditing.update { true }
    }


    fun updatePair() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        pairCurrencyRepository.updateCurrencyPair(_editPairCurrency.value)
        _editPairCurrency.update { newPair }
        _isEditing.update { false }

        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    private fun updatePairRates(pairCurrency: PairCurrency) =
        viewModelScope.launch {

            pairCurrencyRepository.updateCurrencyPair(pairCurrency)

        }

    fun updateAllPairsRates() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        _currencyPairs.value.forEach {
            updatePairRates(it)
        }
        _uiState.update {
            it.copy(isLoading = false)
        }
    }


    fun updatePairCurrencyFrom(fromCurrency: FiatCurrency) {
        _editPairCurrency.update {
            it.copy(fromCurrency = fromCurrency)
        }
    }

    fun updatePairCurrencyTo(toCurrency: FiatCurrency) {
        _editPairCurrency.update {
            it.copy(toCurrency = toCurrency)
        }
    }


    fun getPairById(id: Int) = viewModelScope.launch {
        pairCurrencyRepository.getPairById(id)
    }

    private fun deletePairById(id: Int) = viewModelScope.launch {
        pairCurrencyRepository.deletePairById(id)
        _isEditing.update { false }
    }


    fun onSwipeToDelete(pairCurrency: PairCurrency) {
        deletePairById(pairCurrency.id)
    }

    fun onSwipeToEdit(pairCurrency: PairCurrency) {
        editPair(pairCurrency)
    }
}