package com.example.project1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project1.database.UserDAO
import com.example.project1.database.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    val password: String? = null,
    val message: String =""

)

class SignUpViewModel(
    private val userDao: UserDAO
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState : StateFlow<SignUpUiState> = _uiState.asStateFlow()


    // Creates a new account by adding username and password into database
    fun create(username: String, password: String){
        viewModelScope.launch {
            //Get user by username
            val user = userDao.getUserByUsername(username)

            // Checks if user exists in the database
            if(user != null){
                _uiState.update { currentState ->
                    currentState.copy()
                }
            } else {
                //Creates a new user
                val newUser = UserEntity(
                    username = username,
                    password = password
                )

                // Inserts user into the database
                userDao.insert(newUser)

                // Message
                _uiState.update { currentState -> currentState.copy(
                    message = "Account Created"
                ) }
            }
        }
    }

}
