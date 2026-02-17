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

    suspend fun getDeckNames(username: String): List<String> {
        // combine decks table + any deck names from user_card entries to be robust
        val explicit = deckDao.getDeckNames(username)
        val implicit = userCardDao.getDeckNamesFromCards(username) // add this DAO function if you don't have it
        return (explicit + implicit).distinct()
    }

    suspend fun createDeck(username: String, deckName: String) {
        deckDao.insert(DeckEntity(username = username, name = deckName))
    }

    suspend fun deleteDeck(username: String, deckName: String) {
        // remove deck record then remove any card associations (optional)
        deckDao.deleteDeck(username, deckName)
        userCardDao.removeCardsInDeck(username, deckName) // optional: add this DAO query if you want deleting a deck to remove its cards
    }
}
