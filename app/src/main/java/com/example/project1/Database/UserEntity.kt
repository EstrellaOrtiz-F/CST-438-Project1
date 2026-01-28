package com.example.project1.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


//This is like a user account stored in the database
//For sign up and the log in.
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String,
    var password: String
)
