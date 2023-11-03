package com.example.practiceingeneral.di

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Provider

class NoteViewModelFactory @Inject constructor(
    creators : Map<Class<out ViewModel>,
    @JvmSuppressWildcards Provider<ViewModel>>
) : BaseViewModelFactory(creators)