package com.example.autohub.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.autohub.data.User
import com.example.autohub.data.UserStatus
import com.example.autohub.ui.navigation.ScreenRoutes
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

fun getAuthUserUID(): String {
    return Firebase.auth.currentUser?.uid ?: ""
}

fun loginUser(
    navController: NavHostController,
    context: Context,
    email: String,
    password: String,
    changeLoadingState: (Boolean) -> Unit,
    changeShowTextToSendMessageToEmail: (Boolean) -> Unit,
) {
    val fbAuth = Firebase.auth

    changeLoadingState(true)
    fbAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            val authUser = fbAuth.currentUser

            if (!task.isSuccessful) {
                changeLoadingState(false)
                changeShowTextToSendMessageToEmail(false)
                Toast.makeText(
                    context,
                    "Ошибка: ${task.exception?.message ?: "unknown"}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (authUser != null) {
                    if (!authUser.isEmailVerified) {
                        Toast.makeText(
                            context,
                            "Аккаунт не верифицирован. Проверьте почту",
                            Toast.LENGTH_SHORT
                        ).show()
                        changeShowTextToSendMessageToEmail(true)
                        fbAuth.signOut()
                        changeLoadingState(false)
                    } else {
                        changeUserStatus(UserStatus.ONLINE)
                        changeLoadingState(true)
                        navController.navigate(ScreenRoutes.ALL_ADS.name) {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true

                            changeLoadingState(false)
                            changeShowTextToSendMessageToEmail(false)
                        }
                    }
                }
            }
        }
}

fun registerUser(
    email: String,
    password: String,
    context: Context,
    user: User,
    navController: NavHostController
) {
    val fbAuth = Firebase.auth
    val fbStore = Firebase.firestore

    fbAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val authUser = fbAuth.currentUser
                val userUID = getAuthUserUID()
                val docReference = fbStore.collection("users").document(userUID)
                docReference.set(user).addOnSuccessListener {
                    Log.d("USER_INFO", "Created document for user: $userUID")
                }

                authUser?.sendEmailVerification().also {
                    Toast.makeText(
                        context,
                        "Для завершения регистрации перейдите по ссылке, отправленной на Вашу почту",
                        Toast.LENGTH_SHORT
                    ).show()
                } ?: Toast.makeText(
                    context,
                    "Ошибка в отправке письма для верификации",
                    Toast.LENGTH_SHORT
                ).show()

                fbAuth.signOut()
                navController.navigate(route = ScreenRoutes.LOGIN.name) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else {
                Toast.makeText(
                    context,
                    "Ошибка: ${task.exception?.message ?: "unknown"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

fun changeUserStatus(status: UserStatus) {
    val fbAuth = Firebase.auth

    if (fbAuth.currentUser != null) {
        Firebase.firestore
            .collection("users")
            .document(getAuthUserUID())
            .update("status", status.name)
    }
}