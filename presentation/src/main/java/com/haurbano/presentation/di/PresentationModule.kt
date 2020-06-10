package com.haurbano.presentation.di

import com.haurbano.presentation.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { PostsViewModel(get()) }
}