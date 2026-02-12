package com.example.project1

import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wishlist"

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewWishlist)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup SearchView
        searchView = findViewById(R.id.searchViewWishlist)
        setupSearch()

        // Load wishlist cards from database
        loadWishlistCards()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate back to landing page
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
                // Filter the wishlist based on search text
                filterWishlist(newText)
                return true
            }
        })
    }

    private fun loadWishlistCards() {
        // TODO: Load wishlist cards from your database
        // Example:
        // val wishlistCards = database.getWishlistCards()
        // val adapter = WishlistAdapter(wishlistCards) { card ->
        //     // Handle card click - navigate to card details
        //     openCardDetails(card)
        // }
        // recyclerView.adapter = adapter
    }

    private fun filterWishlist(query: String?) {
        // TODO: Filter wishlist based on search query
        // Update the adapter with filtered results
    }

    private fun openCardDetails(cardId: String) {
        // TODO: Navigate to card details activity
        // val intent = Intent(this, CardDetailsActivity::class.java)
        // intent.putExtra("CARD_ID", cardId)
        // startActivity(intent)
    }
}