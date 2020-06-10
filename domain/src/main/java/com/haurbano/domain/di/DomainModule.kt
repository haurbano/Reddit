package com.haurbano.domain.di

import com.haurbano.domain.GetPostUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPostUseCase(get()) }
}