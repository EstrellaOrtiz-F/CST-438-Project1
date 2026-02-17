package com.example.project1.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.project1.database.UserCardEntity
import kotlin.math.ceil

private enum class ListTab { COLLECTION, WISHLIST }

@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    onCardClick: (UserCardEntity) -> Unit = {},
    onRemoveCard: (UserCardEntity) -> Unit = {}
) {
    LaunchedEffect(Unit) { vm.load() }

    var selectedTab by remember { mutableStateOf(ListTab.COLLECTION) }

    // Deck creation dialog state
    var showCreateDeck by remember { mutableStateOf(false) }
    var newDeckName by remember { mutableStateOf("") }

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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
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

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatChip(label = "Cards", value = vm.collection.size.toString())
                            StatChip(label = "Wishlist", value = vm.wishlist.size.toString())
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val collectionSelected = selectedTab == ListTab.COLLECTION
                            Button(
                                onClick = { selectedTab = ListTab.COLLECTION },
                                modifier = Modifier.weight(1f),
                                colors = if (collectionSelected)
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                else
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Text(
                                    text = "Collection",
                                    color = if (collectionSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            val wishlistSelected = selectedTab == ListTab.WISHLIST
                            OutlinedButton(
                                onClick = { selectedTab = ListTab.WISHLIST },
                                modifier = Modifier.weight(1f),
                                colors = if (wishlistSelected)
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                else
                                    ButtonDefaults.buttonColors(containerColor = Color.Transparent),
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
            }

            item {
                Text(
                    text = when (selectedTab) {
                        ListTab.COLLECTION -> "Card Collection"
                        ListTab.WISHLIST -> "Wishlist"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
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
                    val gridHeight = gridHeightForItems(
                        itemCount = displayed.size,
                        columns = 2,
                        itemHeight = 230.dp,
                        spacing = 12.dp
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridHeight),
                        userScrollEnabled = false
                    ) {
                        items(displayed) { card ->
                            NiceCardItem(
                                card = card,
                                onClick = { onCardClick(card) },
                                onRemove = { onRemoveCard(card) }
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Decks",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { showCreateDeck = true }) {
                        Text("Create Deck")
                    }
                }
            }

            // ✅ THIS is what you were missing:
            item {
                if (showCreateDeck) {
                    AlertDialog(
                        onDismissRequest = { showCreateDeck = false },
                        title = { Text("Create new deck") },
                        text = {
                            Column {
                                Text("Enter a deck name:")
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = newDeckName,
                                    onValueChange = { newDeckName = it },
                                    label = { Text("Deck name") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val trimmed = newDeckName.trim()
                                    if (trimmed.isNotEmpty()) {
                                        vm.createDeck(trimmed)
                                        newDeckName = ""
                                        showCreateDeck = false
                                    }
                                }
                            ) { Text("Create") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showCreateDeck = false }) { Text("Cancel") }
                        }
                    )
                }
            }

            // ✅ Render decks + their cards
            if (vm.decks.isEmpty()) {
                item {
                    Text(
                        text = "No decks created.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                    )
                }
            } else {
                vm.decks.forEach { (deckName, cards) ->
                    item {
                        Text(
                            text = deckName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (cards.isEmpty()) {
                        item {
                            Text(
                                text = "No cards in this deck.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                            )
                        }
                    } else {
                        item {
                            val deckHeight = gridHeightForItems(
                                itemCount = cards.size,
                                columns = 2,
                                itemHeight = 230.dp,
                                spacing = 12.dp
                            )

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(deckHeight),
                                userScrollEnabled = false
                            ) {
                                items(cards) { card ->
                                    NiceCardItem(
                                        card = card,
                                        onClick = { onCardClick(card) },
                                        onRemove = { onRemoveCard(card) }
                                    )
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
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
private fun NiceCardItem(
    card: UserCardEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    var showConfirm by remember { mutableStateOf(false) }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Remove card?") },
            text = {
                val where = when (card.listType) {
                    "WISHLIST" -> "wishlist"
                    "DECK" -> "deck \"${card.deckName ?: "Main"}\""
                    else -> "collection"
                }
                Text("Remove \"${card.cardName}\" from your $where?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirm = false
                        onRemove()
                    }
                ) { Text("Remove") }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("Cancel") }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.cardName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = { showConfirm = true }) {
                    Text("Remove")
                }
            }
        }
    }
}
private fun gridHeightForItems(
    itemCount: Int,
    columns: Int,
    itemHeight: Dp,
    spacing: Dp
): Dp {
    if (itemCount <= 0) return 0.dp
    val rows = ceil(itemCount / columns.toFloat()).toInt().coerceAtLeast(1)
    return (itemHeight * rows.toFloat()) + (spacing * (rows - 1).toFloat())
}