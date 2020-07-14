package com.haurbano.data.repositories.common

import com.haurbano.domain.common.ErrorEntity
import com.haurbano.domain.common.Resource
import java.io.IOException
import java.lang.Exception

class IOErrorHandlerRepository: ErrorHandlerRepository {
    override suspend fun <T> handlerErrors(task: suspend () -> Resource<T>): Resource<T> {
        return try {
            task()
        } catch (exc: IOException) {
            Resource.Error<T>(ErrorEntity.NetworkError)
        } catch (exc: Exception) {
            Resource.Error<T>(ErrorEntity.UnknownError)
        }
    }

}