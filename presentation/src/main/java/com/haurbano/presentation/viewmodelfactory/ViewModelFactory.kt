package com.haurbano.presentation.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haurbano.domain.usecases.CheckPostAsReadUseCase
import com.haurbano.domain.usecases.DismissPostUseCase
import com.haurbano.domain.usecases.GetPostUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val checkPostAsReadUseCase: CheckPostAsReadUseCase,
    private val dismissPostUseCase: DismissPostUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetPostUseCase::class.java,
            CheckPostAsReadUseCase::class.java,
            DismissPostUseCase::class.java
        ).newInstance(
            getPostUseCase,
            checkPostAsReadUseCase,
            dismissPostUseCase
        )
    }
}