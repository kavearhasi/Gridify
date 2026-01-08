package com.example.gridify.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object ProductionDispatcherProvider : DispatcherProvider {
    override fun provideUiContext(): CoroutineContext {
        return Dispatchers.Main
    }
    override fun provideIoContext(): CoroutineContext {
        return Dispatchers.IO
    }
}