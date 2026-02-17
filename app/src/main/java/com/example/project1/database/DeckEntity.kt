package com.example.project1.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a named deck for a user. We store a row per deck (one row per user+deck name),
 * allowing empty decks to exist independently of the cards table. Hopefully I don't need to change this later T-T
 */
@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val name: String
)
