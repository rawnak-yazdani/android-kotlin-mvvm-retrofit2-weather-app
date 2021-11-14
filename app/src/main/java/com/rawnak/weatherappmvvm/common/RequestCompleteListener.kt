package com.rawnak.weatherappmvvm.common

interface RequestCompleteListener<T> {
    fun onRequestSuccess(data: T)
    fun onRequestFailed(errorMessage: String)
}