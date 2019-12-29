package com.dd.coroutineretrofit.retrofit

import com.dd.coroutineretrofit.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("dds861/e5f0ae4e99ff820e003c38f35276d4ce/raw/35f7b1452b3cfd55009416c62b055592858b9177/json1.json")
    suspend fun getList(): List<User>

}