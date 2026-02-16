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
 * Shows details for a single Yu-Gi-Oh card.
 * Should Display a larger image and key fields (name, type, description, prices, etc).
 */
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
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
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
            // Larger card image
            AsyncImage(
                model = imgUrl,
                contentDescription = card.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )

            // Core info
            Text(
                text = card.name ?: "Unknown Name",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Type: ${card.type ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Market price: $price",
                style = MaterialTheme.typography.bodyLarge
            )

            // Stats (only show if present)
            val atk = card.atk
            val def = card.def
            val level = card.level

            if (atk != null || def != null || level != null) {
                val stats = buildString {
                    if (atk != null) append("ATK: $atk  ")
                    if (def != null) append("DEF: $def  ")
                    if (level != null) append("Level: $level")
                }.trim()

                if (stats.isNotBlank()) {
                    Text(
                        text = stats,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Description / effect text
            if (!card.desc.isNullOrBlank()) {
                Text(
                    text = "Effect / Description",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = card.desc!!,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
