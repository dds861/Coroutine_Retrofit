package com.dd.coroutineretrofit.retrofit

import com.dd.coroutineretrofit.constants.ConstantsApp
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val appService: ApiService by lazy {
        Retrofit.Builder()
                .baseUrl(ConstantsApp.URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(ApiService::class.java)
    }
}