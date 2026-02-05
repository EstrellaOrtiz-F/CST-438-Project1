package com.example.project1.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserCardEntity
import com.example.project1.database.UserEntity
import com.example.project1.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val username: String,
    private val repo: ProfileRepository
) : ViewModel() {

    var user by mutableStateOf<UserEntity?>(null)
        private set

    var cards by mutableStateOf<List<UserCardEntity>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun load() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                user = repo.getUser(username)
                cards = repo.getUserCards(username)
            } finally {
                isLoading = false
            }
        }
    }
}
