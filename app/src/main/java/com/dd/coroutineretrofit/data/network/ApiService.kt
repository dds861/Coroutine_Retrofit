package com.dd.coroutineretrofit.data.network

import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*

interface ApiService {

    @FormUrlEncoded
    @POST("/contests")
    suspend fun postVote(@Field("id") id: String): ResponseBody

}