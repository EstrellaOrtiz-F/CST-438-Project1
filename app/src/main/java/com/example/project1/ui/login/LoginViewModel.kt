package com.example.project1.ui.login


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import kotlinx.coroutines.launch


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}


class LoginViewModel(
    private val userDao: UserDAO
) : ViewModel() {


    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set


    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            loginState = LoginState.Error("Username and password are required")
            return
        }


        loginState = LoginState.Loading


        viewModelScope.launch {
            try {
                val user = userDao.getUserByUsername(username)


                if (user == null) {
                    loginState = LoginState.Error("User not found")
                    return@launch
                }


// No hashing: plaintext compare (as requested)
                if (user.password != password) {
                    loginState = LoginState.Error("Incorrect password")
                    return@launch
                }


                loginState = LoginState.Success
            } catch (e: Exception) {
                loginState = LoginState.Error("Login failed: ${e.message ?: "unknown error"}")
            }
        }
    }


    fun reset() {
        loginState = LoginState.Idle
    }
}