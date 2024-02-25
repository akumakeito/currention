package ru.akumakeito.currention.model

import ru.akumakeito.currention.domain.FiatCurrency

sealed class StateModel {
    object Loading : StateModel()
    object Error : StateModel()
    data class Content(val currencies : List<FiatCurrency>) : StateModel()
}