package com.calvin.internkumparantest.data

sealed class Resource<out T> {
    data class Loading<out T>(val state: Boolean) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String) : Resource<T>()
}