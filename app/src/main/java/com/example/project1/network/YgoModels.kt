package com.example.project1.network

import com.squareup.moshi.Json

/**
 * Models for YGOProDeck API responses.
 * Add/adjust fields here to match the API response.
 */

/** Top-level response wrapper from cardinfo.php */
data class CardInfoResponse(
    @Json(name = "data")
    val data: List<CardDto>? = null,

    @Json(name = "meta")
    val meta: MetaDto? = null
)

data class MetaDto(
    @Json(name = "current_rows")
    val currentRows: Int? = null,
    @Json(name = "total_rows")
    val totalRows: Int? = null,
    @Json(name = "next_page")
    val nextPage: String? = null,
    @Json(name = "next_page_offset")
    val nextPageOffset: Int? = null
)

/** Card DTO representing a single card returned by the API */
data class CardDto(
    val id: Long,
    val name: String,
    val desc: String?,
    val atk: Int?,
    val def: Int?,
    val level: Int?,

    //  added these two for display / filtering
    // `type` is human-friendly (eg. "Normal Monster", "Spell Card")
    val type: String?,
    // `frameType` is more machine-friendly (eg. "normal", "spell", "trap")
    val frameType: String?,

    @Json(name = "card_images")
    val cardImages: List<CardImageDto>? = null,

    @Json(name = "card_prices")
    val cardPrices: List<CardPriceDto>? = null
)

data class CardImageDto(
    val id: Long,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "image_url_small")
    val imageUrlSmall: String?,
    @Json(name = "image_url_cropped")
    val imageUrlCropped: String?
)

data class CardPriceDto(
    @Json(name = "cardmarket_price")
    val cardmarketPrice: String?,
    @Json(name = "tcgplayer_price")
    val tcgplayerPrice: String?,
    @Json(name = "ebay_price")
    val ebayPrice: String?,
    @Json(name = "amazon_price")
    val amazonPrice: String?,
    @Json(name = "coolstuffinc_price")
    val coolstuffincPrice: String?
)
