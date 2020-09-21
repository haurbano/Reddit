package com.haurbano.data.di

import com.haurbano.data.repositories.PostsRepositoryImpl
import com.haurbano.domain.repositories.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}