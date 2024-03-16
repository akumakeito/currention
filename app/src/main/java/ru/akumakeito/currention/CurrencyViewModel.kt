package ru.akumakeito.currention

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.model.SearchState
import ru.akumakeito.currention.repository.CurrencyRepository
import javax.inject.Inject


val usd = FiatCurrency(
    1,
    "Доллар США",
    "USD",
    "840",
    "$",
    R.drawable.flag_usd
)

val rub = FiatCurrency(
    1,
    "Российский рубль",
    "RUB",
    "643",
    "P",
    R.drawable.flag_rub
)
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
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    init {
        getFiatCurrencies()
    }

    private val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState.asStateFlow()

    private val _fiatCurrencies = repository.fiatCurrencies

    @OptIn(ExperimentalCoroutinesApi::class)
    val fiatCurrencies = _searchingState.flatMapLatest { state ->
        _fiatCurrencies.map { currencies ->

            if (state.searchText.isBlank()) {
                currencies.sortedByDescending { it.isPopular }.sortedByDescending { it.isFavorite }

            } else {
                currencies.filter { it.doesMatchSearchQuery(state.searchText) }
            }
        }
    }


    private val _currencyPairs = repository.currencyPairs
    val currencyPairs = _currencyPairs

    private val _editPairCurrency = MutableStateFlow(newPair)
    val editPairCurrency = _editPairCurrency.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    fun addNewCurrencyPair() = viewModelScope.launch(Dispatchers.IO) {
        val newAddedPair = repository.addNewCurrencyPair(newPair)
        Log.d("editingPair", "new pair $newAddedPair")
        _editPairCurrency.update { newAddedPair }
        Log.d("editingPair", "edit pair ${_editPairCurrency.value}")
    }


    fun getFiatCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFiatCurrencyList()
    }

    fun onSearchTextChange(text: String) {
        _searchingState.update {
            it.copy(searchText = text)
        }
    }

    fun updatePair() = viewModelScope.launch(Dispatchers.IO) {

    }

    fun editPair(pairCurrency: PairCurrency) {
        _editPairCurrency.update { pairCurrency }
        _isEditing.update { true }
    }

    fun getPairById(id : Int) = viewModelScope.launch {
        repository.getPairById(id)
    }

    fun getPairRates(
        currencyFromShortCode: FiatCurrency,
        currencyToShortCode: FiatCurrency,
        amount: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPairRates(currencyFromShortCode, currencyToShortCode, amount)
        }
    }

    fun updateFavoriteCurrency(currency: FiatCurrency) {
        Log.d("checkbox", "vm $currency")
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCurrency(currency)
        }
    }


}
