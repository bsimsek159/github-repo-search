package com.bsimsek.githubreposearch.core.extension

fun <T> lazyThreadSafetyNone(initializer: () -> T):
        Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
