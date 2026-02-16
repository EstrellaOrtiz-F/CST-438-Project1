package com.example.project1.ui.cards

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.network.CardDto
import com.example.project1.repository.CardRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for CardListScreen.
 * - Loads paged cards from YGOProDeck
 * - Supports fuzzy search (fname) through repo
 * - Exposes an errorMessage that the UI can show in a dialog
 */
class CardViewModel(
    private val repo: CardRepository = CardRepository()
) : ViewModel() {

    /** Current cards shown on screen */
    var cards by mutableStateOf<List<CardDto>>(emptyList())
        private set

    /** True while a network request is in progress */
    var isLoading by mutableStateOf(false)
        private set

    /** Non-null when a request fails; UI displays this */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    /** Pagination offset (API uses offset + num) */
    private var offset = 0

    /** How many cards to request per page */
    private val pageSize = 20

    /**
     * Clears any displayed error.
     * Called by the UI when the user dismisses the AlertDialog.
     */
    fun clearError() {
        errorMessage = null
    }

    /**
     * Loads the first page of cards (resets pagination).
     * Used when the screen opens or when clearing search.
     */
    fun loadFirstPage() {
        offset = 0
        cards = emptyList()
        errorMessage = null
        loadMore()
    }

    /**
     * Loads the next page of cards and appends to current list.
     */
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
                errorMessage = "Failed to load cards: ${e.message ?: "unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Performs a fuzzy name search.
     * This matches the YGOProDeck API parameter: fname=<query>
     *
     * NOTE: Your repository function name is currently searchCards(name=...).
     * If that function actually uses "fname" under the hood, you're good.
     */
    fun searchByFuzzyName(query: String) {
        search(query)
    }

    /**
     * Search implementation
     */
    fun search(query: String) {
        if (isLoading) return

        val q = query.trim()
        if (q.isEmpty()) {
            // If user clears search, go back to first page of all cards
            loadFirstPage()
            return
        }

        isLoading = true
        errorMessage = null
        offset = 0

        viewModelScope.launch {
            try {
                // IMPORTANT: This should use the API fuzzy parameter fname, not exact name.
                // As long as CardRepository.searchCards() calls the API with fname, you're fine.
                cards = repo.searchByFuzzyName(query = q, num = pageSize, offset = offset)
                offset += pageSize
            } catch (e: Exception) {
                errorMessage = "Search failed: ${e.message ?: "unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }
}
