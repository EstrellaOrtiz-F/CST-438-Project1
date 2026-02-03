package com.example.project1.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.project1.network.CardDto

@Composable
fun CardListScreen(
    vm: CardViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCardClick: (CardDto) -> Unit = {}
) {
    LaunchedEffect(Unit) { vm.loadFirstPage() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(vm.cards) { card ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick(card) }
            ) {
                Row(Modifier.padding(12.dp)) {
                    val img = card.cardImages?.firstOrNull()?.imageUrl
                    AsyncImage(
                        model = img,
                        contentDescription = card.name,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(card.name, style = MaterialTheme.typography.titleMedium)

                        val price = card.cardPrices?.firstOrNull()?.tcgplayerPrice
                            ?: card.cardPrices?.firstOrNull()?.cardmarketPrice
                            ?: "N/A"

                        Text("Market price: $price")
                    }
                }
            }
        }

        item {
            Button(
                onClick = { vm.loadMore() },
                enabled = !vm.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (vm.isLoading) "Loading..." else "Load more")
            }
        }
    }
}
