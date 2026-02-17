package com.example.project1.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Stores named decks per user.
 * Allows decks to exist even when they have 0 cards.
 */
@Entity(
    tableName = "decks",
    indices = [
        Index(value = ["username", "name"], unique = true)
    ]
)
data class DeckEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val name: String
)
