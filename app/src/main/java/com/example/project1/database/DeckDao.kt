package com.example.project1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeckDao {

    @Query("SELECT name FROM decks WHERE username = :username ORDER BY name ASC")
    suspend fun getDeckNames(username: String): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(deck: DeckEntity)

    @Query("DELETE FROM decks WHERE username = :username AND name = :name")
    suspend fun deleteDeck(username: String, name: String)
}

