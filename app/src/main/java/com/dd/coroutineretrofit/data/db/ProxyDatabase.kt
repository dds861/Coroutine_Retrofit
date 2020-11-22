package com.dd.coroutineretrofit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProxyAddress::class], version = 1, exportSchema = false)
abstract class ProxyDatabase : RoomDatabase() {

    abstract fun getProxyDao(): ProxyDao

    private class ProxyDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance?.let { database ->
                scope.launch {
                    val proxyDao = database.getProxyDao()
                    proxyDao.insertProxy(
                        ProxyAddress(
                            ip = "192.168.0.1",
                            port = 8080,
                            isUsed = true,
                            isSuccessfullySent = false,
                            voteCounter = 0,
                            requestFailureDescription = "test"
                        )
                    )
                }

            }
        }
    }


    companion object {
        private val instance: ProxyDatabase? = null

        fun getDatabase(context: Context, viewModelScope: CoroutineScope): ProxyDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    ProxyDatabase::class.java,
                    "appDatabase.db"
                )
                    .addCallback(ProxyDatabaseCallback(viewModelScope))
                    .build()
            }
        }
    }
}