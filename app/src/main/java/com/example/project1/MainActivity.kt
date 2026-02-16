package com.example.project1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project1.database.AppDatabase
import com.example.project1.database.UserCardEntity
import com.example.project1.ui.cards.CardListScreen
import com.example.project1.ui.cards.CardDetailScreen
import com.example.project1.ui.cards.UserCardDetailScreen
import com.example.project1.network.CardDto
import com.example.project1.ui.landing.LandingScreen
import com.example.project1.ui.login.LoginScreen
import com.example.project1.ui.login.LoginState
import com.example.project1.ui.login.LoginViewModel
import com.example.project1.ui.profile.ProfileScreen
import com.example.project1.ui.profile.ProfileVMProvider
import com.example.project1.ui.profile.ProfileViewModel
import com.example.project1.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val userDao = db.userDao()

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
            var currentUser by rememberSaveable { mutableStateOf<String?>(null) }
            var route by rememberSaveable { mutableStateOf("landing") }
            var pendingUsername by rememberSaveable { mutableStateOf("") }

            val state = loginViewModel.loginState

            LaunchedEffect(state) {
                when (state) {
                    is LoginState.Success -> {
                        currentUser = pendingUsername
                        route = "landing"
                        Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        loginViewModel.reset()
                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        loginViewModel.reset()
                    }
                    else -> Unit
                }
            }

            if (currentUser == null) {
                LoginScreen { username, password ->
                    pendingUsername = username
                    loginViewModel.login(username, password)
                }
            } else {
                val profileVm: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = ProfileVMProvider(currentUser!!, db)
                )
              //displays the landing page
                when (route) {
                    "landing" -> LandingScreen(
                        username = currentUser!!,
                        onOpenProfile = { route = "profile" },
                        onOpenCards = { route = "cards" },
                        onOpenSettings = { route = "settings" }
                    )

                    "cards" -> {
                        // Holds the selected card. If null, we're on the list screen.
                        var selectedCard by remember { mutableStateOf<CardDto?>(null) }

                        Column {
                            // Back button behavior:
                            // - If you're in details, go back to list
                            // - If you're in list, go back to landing
                            Button(
                                onClick = {
                                    if (selectedCard != null) selectedCard = null else route = "landing"
                                }
                            ) { Text("Back") }

                            if (selectedCard == null) {
                                // Show the list
                                CardListScreen(
                                    onCardClick = { clicked ->
                                        // When the user clicks a card, show details
                                        selectedCard = clicked
                                    },
                                    onAddToWishlist = { dto ->
                                        val entity = UserCardEntity(
                                            username = currentUser!!,
                                            cardId = dto.id,
                                            cardName = dto.name,
                                            imageUrl = dto.cardImages?.firstOrNull()?.imageUrl,
                                            listType = "WISHLIST",
                                            deckName = null
                                        )

                                        lifecycleScope.launch {
                                            db.userCardDao().addCard(entity)
                                            profileVm.refresh()
                                            Toast.makeText(this@MainActivity, "Added to wishlist", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    onAddToDeck = { dto ->
                                        val entity = UserCardEntity(
                                            username = currentUser!!,
                                            cardId = dto.id,
                                            cardName = dto.name,
                                            imageUrl = dto.cardImages?.firstOrNull()?.imageUrl,
                                            listType = "DECK",
                                            deckName = "Main"
                                        )

                                        lifecycleScope.launch {
                                            db.userCardDao().addCard(entity)
                                            profileVm.refresh()
                                            Toast.makeText(this@MainActivity, "Added to deck", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            } else {
                                // Show details
                                CardDetailScreen(
                                    card = selectedCard!!,
                                    onBack = { selectedCard = null }
                                )
                            }
                        }
                    }

                    //added this so that the landing page is seen after logging in
                    "profile" -> {
                        var selectedProfileCard by remember { mutableStateOf<UserCardEntity?>(null) }

                        Column {
                            Button(
                                onClick = {
                                    if (selectedProfileCard != null) selectedProfileCard = null else route = "landing"
                                }
                            ) { Text("Back") }

                            LaunchedEffect(Unit) { profileVm.load() }

                            if (selectedProfileCard == null) {
                                ProfileScreen(
                                    vm = profileVm,
                                    onCardClick = { clicked ->
                                        selectedProfileCard = clicked
                                    },
                                    onRemoveCard = { cardToRemove ->
                                        profileVm.removeCard(cardToRemove)
                                        Toast.makeText(this@MainActivity, "Removed", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            } else {
                                // ✅ Reuses the SAME CardDetailScreen via API lookup by id
                                UserCardDetailScreen(
                                    savedCard = selectedProfileCard!!,
                                    onBack = { selectedProfileCard = null }
                                )
                            }
                        }
                    }


                    "settings" -> Column {
                        Button(onClick = { route = "landing" }) { Text("Back") }
                        SettingsScreen()
                    }

                    else -> route = "landing"
                }
            }
        }
    }
}
