package com.example.project1.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Estrella Ortiz
 * <br>COURSE: CST- 438
 * <br>DATE: 01/28/2026
 * <br>ASSIGNMENT: Project 01
 */

//This is like a user account stored in the database
//For sign up and the log in.
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String,
    var password: String
)
