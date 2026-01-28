package com.example.project1.database

import androidx.room.*

/**
 * @author Estrella Ortiz
 * <br>COURSE: CST- 438
 * <br>DATE: 01/28/2026
 * <br>ASSIGNMENT: Project 01
 */
@Dao
interface UserDAO {

    // Creates a new user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    //Checks users username for login
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    //gets the users ID
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): UserEntity?

    /* added extra queries for testing
     */

    // Get all users
    @Query("SELECT * FROM users ORDER BY username")
    suspend fun getAllUsers(): List<UserEntity>


    // Delete all users
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    // Count users
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

}
