package com.example.autohub.data.firebase.model

import android.util.Log
import com.example.autohub.domain.model.result.FirebaseResult
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeFirebaseCall(apiCall: suspend () -> T): FirebaseResult<T> {
    return try {
        val result = apiCall()
        Log.i("SafeFirebaseCall", apiCall().toString())
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
        FirebaseResult.Error.TimeoutError(timeoutMessage = "The server response time has been exceeded")
    }
}