package com.example.project1

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.project1.ui.cards.CardListScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.project1.database.AppDatabase
import com.example.project1.database.UserEntity
import com.example.project1.ui.login.LoginScreen
import com.example.project1.ui.login.LoginState
import com.example.project1.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = AppDatabase.getDatabase(applicationContext).userDao()

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    return LoginViewModel(userDao) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        val loginViewModel: LoginViewModel by viewModels { factory }

        setContent {
            val state = loginViewModel.loginState

            // Controls which screen is visible
            var isLoggedIn by remember { mutableStateOf(false) }

            // React to login state changes
            LaunchedEffect(state) {
                when (state) {
                    is LoginState.Success -> {
                        Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        isLoggedIn = true
                        loginViewModel.reset()
                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        loginViewModel.reset()
                    }
                    else -> Unit
                }
            }
            // Show either Login or Card List
            if (!isLoggedIn) {
                LoginScreen { username, password ->
                    loginViewModel.login(username, password)
                }
            } else {
                CardListScreen()
            }
        }
    }
}
