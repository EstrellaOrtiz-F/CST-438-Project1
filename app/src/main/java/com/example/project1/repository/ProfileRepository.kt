package com.example.project1.repository

import com.example.project1.database.UserCardDao
import com.example.project1.database.UserDAO

class ProfileRepository (
    private val userDao: UserDAO, private val userCardDao: UserCardDao
)
{
    //loads the users account info
suspend fun getUser(username: String) =
    userDao.getUserByUsername(username)
//lods users saved card collection
suspend fun getUserCards(username: String) =
    userCardDao.getCardsForUser(username)

}