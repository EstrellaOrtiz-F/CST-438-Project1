package com.example.project1.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.project1.database.UserCardEntity

/**
 * Simple profile screen (no experimental Material APIs).
 * Shows username + card collection from Room.
 */
@Composable
fun ProfileScreen(vm: ProfileViewModel) {
    LaunchedEffect(Unit) { vm.load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        if (vm.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            return
        }

        if (vm.user == null) {
            Text("User not found")
            return
        }

        // Acceptance Criteria #1: Username displayed
        Text(
            text = "Username: ${vm.user?.username ?: ""}",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Total cards: ${vm.cards.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Card Collection",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        // Acceptance Criteria #2: Card collection displayed
        if (vm.cards.isEmpty()) {
            Text("No cards saved yet.")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(vm.cards) { card ->
                    SimpleCardItem(card)
                }
            }
        }
    }
}

@Composable
private fun SimpleCardItem(card: UserCardEntity) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                model = card.imageUrl,
                contentDescription = card.cardName,
                modifier = Modifier.height(140.dp)
            )

            Text(
                text = card.cardName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

