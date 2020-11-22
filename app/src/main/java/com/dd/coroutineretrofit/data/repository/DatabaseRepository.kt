package com.dd.coroutineretrofit.data.repository

import androidx.lifecycle.LiveData
import com.dd.coroutineretrofit.data.db.ProxyAddress
import com.dd.coroutineretrofit.data.db.ProxyDao

class DatabaseRepository(private val proxyDao: ProxyDao) {

    val allProxies: LiveData<List<ProxyAddress>> = proxyDao.getAllProxies()

    suspend fun insert(proxyAddress: ProxyAddress) {
        proxyDao.insertProxy(proxyAddress)
    }

    suspend fun updateProxy(proxyAddress: ProxyAddress) {
        proxyDao.updateProxy(proxyAddress.ip, proxyAddress.port, proxyAddress.isUsed)
    }

    suspend fun insertAllProxy(list: List<ProxyAddress>) {
        proxyDao.insertAllProxy(list)
    }

    suspend fun getProxy(id: Long): ProxyAddress {
        return proxyDao.getProxy(id)
    }

    suspend fun getUnUsedProxy(): List<ProxyAddress> {
        return proxyDao.getUnUsedProxies()
    }

    suspend fun isProxyNew(proxyAddress: ProxyAddress): Boolean {
        return proxyDao.isProxyNew(proxyAddress.ip, proxyAddress.port)
    }
}