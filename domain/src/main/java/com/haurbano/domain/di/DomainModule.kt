package com.haurbano.domain.di

import com.haurbano.domain.usecases.CheckPostAsReadUseCase
import com.haurbano.domain.usecases.GetPostUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPostUseCase(get()) }
    factory { CheckPostAsReadUseCase(get()) }
}