package ru.akumakeito.currention.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.model.PairCurrency
import ru.akumakeito.currention.domain.repository.CurrencyRepository
import ru.akumakeito.currention.domain.repository.PairCurrencyRepository
import ru.akumakeito.currention.domain.state.ErrorType
import ru.akumakeito.currention.domain.state.StateModel
import ru.akumakeito.currention.presentation.util.Constants.Companion.newPair
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PairCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository,
    private val currencyRepository: CurrencyRepository
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


    fun addNewCurrencyPair() = viewModelScope.launch(Dispatchers.IO) {
        val newAddedPair = pairCurrencyRepository.addNewCurrencyPair(newPair)
        editPair(newAddedPair)
    }

    private fun editPair(pairCurrency: PairCurrency) {
        _editPairCurrency.update { pairCurrency }
        _isEditing.update { true }
    }


    fun updatePair() = viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {

            pairCurrencyRepository.updateCurrencyPair(pairCurrency)

        }

    fun updateAllPairsRates() = viewModelScope.launch(Dispatchers.IO) {
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