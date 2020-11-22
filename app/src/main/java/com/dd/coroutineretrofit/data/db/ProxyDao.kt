package com.dd.coroutineretrofit.data.db

import android.content.ContentProviderClient
import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ProxyDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProxy(proxyAddress: ProxyAddress)

    @Query("UPDATE proxyList SET isUsed = :isUsed WHERE ip=:ip AND port=:port")
    suspend fun updateProxy(ip: String, port: Int, isUsed: Boolean)

    @Insert
    @JvmSuppressWildcards
    suspend fun insertAllProxy(list: List<ProxyAddress>)

    @Query("SELECT * FROM proxyList WHERE id = :id")
    suspend fun getProxy(id: Long): ProxyAddress

    @Query("SELECT * FROM (SELECT * FROM proxyList ORDER BY id ASC) ORDER BY isUsed ASC")
    fun getAllProxies(): LiveData<List<ProxyAddress>>

    @Query("SELECT * FROM proxyList WHERE isUsed = 0")
    suspend fun getUnUsedProxies(): List<ProxyAddress>

    @Query("SELECT EXISTS(SELECT 1 FROM proxyList WHERE isUsed=:ip AND port = :port AND isUsed=0 LIMIT 1)")
    suspend fun isProxyNew(ip: String, port: Int): Boolean


}