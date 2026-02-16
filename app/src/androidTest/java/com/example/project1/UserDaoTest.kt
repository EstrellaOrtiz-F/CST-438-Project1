package com.example.project1

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project1.database.AppDatabase
import com.example.project1.database.UserDAO
import com.example.project1.database.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Author: Estrella Ortiz
 * Date: 2/16/26
 */

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: UserDAO

    @Before
    fun setup() {
        // create in memory db
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTest() = runTest {
        dao.insert(UserEntity(username = "a", password = "1"))
        val u = dao.getUserByUsername("a")
        // inserts and retrieves
        assertNotNull(u)
        assertEquals("a", u?.username)
    }

    @Test
    fun getByIdTest() = runTest {
        dao.insert(UserEntity(id = 7, username = "b", password = "2"))
        val u = dao.getUserById(7)

        assertNotNull(u)
        assertEquals(7, u?.id)
    }

    @Test
    fun listTest() = runTest {
        dao.insert(UserEntity(username = "c", password = "3"))
        dao.insert(UserEntity(username = "a", password = "1"))
        dao.insert(UserEntity(username = "b", password = "2"))

        val list = dao.getAllUsers()

        assertEquals(3, list.size)
        assertEquals("a", list[0].username)
        assertEquals("b", list[1].username)
        assertEquals("c", list[2].username)
    }

    @Test
    fun countTest() = runTest {
        dao.insert(UserEntity(username = "a", password = "1"))
        dao.insert(UserEntity(username = "b", password = "2"))

        val count = dao.getUserCount()
        assertEquals(2, count)
    }

    @Test
    fun deleteTest() = runTest {// deletes all users
        dao.insert(UserEntity(username = "a", password = "1"))
        dao.insert(UserEntity(username = "b", password = "2"))

        dao.deleteAllUsers()

        val count = dao.getUserCount()
        assertEquals(0, count)
    }
}