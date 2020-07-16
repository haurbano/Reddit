package com.haurbano.domain.common

sealed class Resource<T>(
    val data: T?,
    val error: ErrorEntity?
) {
    class Success<T>(val successData: T): Resource<T>(successData, null)
    class Error<T>(error: ErrorEntity): Resource<T>(null, error)
    class Progress<T>: Resource<T>(null, null)
}