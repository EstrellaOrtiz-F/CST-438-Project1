package com.example.project1.repository

import com.example.project1.network.NetworkModule
import com.example.project1.network.CardDto

class CardRepository {
    private val api = NetworkModule.api

    suspend fun getCardsPage(num: Int, offset: Int): List<CardDto> =
        api.getCards(num = num, offset = offset).data

    suspend fun searchCards(name: String, num: Int, offset: Int): List<CardDto> =
        api.searchCards(name = name, num = num, offset = offset).data
}