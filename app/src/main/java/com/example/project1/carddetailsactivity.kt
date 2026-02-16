package com.example.project1

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class CardDetailsActivity : AppCompatActivity() {

    private lateinit var cardImage: ImageView
    private lateinit var cardName: TextView
    private lateinit var cardType: TextView
    private lateinit var cardAttribute: TextView
    private lateinit var cardLevel: TextView
    private lateinit var cardAtk: TextView
    private lateinit var cardDef: TextView
    private lateinit var cardDescription: TextView
    private lateinit var cardRace: TextView
    private lateinit var cardArchetype: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var contentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Card Details"

        cardImage = findViewById(R.id.cardDetailImage)
        cardName = findViewById(R.id.cardDetailName)
        cardType = findViewById(R.id.cardDetailType)
        cardAttribute = findViewById(R.id.cardDetailAttribute)
        cardLevel = findViewById(R.id.cardDetailLevel)
        cardAtk = findViewById(R.id.cardDetailAtk)
        cardDef = findViewById(R.id.cardDetailDef)
        cardDescription = findViewById(R.id.cardDetailDescription)
        cardRace = findViewById(R.id.cardDetailRace)
        cardArchetype = findViewById(R.id.cardDetailArchetype)
        progressBar = findViewById(R.id.progressBar)
        contentLayout = findViewById(R.id.contentLayout)

        val cardId = intent.getLongExtra("CARD_ID", 0)

        if (cardId == 0L) {
            Toast.makeText(this, "Error: Invalid card ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadCardDetails(cardId)
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

    private fun loadCardDetails(cardId: Long) {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                contentLayout.visibility = View.GONE

                val cardData = withContext(Dispatchers.IO) {
                    fetchCardFromAPI(cardId)
                }

                if (cardData != null) {
                    displayCardDetails(cardData)
                } else {
                    Toast.makeText(
                        this@CardDetailsActivity,
                        "Card not found",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@CardDetailsActivity,
                    "Error loading card: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } finally {
                progressBar.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchCardFromAPI(cardId: Long): JSONObject? {
        return try {
            val url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?id=$cardId"
            val response = URL(url).readText()
            val jsonResponse = JSONObject(response)

            val dataArray = jsonResponse.getJSONArray("data")
            if (dataArray.length() > 0) {
                dataArray.getJSONObject(0)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun displayCardDetails(cardData: JSONObject) {
        cardName.text = cardData.optString("name", "Unknown")
        cardType.text = "Type: ${cardData.optString("type", "N/A")}"
        cardDescription.text = cardData.optString("desc", "No description available")
        cardRace.text = "Race: ${cardData.optString("race", "N/A")}"

        val archetype = cardData.optString("archetype", "")
        if (archetype.isNotEmpty()) {
            cardArchetype.visibility = View.VISIBLE
            cardArchetype.text = "Archetype: $archetype"
        } else {
            cardArchetype.visibility = View.GONE
        }

        if (cardData.has("attribute")) {
            cardAttribute.visibility = View.VISIBLE
            cardAttribute.text = "Attribute: ${cardData.optString("attribute", "N/A")}"
        } else {
            cardAttribute.visibility = View.GONE
        }

        if (cardData.has("level")) {
            cardLevel.visibility = View.VISIBLE
            cardLevel.text = "Level: ${cardData.optInt("level", 0)}"
        } else if (cardData.has("linkval")) {
            cardLevel.visibility = View.VISIBLE
            cardLevel.text = "Link: ${cardData.optInt("linkval", 0)}"
        } else {
            cardLevel.visibility = View.GONE
        }

        if (cardData.has("atk")) {
            cardAtk.visibility = View.VISIBLE
            cardAtk.text = "ATK: ${cardData.optInt("atk", 0)}"
        } else {
            cardAtk.visibility = View.GONE
        }

        if (cardData.has("def")) {
            cardDef.visibility = View.VISIBLE
            cardDef.text = "DEF: ${cardData.optInt("def", 0)}"
        } else {
            cardDef.visibility = View.GONE
        }

        val imageUrl = if (cardData.has("card_images")) {
            val images = cardData.getJSONArray("card_images")
            if (images.length() > 0) {
                images.getJSONObject(0).optString("image_url", "")
            } else {
                ""
            }
        } else {
            ""
        }

        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_card_placeholder)
                .error(R.drawable.ic_card_error)
                .into(cardImage)
        }
    }
}