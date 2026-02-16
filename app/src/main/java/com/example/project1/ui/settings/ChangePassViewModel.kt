package com.example.project1.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.SignUpUiState
import com.example.project1.database.UserDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChangePassUiState(
    val message: String = ""
)

class ChangePassViewModel(
    private val userDao: UserDAO
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePassUiState())
    val uiState: StateFlow<ChangePassUiState> = _uiState.asStateFlow()

    fun changePassword(username: String, currentPassword : String, newPassword: String) {
        viewModelScope.launch {

            val passIsEmpty = newPassword.replace("\\s".toRegex(), "").isEmpty()

            // Look up user
            val user = userDao.getUserByUsername(username)
            if (user == null) {
                _uiState.update {
                    it.copy(message = "Incorrect Username")
                }
                return@launch
            }

            if(user.password == currentPassword && !passIsEmpty){
                // Update password (copy if data class)
                val updatedUser = user.copy(password = newPassword)
                userDao.updateUser(updatedUser)
                _uiState.update {
                    it.copy(message = "Password Changed")
                }
            } else {
                _uiState.update {
                    it.copy(message = "Current Password incorrect")
                }
            }
        }
    }
}