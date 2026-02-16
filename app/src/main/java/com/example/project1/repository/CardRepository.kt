package com.example.project1.repository

import com.example.project1.network.NetworkModule
import com.example.project1.network.CardDto

/**
 * Repository that wraps YgoApi calls.
 * Keeps network logic out of the ViewModel.
 */
class CardRepository {
    private val api = NetworkModule.api

    suspend fun getCardsPage(num: Int, offset: Int): List<CardDto> =
        api.getCards(num = num, offset = offset).data ?: emptyList()

    /**
     * Fuzzy search using YGOProDeck's `fname` parameter.
     */
    suspend fun searchByFuzzyName(query: String, num: Int, offset: Int): List<CardDto> =
        api.searchCards(fname = query, num = num, offset = offset).data ?: emptyList()
}
