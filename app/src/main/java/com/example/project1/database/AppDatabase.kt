package com.example.project1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author Estrella Ortiz-Felix
 * App database
 * Stores users and their saved card collection.
 */
@Database(
    entities = [UserEntity::class, UserCardEntity::class, DeckEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    //  DAO
    abstract fun userDao(): UserDAO

    // DAO for user collection
    abstract fun userCardDao(): UserCardDao

    // Dao for decks
    abstract fun deckDao(): DeckDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}
