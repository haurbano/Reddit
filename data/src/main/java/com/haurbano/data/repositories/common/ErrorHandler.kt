package com.haurbano.data.repositories.common

import com.haurbano.domain.common.Resource

interface ErrorHandler {
    suspend fun <T> handleErrors(task: suspend () -> Resource<T>): Resource<T>
}