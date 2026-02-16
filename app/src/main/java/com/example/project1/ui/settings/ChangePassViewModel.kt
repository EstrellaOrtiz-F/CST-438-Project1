package com.example.project1.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import kotlinx.coroutines.launch

class ChangePassViewModel(
    private val userDao: UserDAO
) : ViewModel() {

    fun changePassword(username: String, currentPassword : String, newPassword: String) {
        viewModelScope.launch {

            // 2) Look up user
            val user = userDao.getUserByUsername(username) // suspend fun in DAO
            if (user == null) {
                return@launch
            }

            if(user.password == currentPassword){
                // 3) Update password (copy if data class)
                val updatedUser = user.copy(password = newPassword)
                userDao.updateUser(updatedUser)
            }
        }
    }
}