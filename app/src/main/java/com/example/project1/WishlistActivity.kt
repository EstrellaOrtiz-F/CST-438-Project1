package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.database.AppDatabase
import com.example.project1.database.UserCardEntity
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: WishlistAdapter
    private lateinit var database: AppDatabase
    private var allCards: List<UserCardEntity> = emptyList()
    private var currentUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        currentUsername = intent.getStringExtra("USERNAME") ?: ""

        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "Error: No user logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        database = AppDatabase.getDatabase(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wishlist"

        recyclerView = findViewById(R.id.recyclerViewWishlist)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.searchViewWishlist)
        setupSearch()

        loadWishlistCards()
    }

    override fun onResume() {
        super.onResume()
        loadWishlistCards()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterWishlist(newText)
                return true
            }
        })
    }

    private fun loadWishlistCards() {
        lifecycleScope.launch {
            try {
                allCards = database.userCardDao().getCardsForUser(currentUsername)

                adapter = WishlistAdapter(allCards) { card ->
                    openCardDetails(card)
                }
                recyclerView.adapter = adapter

                if (allCards.isEmpty()) {
                    Toast.makeText(
                        this@WishlistActivity,
                        "Your wishlist is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@WishlistActivity,
                    "Error loading wishlist: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun filterWishlist(query: String?) {
        if (!::adapter.isInitialized) return

        val filteredCards = if (query.isNullOrBlank()) {
            allCards
        } else {
            allCards.filter { card ->
                card.cardName.contains(query, ignoreCase = true)
            }
        }

        adapter.updateCards(filteredCards)
    }

    private fun openCardDetails(card: UserCardEntity) {
        val intent = Intent(this, CardDetailsActivity::class.java)
        intent.putExtra("CARD_ID", card.cardId)
        startActivity(intent)
    }
}