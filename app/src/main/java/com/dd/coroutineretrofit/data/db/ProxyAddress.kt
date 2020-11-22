package com.dd.coroutineretrofit.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proxyList")
data class ProxyAddress(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "ip")
    val ip: String,

    @ColumnInfo(name = "port")
    val port: Int,

    @ColumnInfo(name = "isUsed")
    val isUsed: Boolean = false,

    @ColumnInfo(name = "isSuccessfullySent")
    val isSuccessfullySent: Boolean = false,

    @ColumnInfo(name = "requestFailureDescription")
    val requestFailureDescription: String = "",

    @ColumnInfo(name = "voteCounter")
    val voteCounter: Int = 0
)