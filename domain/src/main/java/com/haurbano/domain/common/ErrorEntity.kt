package com.haurbano.domain.common

sealed class ErrorEntity {
    object NetworkError: ErrorEntity()
    object UnknownError: ErrorEntity()
    object DataNullError: ErrorEntity()
}