package com.haurbano.data.di

import com.haurbano.data.retrofit.PostsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServicesModule {

    private const val BASE_URL = "https://www.reddit.com/r/all/"

    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesPostService(
        retrofit: Retrofit
    ): PostsService {
        return retrofit.create(PostsService::class.java)
    }
}