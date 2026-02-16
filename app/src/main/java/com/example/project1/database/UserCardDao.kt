package com.example.project1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/**
@author Estrella Ortiz-Felix
UserCardDao

 */
@Dao
interface UserCardDao {

    @Query("SELECT * FROM user_cards WHERE username = :username")
    suspend fun getCardsForUser(username: String): List<UserCardEntity>

    @Query("SELECT * FROM user_cards WHERE username = :username AND listType = 'WISHLIST'")
    suspend fun getWishlist(username: String): List<UserCardEntity>

    // Collection
    @Query("SELECT * FROM user_cards WHERE username = :username AND (listType = 'COLLECTION' OR listType = 'DECK')")
    suspend fun getCollection(username: String): List<UserCardEntity>

    @Query("SELECT * FROM user_cards WHERE username = :username AND listType = 'DECK' AND deckName = :deckName")
    suspend fun getDeck(username: String, deckName: String): List<UserCardEntity>

    @Query("SELECT DISTINCT deckName FROM user_cards WHERE username = :username AND listType = 'DECK'")
    suspend fun getDeckNames(username: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: UserCardEntity)

    @Query("DELETE FROM user_cards WHERE username = :username AND cardId = :cardId AND listType = :listType")
    suspend fun removeCard(username: String, cardId: Long, listType: String)
}