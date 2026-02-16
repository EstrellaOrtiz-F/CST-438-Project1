package com.example.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.database.UserCardEntity

class WishlistAdapter(
    private var cards: List<UserCardEntity>,
    private val onCardClick: (UserCardEntity) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
        val cardName: TextView = itemView.findViewById(R.id.cardName)
        val cardId: TextView = itemView.findViewById(R.id.cardId)

        fun bind(card: UserCardEntity) {
            cardName.text = card.cardName
            cardId.text = "ID: ${card.cardId}"

            if (!card.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(card.imageUrl)
                    .placeholder(R.drawable.ic_card_placeholder)
                    .error(R.drawable.ic_card_error)
                    .into(cardImage)
            } else {
                cardImage.setImageResource(R.drawable.ic_card_placeholder)
            }

            itemView.setOnClickListener {
                onCardClick(card)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlist_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    fun updateCards(newCards: List<UserCardEntity>) {
        cards = newCards
        notifyDataSetChanged()
    }
}