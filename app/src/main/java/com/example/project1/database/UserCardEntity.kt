package com.example.project1.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cards")
data class UserCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val cardId: Long,
    val cardName: String,
    val imageUrl: String?,
    val listType: String,   //Works for the collection and wishlist
    val deckName: String? = null
)