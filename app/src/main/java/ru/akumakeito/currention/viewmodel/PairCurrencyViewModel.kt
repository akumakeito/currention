package ru.akumakeito.currention.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.model.ErrorType
import ru.akumakeito.currention.model.SearchState
import ru.akumakeito.currention.model.StateModel
import ru.akumakeito.currention.repository.CurrencyRepository
import ru.akumakeito.currention.repository.PairCurrencyRepository
import java.io.IOException
import javax.inject.Inject

private val newPair =
    PairCurrency(
        id = 0,
        fromCurrency = usd,
        toCurrency = rub,
        toCurrencyLastRate = 0.0,
        toCurrencyNewRate = 0.0,
        rateCurrency = 0.0f
    )

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

    private val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState.asStateFlow()

    private val _fiatCurrencies = currencyRepository.fiatCurrencies
    @OptIn(ExperimentalCoroutinesApi::class)
    val fiatCurrencies = _searchingState.flatMapLatest { state ->
        _fiatCurrencies.map { currencies ->
            if (state.searchText.isBlank()) {
                currencies.filter { it.isPopular }  //TODO заменить на is Favorite
            } else {
               currencies.filter { it.doesMatchSearchQuery(state.searchText) }

            }
        }

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

    fun onSearchTextChange(text: String) {
        _searchingState.update {
            it.copy(searchText = text)
        }
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

    fun updatePairRates(pairCurrency: PairCurrency) = viewModelScope.launch(Dispatchers.IO) {

        pairCurrencyRepository.updateCurrencyPair(pairCurrency)
        _uiState.update {
            it.copy(isLoading = false)
        }
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



    fun getPairById(id : Int) = viewModelScope.launch {
        pairCurrencyRepository.getPairById(id)
    }

    private fun deletePairById(id : Int) = viewModelScope.launch {
        pairCurrencyRepository.deletePairById(id)
        _isEditing.update { false }
    }

    fun getPairRates(
        currencyFromShortCode: FiatCurrency,
        currencyToShortCode: FiatCurrency,
        amount: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            pairCurrencyRepository.getPairRates(currencyFromShortCode, currencyToShortCode, amount)
        }
    }

    fun onSwipeToDelete(pairCurrency: PairCurrency) {
        deletePairById(pairCurrency.id)
    }

    fun onSwipeToEdit(pairCurrency: PairCurrency) {
        editPair(pairCurrency)
    }
}