package ru.skillbranch.sbdelivery.ui.search

import ru.skillbranch.sbdelivery.core.adapter.ProductItemState

sealed class SearchState {
    object Loading: SearchState()
    data class Result(val items: List<ProductItemState>): SearchState()
    data class Error(val errorDescription:String): SearchState()
}