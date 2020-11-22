package com.dd.coroutineretrofit.presentation.ui.add

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProxyPresentation(
    val id: Long = 0,
    val ip: String,
    val port: String,
    val isUsed: Boolean = false,
    val isSuccessfullySent: Boolean,
    val requestFailureDescription: String,
    val voteCounter: Int
) : Parcelable