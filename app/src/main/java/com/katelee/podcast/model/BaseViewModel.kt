package com.katelee.podcast.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * Created by Kate on 2020-04-15.
 */

open class BaseViewModel : ViewModel() {
    fun <T> request(
        onError: (error: Throwable) -> Unit = {},
        execute: suspend CoroutineScope.() -> T
    ) {
        viewModelScope.launch(errorHandler { onError.invoke(it) }) {
            launch(Dispatchers.IO) {
                execute()
            }
        }
    }

    private fun errorHandler(onError: (error: Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            onError.invoke(throwable)
        }
    }
}