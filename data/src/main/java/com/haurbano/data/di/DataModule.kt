package com.haurbano.data.di

import com.haurbano.data.datasources.ReadPostDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.data.repositories.PostsRepositoryImpl
import com.haurbano.data.retrofit.PostsService
import com.haurbano.data.retrofit.RetrofitClient
import com.haurbano.domain.repositories.PostsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<PostsService> { RetrofitClient.create(PostsService::class.java) }
    single { ReadPostDataSource(androidContext()) }

    factory { PostsMapper() }
    factory<PostsRepository> { PostsRepositoryImpl(get(), get(), get()) }
}