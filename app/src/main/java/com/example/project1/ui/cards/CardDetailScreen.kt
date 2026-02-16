package com.example.project1.ui.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.project1.network.CardDto

/**
 * Displays a larger card image plus important card info.
 * This is shown when a user taps a card from CardListScreen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(
    card: CardDto,
    onBack: () -> Unit
) {
    val imgUrl = card.cardImages?.firstOrNull()?.imageUrl

    val price = card.cardPrices?.firstOrNull()?.tcgplayerPrice
        ?: card.cardPrices?.firstOrNull()?.cardmarketPrice
        ?: "N/A"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.name ?: "Card Details") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Large image
            AsyncImage(
                model = imgUrl,
                contentDescription = card.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            )

            Text(card.name ?: "Unknown Name", style = MaterialTheme.typography.headlineSmall)
            Text("Type: ${card.type ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
            Text("Market price: $price", style = MaterialTheme.typography.bodyLarge)

            // Optional monster stats
            val atk = card.atk
            val def = card.def
            val level = card.level
            if (atk != null || def != null || level != null) {
                val stats = buildString {
                    if (atk != null) append("ATK: $atk  ")
                    if (def != null) append("DEF: $def  ")
                    if (level != null) append("Level: $level")
                }.trim()
                if (stats.isNotBlank()) Text(stats, style = MaterialTheme.typography.bodyLarge)
            }

            // Description
            if (!card.desc.isNullOrBlank()) {
                Text("Effect / Description", style = MaterialTheme.typography.titleMedium)
                Text(card.desc!!, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
