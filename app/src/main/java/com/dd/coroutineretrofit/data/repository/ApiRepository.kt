package com.dd.coroutineretrofit.data.repository

import com.dd.coroutineretrofit.data.db.ProxyAddress
import com.dd.coroutineretrofit.data.network.ApiService
import com.dd.coroutineretrofit.data.network.RetrofitInstance

class ApiRepository(private val proxy: ProxyAddress) {
    private var service: ApiService = RetrofitInstance(proxy).appService

    suspend fun postVote(id: String) = service.postVote(id)
}