package com.dicoding.picodiploma.loginwithanimation.data.remote.pref

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String
)