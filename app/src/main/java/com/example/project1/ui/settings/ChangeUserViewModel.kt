package com.example.project1.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChangeUserUiState(
    val password: String? = null,
    val message: String = ""
)

class ChangeUserViewModel(
    private val userDao: UserDAO
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePassUiState())
    val uiState: StateFlow<ChangePassUiState> = _uiState.asStateFlow()




    fun changeUsername(username: String, newUsername : String, password: String) {
        viewModelScope.launch {

            val userIsEmpty = newUsername.replace("\\s".toRegex(), "").isEmpty()

            // Look up user
            val user = userDao.getUserByUsername(username)
            if (user == null) {
                _uiState.update {
                    it.copy(message = "Incorrect User")
                }
                return@launch
            }

            if(user.password == password && !userIsEmpty){
                // Update password (copy if data class)
                val updatedUser = user.copy(username = newUsername)
                userDao.updateUser(updatedUser)
                _uiState.update {
                    it.copy(message = "Username Changed")
                }
            } else {
                _uiState.update {
                    it.copy(message = "Password Incorrect")
                }
            }
        }
    }
}