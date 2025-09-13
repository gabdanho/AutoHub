package com.example.autohub.data.firebase.model

import com.example.autohub.domain.HandledException
import com.example.autohub.domain.model.result.FirebaseResult
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeFirebaseCall(
    timeoutMillis: Long = 10000,
    apiCall: suspend () -> T,
): FirebaseResult<T> {
    return try {
        val result = withTimeout(timeoutMillis) {
            apiCall()
        }
        FirebaseResult.Success(data = result)
    } catch (e: HttpException) {
        FirebaseResult.Error.ServerError(
            serverMessage = e.toString(),
            errorCode = e.code()
        )
    } catch (e: SocketTimeoutException) {
        FirebaseResult.Error.TimeoutError(timeoutMessage = e.toString())
    } catch (e: IOException) {
        FirebaseResult.Error.ConnectionError(connectionMessage = e.toString())
    } catch (e: Exception) {
        FirebaseResult.Error.UnknownError(unknownMessage = e.toString())
    } catch (e: TimeoutCancellationException) {
        FirebaseResult.Error.TimeoutError(timeoutMessage = e.toString())
    } catch (e: HandledException) {
        FirebaseResult.Error.HandledError(tag = e.tag)
    }
}