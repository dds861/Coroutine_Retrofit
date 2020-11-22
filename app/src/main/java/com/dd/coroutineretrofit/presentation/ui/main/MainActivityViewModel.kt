package com.dd.coroutineretrofit.presentation.ui.main

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dd.coroutineretrofit.data.db.ProxyAddress
import com.dd.coroutineretrofit.data.db.ProxyDatabase
import com.dd.coroutineretrofit.data.repository.ApiRepository
import com.dd.coroutineretrofit.data.repository.DatabaseRepository
import com.dd.coroutineretrofit.presentation.ui.add.ProxyPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(application: Application, private val eventManager: UIEventManager) :
    ViewModel() {

    private val databaseRepository: DatabaseRepository =
        DatabaseRepository(ProxyDatabase.getDatabase(application, viewModelScope).getProxyDao())

    val allProxies: LiveData<List<ProxyAddress>>
    var isCancelIteration = false

    init {
        allProxies = databaseRepository.allProxies
    }

    private fun insert(proxyAddress: ProxyAddress) {
        viewModelScope.launch {
            databaseRepository.insert(proxyAddress)
        }
    }

    private fun update(proxyAddress: ProxyAddress) {
        viewModelScope.launch {
            databaseRepository.updateProxy(proxyAddress)
        }
    }

    private fun insertAllProxy(list: List<ProxyAddress>) {
        viewModelScope.launch {
            databaseRepository.insertAllProxy(list)
        }
    }

    fun insertAllProxy(list: ArrayList<ProxyPresentation>) {
        val proxies: List<ProxyAddress> = list.map {

            var ip = it.ip
            var port = it.port.toInt()

            if (!Patterns.IP_ADDRESS.matcher(ip).matches()) {
                ip = "0.0.0.0"
                port = 0
            }

            ProxyAddress(
                ip = ip,
                port = port,
                isUsed = false,
                isSuccessfullySent = false,
                requestFailureDescription = "unknown",
                voteCounter = 0
            )

        }.filter {
            it.ip != "0.0.0.0"
        }

        insertAllProxy(proxies)
    }


    fun startLoadDataFromWeb() = liveData {

        databaseRepository.getUnUsedProxy().let {
            loop@ for (i in it.indices) {

                if (isCancelIteration) {
                    break@loop
                }

                Log.i("autolog", "proxy send:${it[i].id} ${it[i].ip}:${it[i].port}")

                val proxy = ProxyAddress(
                    ip = it[i].ip,
                    port = it[i].port,
                    isUsed = false,
                    isSuccessfullySent = false,
                    requestFailureDescription = "unknown",
                    voteCounter = 0
                )

                Log.i("autolog", "isProxyNew: true")
                ApiRepository(it[i]).let { repository ->
                    try {
                        eventManager.showProgressBar()
                        delay(1000)

                        Log.i("autolog2", "Before send")
                        val receivedData = repository.postVote("125080")
                        Log.i("autolog2", "After send")



                        update(
                            proxy.copy(
                                isUsed = true,
                                isSuccessfullySent = true,
                                requestFailureDescription = "successfull"
                            )
                        )
                        eventManager.hideProgressBar()
                        Log.i("autolog", "Sent successfully : ${receivedData}")
                        emit(receivedData)
                    } catch (e: Exception) {
                        update(
                            proxy.copy(
                                isUsed = true,
                                isSuccessfullySent = false,
                                requestFailureDescription = e.message.toString()
                            )
                        )
                        eventManager.hideProgressBar()
                        eventManager.showToast("Not Successful")
                        Log.i("autolog", "Exception: $e")
                    }
                }

            }
        }
    }


}