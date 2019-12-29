package com.dd.coroutineretrofit.model
import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("hobby")
    val hobby: String,
    @SerializedName("name")
    val name: String
)