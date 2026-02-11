package com.example.project1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * NetworkModule
 * - Creates a Retrofit instance for the YGOProDeck API
 * - Uses Moshi + KotlinJsonAdapterFactory so Kotlin data classes parse correctly
 */
object NetworkModule {

    private const val BASE_URL = "https://db.ygoprodeck.com/api/v7/"

    // IMPORTANT: KotlinJsonAdapterFactory enables Moshi to parse Kotlin data classes properly
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                // BASIC shows request/response lines (enough for debugging without spam)
                level = HttpLoggingInterceptor.Level.BASIC
            }
        )
        .build()

    val api: YgoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(YgoApi::class.java)
}
