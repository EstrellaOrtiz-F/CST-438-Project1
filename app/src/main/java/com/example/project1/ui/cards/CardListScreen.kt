package com.example.project1.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.project1.network.CardDto

/**
 * CardListScreen with:
 * - search box
 * - simple filter dropdown (All / Monsters / Spells / Traps)
 */
@Composable
fun CardListScreen(
    vm: CardViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCardClick: (CardDto) -> Unit = {},
    onAddToWishlist: (CardDto) -> Unit,
    onAddToDeck: (CardDto) -> Unit
) {
    // Load first page when screen appears
    LaunchedEffect(Unit) {
        vm.loadFirstPage()
    }

    // Search & filter UI state
    var searchQuery by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(FilterOption.All) }

    // Derived filtered list from vm.cards (client-side filter)
    val filteredCards by remember(vm.cards, selectedFilter, searchQuery) {
        derivedStateOf {
            val base = vm.cards

            // If searchQuery is not blank, we prefer to perform server-side fuzzy search
            // by calling vm.searchByFuzzyName when user taps Search button below.
            // For client-side filtering of loaded results:
            base.filter { card ->
                when (selectedFilter) {
                    FilterOption.All -> true
                    FilterOption.Monsters -> (card.frameType?.lowercase() ?: "").let { ft ->
                        ft != "spell" && ft != "trap"
                    }
                    FilterOption.Spells -> (card.frameType?.lowercase() ?: "") == "spell"
                    FilterOption.Traps -> (card.frameType?.lowercase() ?: "") == "trap"
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Top bar: Search + Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by Card Name") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Search button: trigger server-side fuzzy search (uses the API)
            Button(
                onClick = {
                    val q = searchQuery.trim()
                    if (q.isEmpty()) {
                        // No query: reload first page
                        vm.loadFirstPage()
                    } else {
                        vm.searchByFuzzyName(q)
                    }
                },
                modifier = Modifier.height(56.dp)
            ) {
                Text("Search")
            }
        }

        // Filter dropdown row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Filter:", modifier = Modifier.padding(end = 8.dp))
            Box {
                Button(onClick = { dropdownExpanded = true }) {
                    Text(selectedFilter.label)
                }
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    FilterOption.values().forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.label) },
                            onClick = {
                                selectedFilter = option
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Simple loading indicator
            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }

        // Error dialog (covers the screen so the message cannot be hidden. Mainly used it for testing)
        vm.errorMessage?.let { msg ->
            AlertDialog(
                onDismissRequest = { vm.clearError() },
                title = { Text("Error loading cards") },
                text = { Text(msg) },
                confirmButton = {
                    TextButton(onClick = { vm.clearError() }) {
                        Text("OK")
                    }
                }
            )
        }

        // List area
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(filteredCards) { card ->
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

                            Text("Market price: $price", style = MaterialTheme.typography.bodyMedium)
                            // show type/frameType for context
                            Text(
                                text = (card.type ?: card.frameType ?: ""),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.height(8.dp))
                            Row {
                                Button(onClick = { onAddToWishlist(card) }) {
                                    Text("Wishlist")
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = { onAddToDeck(card) }) {
                                    Text("Deck")
                                }
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { vm.loadMore() },
                    enabled = !vm.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(if (vm.isLoading) "Loading..." else "Load more")
                }
            }
        }
    }
}

/** Simple enum for the filter dropdown */
private enum class FilterOption(val label: String) {
    All("All"),
    Monsters("Monsters"),
    Spells("Spells"),
    Traps("Traps")
}
