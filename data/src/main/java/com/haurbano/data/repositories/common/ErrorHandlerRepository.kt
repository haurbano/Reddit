package com.haurbano.data.repositories.common

import com.haurbano.domain.common.Resource

interface ErrorHandlerRepository {
    suspend fun <T> handlerErrors(task: suspend () -> Resource<T>): Resource<T>
}