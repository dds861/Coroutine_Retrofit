package com.dd.coroutineretrofit.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dd.coroutineretrofit.repository.AppRepository
import kotlinx.coroutines.delay

class MainActivityViewModel(private val eventManager: UIEventManager) : ViewModel() {
    private val repository = AppRepository()

    fun loadDataFromWeb() = liveData {
        eventManager.showProgressBar()
        delay(1000)
        val receivedData = repository.getList()
        eventManager.hideProgressBar()
        eventManager.showToast("Loaded")
        emit(receivedData)
    }
}