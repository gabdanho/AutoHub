package com.example.autohub.data.firebase.model

import com.example.autohub.domain.model.result.FirebaseResult
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeFirebaseCall(apiCall: suspend () -> T): FirebaseResult<T> {
    return try {
        FirebaseResult.Success(apiCall())
    } catch (e: HttpException) {
        FirebaseResult.ServerError(
            message = e.toString(),
            errorCode = e.code()
        )
    } catch (e: SocketTimeoutException) {
        FirebaseResult.TimeoutError(message = e.toString())
    } catch (e: IOException) {
        FirebaseResult.ConnectionError(message = e.toString())
    } catch (e: Exception) {
        FirebaseResult.UnknownError(message = e.toString())
    } catch (e: TimeoutCancellationException) {
        FirebaseResult.TimeoutError(message = "The server response time has been exceeded")
    }
}