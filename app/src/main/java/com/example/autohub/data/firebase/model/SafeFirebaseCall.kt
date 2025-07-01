package com.example.autohub.data.firebase.model

import com.example.autohub.domain.model.Result
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeFirebaseCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: HttpException) {
        Result.ServerError(
            message = e.toString(),
            errorCode = e.code()
        )
    } catch (e: SocketTimeoutException) {
        Result.TimeoutError(message = e.toString())
    } catch (e: IOException) {
        Result.ConnectionError(message = e.toString())
    } catch (e: Exception) {
        Result.UnknownError(message = e.toString())
    }
}