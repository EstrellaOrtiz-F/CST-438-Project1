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
    val image_url: String?
)

data class CardPriceDto(
    @Json(name = "tcgplayer_price")
    val tcgplayer_price: String?,

    @Json(name = "cardmarket_price")
    val cardmarket_price: String?
)