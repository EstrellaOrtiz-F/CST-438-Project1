package com.example.project1.ui.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project1.database.UserCardEntity
import com.example.project1.repository.CardRepository

/**
 * Loads full CardDto details from the API using the saved UserCardEntity.cardId,
 * then displays the shared CardDetailScreen.
 */
@Composable
fun UserCardDetailScreen(
    savedCard: UserCardEntity,
    onBack: () -> Unit,
    repo: CardRepository = CardRepository()
) {
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var cardDto by remember { mutableStateOf<com.example.project1.network.CardDto?>(null) }

    LaunchedEffect(savedCard.cardId) {
        isLoading = true
        error = null
        cardDto = null

        try {
            cardDto = repo.getCardById(savedCard.cardId.toInt())
            if (cardDto == null) {
                error = "Could not load details for this card."
            }
        } catch (e: Exception) {
            error = "Failed to load card: ${e.message ?: "unknown error"}"
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        error != null -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error", style = MaterialTheme.typography.titleLarge)
            Text(error!!)
            Button(onClick = onBack) { Text("Back") }
        }

        cardDto != null -> CardDetailScreen(
            card = cardDto!!,
            onBack = onBack
        )
    }
}
