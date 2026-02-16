package com.example.project1.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import kotlinx.coroutines.launch

class ChangeUserViewModel(
    private val userDao: UserDAO
) : ViewModel() {

    fun changeUsername(username: String, newUsername : String, password: String) {
        viewModelScope.launch {

            // 2) Look up user
            val user = userDao.getUserByUsername(username) // suspend fun in DAO
            if (user == null) {
                return@launch
            }

            if(user.password == password){
                // 3) Update password (copy if data class)
                val updatedUser = user.copy(username = newUsername)
                userDao.updateUser(updatedUser)
            }
        }
    }
}