package com.example.project1.network

import com.squareup.moshi.Json

data class CardInfoResponse(
    val data: List<CardDto>
)

data class CardDto(
    val id: Long,
    val name: String,
    val desc: String?,

    @Json(name = "card_images")
    val cardImages: List<CardImageDto>?,

    @Json(name = "card_prices")
    val cardPrices: List<CardPriceDto>?
)

data class CardImageDto(
    @Json(name = "image_url")
    val imageUrl: String?
)

data class CardPriceDto(
    @Json(name = "tcgplayer_price")
    val tcgplayerPrice: String?,

    @Json(name = "cardmarket_price")
    val cardmarketPrice: String?
)
