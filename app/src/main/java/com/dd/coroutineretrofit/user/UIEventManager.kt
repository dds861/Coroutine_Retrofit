package com.dd.coroutineretrofit.user

interface UIEventManager {
    fun showToast(text: String)

    fun showProgressBar()

    fun hideProgressBar()
}