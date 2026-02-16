package com.example.project1.repository

import com.example.project1.database.*

/**
 * Author:Estrella Ortiz
 * Repository for the user profile
 */
class ProfileRepository(
    private val userDao: UserDAO,
    private val userCardDao: UserCardDao
) {

    //gets the user,the cards
    suspend fun getUser(username: String) =
        userDao.getUserByUsername(username)

    suspend fun getUserCards(username: String) =
        userCardDao.getCollection(username)

    //gets wishlist
    suspend fun getWishlist(username: String) =
        userCardDao.getWishlist(username)

    suspend fun getDeckNames(username: String) =
        userCardDao.getDeckNames(username)

    suspend fun getDeck(username: String, deckName: String) =
        userCardDao.getDeck(username, deckName)
}
