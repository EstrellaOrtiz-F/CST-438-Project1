package com.example.project1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import com.example.project1.database.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

            // Holds value that checks if user or password is empty
            val userIsEmpty = username.replace("\\s".toRegex(), "").isEmpty()
            val passIsEmpty = password.replace("\\s".toRegex(), "").isEmpty()

            // Messages for username and password scenarios
            if (userIsEmpty && passIsEmpty) {
                _uiState.update {
                    it.copy(message = "Username & Password cannot be empty")
                }
            } else if(userIsEmpty && !passIsEmpty){
                _uiState.update {
                    it.copy(message = "Username cannot be empty")
                }
            } else if(!userIsEmpty && passIsEmpty){
                _uiState.update {
                    it.copy(message = "Password cannot be empty")
                }
            }

            // Checks if user exists in the database
            if (user != null) {
                _uiState.update {
                    it.copy(message = "Username already exists")
                }
            } else if (!userIsEmpty && !passIsEmpty) {
                //Creates a new user if as password & user is not empty
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
