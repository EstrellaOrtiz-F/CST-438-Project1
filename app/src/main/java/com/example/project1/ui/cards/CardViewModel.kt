package com.example.project1.ui.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.network.CardDto
import com.example.project1.repository.CardRepository
import kotlinx.coroutines.launch

class CardViewModel(
    private val repo: CardRepository = CardRepository()
) : ViewModel() {

    var cards by mutableStateOf<List<CardDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private var offset = 0
    private val pageSize = 20

    fun loadFirstPage() {
        offset = 0
        cards = emptyList()
        loadMore()
    }

    fun loadMore() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val next = repo.getCardsPage(num = pageSize, offset = offset)
                cards = cards + next
                offset += pageSize
            } finally {
                isLoading = false
            }
        }
    }

    fun search(query: String) {
        if (isLoading) return
        isLoading = true
        offset = 0

        viewModelScope.launch {
            try {
                cards = repo.searchByFuzzyName(query, num = pageSize, offset = offset)
                offset += pageSize
            } finally {
                isLoading = false
            }
        }
    }
}
