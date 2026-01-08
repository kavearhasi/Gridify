package com.example.gridify.common

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    fun provideUiContext(): CoroutineContext
    fun provideIoContext(): CoroutineContext
}