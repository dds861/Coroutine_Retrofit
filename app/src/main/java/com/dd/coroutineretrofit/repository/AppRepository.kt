package com.dd.coroutineretrofit.repository

import com.dd.coroutineretrofit.model.User
import com.dd.coroutineretrofit.retrofit.ApiService
import com.dd.coroutineretrofit.retrofit.RetrofitInstance

class AppRepository {
    private var service: ApiService = RetrofitInstance.appService

    suspend fun getList(): List<User> = service.getList()
}