package com.example.project1.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.project1.database.UserCardEntity

/**
 * @author Estrella Ortiz
 * Project:1
 * Abstract: Displays the users information like username
 * The user can also view their wishlist and card collection from their profile
 */

private enum class ListTab { COLLECTION, WISHLIST }

@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    onOpenWishlist: () -> Unit = {}
) {
    LaunchedEffect(Unit) { vm.load() }

    // which tab is currently selected
    var selectedTab by remember { mutableStateOf(ListTab.COLLECTION) }

    val bg = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
            MaterialTheme.colorScheme.background
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {
        if (vm.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            return
        }

        if (vm.user == null) {
            Text(
                text = "User not found",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge
            )
            return
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Profile card with avatar and stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = vm.user?.username?.firstOrNull()?.uppercase() ?: "U",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = vm.user?.username ?: "",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Welcome back!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    // Stats row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatChip(label = "Cards", value = vm.collection.size.toString())
                        StatChip(label = "Wishlist", value = vm.wishlist.size.toString())
                    }

                    // Buttons row: toggles for Collection/Wishlist
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Collection button - filled when selected
                        val collectionColors = if (selectedTab == ListTab.COLLECTION) {
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        } else {
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                        }

                        Button(
                            onClick = { selectedTab = ListTab.COLLECTION },
                            modifier = Modifier.weight(1f),
                            colors = collectionColors
                        ) {
                            Text(
                                text = "Collection",
                                color = if (selectedTab == ListTab.COLLECTION) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Wishlist button - outlined/outlined look when not selected, filled when selected
                        val wishlistSelected = selectedTab == ListTab.WISHLIST
                        val wishlistColors = if (wishlistSelected) {
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        }

                        OutlinedButton(
                            onClick = { selectedTab = ListTab.WISHLIST },
                            modifier = Modifier.weight(1f),
                            colors = wishlistColors,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Wishlist",
                                color = if (wishlistSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Title above grid reflects selected tab
            Text(
                text = when (selectedTab) {
                    ListTab.COLLECTION -> "Card Collection"
                    ListTab.WISHLIST -> "Wishlist"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Data source for the grid depends on selected tab
            val displayed = when (selectedTab) {
                ListTab.COLLECTION -> vm.collection
                ListTab.WISHLIST -> vm.wishlist
            }

            if (displayed.isEmpty()) {
                Text(
                    text = if (selectedTab == ListTab.COLLECTION) "No cards saved yet." else "No wishlist items.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(displayed) { card: UserCardEntity ->
                        NiceCardItem(card)
                    }
                }
            }


            Text(
                text = "Decks",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (vm.decks.isEmpty()) {
                Text(
                    text = "No decks created.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            } else {
                vm.decks.forEach { (deckName, cards) ->
                    Text(
                        text = deckName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (cards.isEmpty()) {
                        Text(
                            text = "No cards in this deck.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cards) { card: UserCardEntity ->
                                NiceCardItem(card)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(999.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = value, fontWeight = FontWeight.Bold)
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun NiceCardItem(card: UserCardEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = card.imageUrl,
                contentDescription = card.cardName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f))
            )

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = card.cardName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap to view",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

