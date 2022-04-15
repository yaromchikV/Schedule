package com.yaromchikv.domain.common

private sealed class Result<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(error: Throwable) : Result<T>(error = error)
}