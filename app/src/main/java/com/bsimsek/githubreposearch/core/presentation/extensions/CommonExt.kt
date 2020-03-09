package com.bsimsek.githubreposearch.core.presentation.extensions

fun <T> lazyThreadSafetyNone(initializer: () -> T):
        Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
