package com.dd.coroutineretrofit.data.network

import com.dd.coroutineretrofit.data.DataConstants
import com.dd.coroutineretrofit.presentation.model.ProxyAddress
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit


class RetrofitInstance(private val proxyAddress: ProxyAddress) {

    private val client = OkHttpClient.Builder().let {
        it.connectTimeout(10, TimeUnit.SECONDS)
        it.writeTimeout(10, TimeUnit.SECONDS)
        it.readTimeout(10, TimeUnit.SECONDS)
        it.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyAddress.ip, proxyAddress.port)))
        it.interceptors()
            .add(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
        it.connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS));
        it.build()
    }


    val appService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(DataConstants.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}