package com.arysugiarto.attendence.util

import android.widget.Button
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.util.animatedtext.ProgressParams
import com.arysugiarto.attendence.util.animatedtext.hideProgress
import com.arysugiarto.attendence.util.animatedtext.showProgress

/**
 * Return [Gson] Constructor
 */
val gson get() = Gson()

/**
 * Get Value From JSONObject Field and return Empty when field not recognized
 */
fun JSONObject.getFieldOrNull(field: String): String =
    if (has(field)) getString(field) else ""

/**
 * Convert any Value within pair of Field and Value to JsonObject
 */
inline fun Gson.toJsonElement(source: HashMap<String, Any>.() -> Unit): JsonElement {
    val hashMap = HashMap<String, Any>()
    hashMap.source()

    return toJsonTree(hashMap)
}

/**
 * Convert any Value within pair of Field and Value to JsonString
 */
inline fun Gson.toJsonString(source: HashMap<String, Any>.() -> Unit): String {
    val hashMap = HashMap<String, Any>()
    hashMap.source()

    return toJson(hashMap)
}

/**
 * Convert [String] as JsonString to [T] object
 */
inline fun <reified T> Gson.fromJson(source: String): T {
    val type = object : TypeToken<T>() {}.type
    return fromJson(source, type)
}


/**
 * Convert [ResponseBody.charStream] to [T] object
 */
inline fun <reified T : Any?> ResponseBody.parse(): T? {
    val classType = object : TypeToken<T>() {}.type
    return Gson().fromJson(charStream(), classType)
}

/**
 * Convert [Int] from error Body to Custom Error Message
 */
fun Int.handleCode() = when (this) {
    401 -> "Akses tidak ditemukan, Harap login kembali"
    403 -> "Akses tidak ditemukan, Harap login kembali"
    404 -> "Halaman tidak ditemukan"
    422 -> "Query tidak ditemukan"
    else -> "Mohon maaf terjadi kesalahan, tunggu beberapa saat untuk mencoba kembali"
}

fun <T> Result<T>.attachLoadingButton(
    button: Button,
    endLoadingText: String,
    params: ProgressParams.() -> Unit
) {
    button.isEnabled = this !is Result.Loading

    when(this) {
        is Result.Loading -> button.showProgress(params)
        else -> button.hideProgress(endLoadingText)
    }
}

// Exception Handler
private fun Throwable.handleException() = when (this) {
    is IOException -> "Failed to read response!"
    is SocketTimeoutException -> "Timeout!"
    is UnknownHostException -> "Check your internet connection!"
    else -> "An error occurred!"
}

/**
 * Convert [Exception]/[Throwable]  to Custom Error Message
 */
val Throwable.parsedMessage get() = handleException()

/**
 * @return [Flow] of [Result] object with 3 Step of Result Handling
 * @param responseBody Used for [Response] callback value from ApiCallback
 */
inline fun <reified T> flowResponse(
    handleError: Boolean = true,
    crossinline errorMessage: (String) -> String = { emptyString },
    crossinline responseBody: suspend () -> Response<T>
) = flow<Result<T>> {
    val response = responseBody.invoke()
    val body= response.body()

    if(response.isSuccessful) {
        emit(Result.success((body)))
    } else {
        emit(
            Result.error(
                message = "Error",
//                data = "error",
                code = response.code()
            )
        )
    }}
    .onStart { emit(Result.loading()) }
    .flowOn(Dispatchers.IO)
    .retryWhen { cause, attempt ->
        attempt <= 3 && cause is SocketTimeoutException
    }
    .catch { throwable ->
        Timber.e("Error @${T::class.java} : $throwable")
        emit(Result.error<T>(throwable.parsedMessage, code = 500))
    }