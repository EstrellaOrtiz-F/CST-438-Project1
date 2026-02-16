package com.example.project1

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project1.database.AppDatabase
import com.example.project1.database.UserCardDao
import com.example.project1.database.UserCardEntity
import com.example.project1.database.UserDAO
import com.example.project1.database.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Author: Estrella Ortiz
 * Date: 2/16/26
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDAO
    private lateinit var cardDao: UserCardDao

    @Before
    fun setup() {
        // in-memory database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        userDao = db.userDao()
        cardDao = db.userCardDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun userTest() = runTest {
        // insert user
        val user = UserEntity(username = "test", password = "1234")
        userDao.insert(user)

        val result = userDao.getUserByUsername("test")

        assertNotNull(result)
        assertEquals("test", result?.username)
    }

    @Test
    fun wishTest() = runTest {
        // add wishlist
        cardDao.addCard(
            UserCardEntity(
                username = "test",
                cardId = 1L,
                cardName = "A",
                imageUrl = null,
                listType = "WISHLIST",
                deckName = null
            )
        )

        val list = cardDao.getWishlist("test")

        assertEquals(1, list.size)
        assertEquals(1L, list.first().cardId)
    }

    @Test
    fun collectTest() = runTest {
        // add collection and deck
        cardDao.addCard(
            UserCardEntity(
                username = "test",
                cardId = 10L,
                cardName = "Deck",
                imageUrl = null,
                listType = "DECK",
                deckName = "Main"
            )
        )

        cardDao.addCard(
            UserCardEntity(
                username = "test",
                cardId = 11L,
                cardName = "Coll",
                imageUrl = null,
                listType = "COLLECTION",
                deckName = null
            )
        )

        val list = cardDao.getCollection("test")

        assertEquals(2, list.size)
    }

    @Test
    fun usersTest() = runTest {
        // two users
        cardDao.addCard(
            UserCardEntity(
                username = "A",
                cardId = 1L,
                cardName = "A",
                imageUrl = null,
                listType = "WISHLIST",
                deckName = null
            )
        )

        cardDao.addCard(
            UserCardEntity(
                username = "B",
                cardId = 2L,
                cardName = "B",
                imageUrl = null,
                listType = "WISHLIST",
                deckName = null
            )
        )

        val listA = cardDao.getWishlist("A")
        val listB = cardDao.getWishlist("B")

        assertEquals(1, listA.size)
        assertEquals(1, listB.size)
        assertNotEquals(listA.first().cardId, listB.first().cardId)
    }




}