package com.example.project1.repository

import com.example.project1.database.*

/**
 * Author:Estrella Ortiz
 * Repository for the user profile
 */
class ProfileRepository(
    private val userDao: UserDAO,
    private val userCardDao: UserCardDao,
    private val deckDao: DeckDao
) {

    //gets the user,the cards
    suspend fun getUser(username: String) =
        userDao.getUserByUsername(username)

    suspend fun getUserCards(username: String) =
        userCardDao.getCollection(username)

    //gets wishlist
    suspend fun getWishlist(username: String) =
        userCardDao.getWishlist(username)

    suspend fun getDeck(username: String, deckName: String) =
        userCardDao.getDeck(username, deckName)

    suspend fun removeUserCard(card: UserCardEntity) {
        userCardDao.removeCard(
            username = card.username,
            cardId = card.cardId,
            listType = card.listType,
            deckName = card.deckName
        )
    }

    // Deck names come from decks table + any existing card rows that have a deckName
    suspend fun getDeckNames(username: String): List<String> {
        val explicit = deckDao.getDeckNames(username)
        val implicit = userCardDao.getDeckNamesFromCards(username)
        return (explicit + implicit).map { it.trim() }.filter { it.isNotEmpty() }.distinct().sorted()
    }

    suspend fun createDeck(username: String, deckName: String) {
        deckDao.insert(DeckEntity(username = username, name = deckName.trim()))
    }

    suspend fun deleteDeck(username: String, deckName: String) {
        // First remove cards in the deck, then delete deck row
        userCardDao.removeCardsInDeck(username, deckName)
        deckDao.deleteDeck(username, deckName)
    }
}
