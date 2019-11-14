package com.cts.avin.network

interface ResponseListener<T> {
    fun onSuccess(t: T)
    fun onError(msg: String)
}
