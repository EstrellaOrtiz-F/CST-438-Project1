package com.example.project1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao interface UserCardDao {
//Allows for access the users card collection via DAO
    //gets cards from a specific user
    @Query("SELECT * FROM user_cards WHERE username = :username")
    suspend fun getCardsForUser(username: String): List<UserCardEntity>
//adds cards to the users collection
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: UserCardEntity)

}