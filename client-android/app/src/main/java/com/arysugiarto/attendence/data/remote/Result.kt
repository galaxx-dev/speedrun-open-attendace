package com.arysugiarto.attendence.data.remote

sealed class Result <T> {
    object Nothing: Result<kotlin.Nothing>()
    class Loading<T> : Result<T>()
    class Success<T>(val data: T?) : Result<T>()
    class Error<T>(
        val message: String,
//        val data: X? = null,
        val status_code: Int = 0
    ): Result<T>()

    companion object {
        val nothing = Nothing
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T?) = Success(data)
        fun <T> error(
            message: String,
//            data: X? = null,
            code: Int = 0
        ) = Error<T>(message)
    }
}