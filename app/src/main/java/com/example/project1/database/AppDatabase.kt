package com.example.project1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * App database
 * Stores users and their saved card collection.
 */
@Database(
    entities = [UserEntity::class, UserCardEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    // Existing DAO
    abstract fun userDao(): UserDAO

    // New DAO for user collection
    abstract fun userCardDao(): UserCardDao

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
