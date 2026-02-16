package com.example.project1.network

import retrofit2.http.GET
import retrofit2.http.Query

interface YgoApi {

    @GET("cardinfo.php")
    suspend fun getCards(
        @Query("num") num: Int = 20,
        @Query("offset") offset: Int = 0
    ): CardInfoResponse

    /**
     * Fuzzy search by card name using `fname`.
     * Example: fname=Magician returns all cards containing "Magician".
     */
    @GET("cardinfo.php")
    suspend fun searchCards(
        @Query("fname") fname: String,
        @Query("num") num: Int = 20,
        @Query("offset") offset: Int = 0
    ): CardInfoResponse
}
