package com.example.project1

import android.R.id.message
import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import com.example.project1.database.UserEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TITLE: SignUpViewModel.kt
 * @author Jesus Alfaro-Suarez
 * COURSE: CST- 438
 * DATE: 02/02/2026
 * ASSIGNMENT: Project 01
 * PURPOSE: Adds functionality to SignUpScreen,
 * creates a new user in database using the inputs credentials.
 */

data class SignUpUiState(
    val password: String? = null,
    val message: String = ""
)

class SignUpViewModel(
    private val userDao: UserDAO
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()


    // Creates a new account by adding username and password into database
    fun create(username: String, password: String) {
        viewModelScope.launch {
            //Get user by username
            val user = userDao.getUserByUsername(username)

            if (username.isEmpty()) {
                _uiState.update {
                    it.copy(message = "Username cannot be empty")
                }
            }

            // Checks if user exists in the database
            if (user != null) {
                _uiState.update { currentState ->
                    currentState.copy()
                }
            } else if (!username.replace("\\s".toRegex(), "").isEmpty()
                && !password.replace("\\s".toRegex(), "").isEmpty()) {
                //Creates a new user
                val newUser = UserEntity(
                    username = username,
                    password = password
                )

                // Inserts user into the database
                userDao.insert(newUser)

                // Message
                _uiState.update { currentState ->
                    currentState.copy(
                        message = "Account Created"

                    )
                }
            }
        }
    }


}
