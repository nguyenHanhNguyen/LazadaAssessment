package com.lazada.core

sealed class DataHolder {

    data class Success<T>(val response: T) : DataHolder()
    data class Error(val failure: Failure) : DataHolder()

}