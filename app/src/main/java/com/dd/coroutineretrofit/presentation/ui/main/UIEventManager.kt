package com.dd.coroutineretrofit.presentation.ui.main

interface UIEventManager {
    fun showToast(text: String)

    fun showProgressBar()

    fun hideProgressBar()
}