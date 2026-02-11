package com.example.project1.ui.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.network.CardDto
import com.example.project1.repository.CardRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the CardListScreen.
 * Loads cards from the YGOProDeck API using paging.
 */
class CardViewModel(
    private val repo: CardRepository = CardRepository()
) : ViewModel() {

    var cards by mutableStateOf<List<CardDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Holds an error message if the network call fails
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var offset = 0
    private val pageSize = 20

    fun loadFirstPage() {
        offset = 0
        cards = emptyList()
        errorMessage = null
        loadMore()
    }

    fun loadMore() {
        if (isLoading) return
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val next = repo.getCardsPage(num = pageSize, offset = offset)
                cards = cards + next
                offset += pageSize
            } catch (e: Exception) {
                // Prevent crash: capture the error so UI can display it
                errorMessage = "Failed to load cards: ${e.message ?: "unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }

    fun search(query: String) {
        if (isLoading) return
        isLoading = true
        errorMessage = null
        offset = 0

        viewModelScope.launch {
            try {
                // Using repository search function (fuzzy search)
                cards = repo.searchCards(name = query, num = pageSize, offset = offset)
                offset += pageSize
            } catch (e: Exception) {
                errorMessage = "Search failed: ${e.message ?: "unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }
}
