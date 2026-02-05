package com.example.project1.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project1.database.AppDatabase
import com.example.project1.repository.ProfileRepository

class ProfileVMProvider(
    private val username: String, // profile view instance
    private val db: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ProfileViewModel::class.java) {
            val repo = ProfileRepository(db.userDao(), db.userCardDao())
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(username, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
