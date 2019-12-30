package com.dd.coroutineretrofit.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dd.coroutineretrofit.repository.AppRepository
import kotlinx.coroutines.delay
import java.io.IOException

class MainActivityViewModel(private val eventManager: UIEventManager) : ViewModel() {
    private val repository = AppRepository()

    fun loadDataFromWeb() = liveData {
        try {
            eventManager.showProgressBar()
            delay(1000)
            val receivedData = repository.getList()
            eventManager.hideProgressBar()
            eventManager.showToast("Loaded")
            emit(receivedData)
        } catch (e: IOException) {
            eventManager.showToast("IOException")
            eventManager.hideProgressBar()
        } catch (e: Exception) {
            eventManager.showToast("Exception")
            eventManager.hideProgressBar()
        }
    }
}