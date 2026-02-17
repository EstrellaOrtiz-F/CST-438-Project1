package com.example.project1.ui.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.*
import com.example.project1.repository.ProfileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val username: String,
    private val repo: ProfileRepository
) : ViewModel() {

    var user by mutableStateOf<UserEntity?>(null)
        private set

    var collection by mutableStateOf<List<UserCardEntity>>(emptyList())
        private set

    var wishlist by mutableStateOf<List<UserCardEntity>>(emptyList())
        private set

    var decks by mutableStateOf<Map<String, List<UserCardEntity>>>(emptyMap())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private var didLoad = false

    fun load() {
        if (isLoading || didLoad) return
        isLoading = true

        viewModelScope.launch {
            try {
                val userJob = async { repo.getUser(username) }
                val collectionJob = async { repo.getUserCards(username) }
                val wishlistJob = async { repo.getWishlist(username) }
                val deckNamesJob = async { repo.getDeckNames(username) }

                user = userJob.await()
                collection = collectionJob.await().distinctBy { it.cardId }
                wishlist = wishlistJob.await()

                val deckMap = mutableMapOf<String, List<UserCardEntity>>()
                for (name in deckNamesJob.await()) {
                    deckMap[name] = repo.getDeck(username, name)
                }
                decks = deckMap

                didLoad = true
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Removes a card from the database, then refreshes the profile lists.
     */
    fun removeCard(card: UserCardEntity) {
        viewModelScope.launch {
            repo.removeUserCard(card)
            refresh()
        }
    }

    /**
     * Creates a new deck in the database.
     */
    fun createDeck(name: String) {
        viewModelScope.launch {
            repo.createDeck(username, name)
            refresh()
        }
    }

    /**
     * Deletes a deck from the database.
     */
    fun deleteDeck(name: String) {
        viewModelScope.launch {
            repo.deleteDeck(username, name)
            refresh()
        }
    }



    // Added so Profile updates immediately after DB changes
    fun refresh() {
        didLoad = false
        load()
    }
}
