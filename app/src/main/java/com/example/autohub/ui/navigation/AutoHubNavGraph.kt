package com.example.autohub.ui.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.autohub.ui.account.AuthUserAccountScreen
import com.example.autohub.ui.ads.AdsMainScreen
import com.example.autohub.ui.login.LoginScreen
import com.example.autohub.ui.login.RegisterScreen
import com.example.autohub.ui.messenger.MessengerScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun AutoHubNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fsAuth = Firebase.auth
    val fsStore = Firebase.firestore

    NavHost(
        navController = navController,
        startDestination = if (fsAuth.currentUser == null) "LoginScreen" else "AdsMainScreen",
        modifier = modifier
    ) {
        composable(route = "LoginScreen") {
            val isLoading = remember { mutableStateOf(false) }

            LoginScreen(
                onRegisterClick = { navController.navigate(route = "RegisterScreen") },
                onLoginClick = { email, password ->
                    isLoading.value = true
                    fsAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            isLoading.value = false
                            Toast.makeText(context, "Ошибка: ${task.exception?.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
                        } else {
                            navController.navigate("AdsMainScreen") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                },
                isProgressBarWork = isLoading.value
            )
        }
        composable(route = "RegisterScreen") {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { email, password, user ->
                    fsAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show()

                            val userUID = fsAuth.currentUser?.uid
                            val docReference = fsStore.collection("users").document(userUID!!)
                            docReference.set(user).addOnSuccessListener {
                                Log.d("USER_INFO", "Created document for user: $userUID\n$user")
                            }

                            navController.navigate(route = "AdsMainScreen") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        } else {
                            Toast.makeText(context, "Ошибка: ${task.exception?.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
        composable(route = "AdsMainScreen") {
            AdsMainScreen(
                adsList = listOf(),
                onAdListClick = { },
                onMessageClick = { navController.navigate("MessengerScreen") },
                onAccountClick = { navController.navigate("AuthUserAccountScreen") }
            )
        }
        composable(route = "MessengerScreen") {
            MessengerScreen(
                buyers = listOf(),
                onMessageClick = { },
                onAccountClick = { navController.navigate("AuthUserAccountScreen") },
                onAdListClick = { navController.navigate("AdsMainScreen") },
                onAnswerClick = { }, // TODO()
            )
        }
        composable(route = "AuthUserAccountScreen") {
            AuthUserAccountScreen(
                yourAds = listOf(),
                onMessageClick = { navController.navigate("MessengerScreen") },
                onAccountClick = { },
                onAdListClick = { navController.navigate("AdsMainScreen") },
                onAdClick = { }, // TODO()
                onSignOutClick = {
                    fsAuth.signOut()
                    navController.navigate("LoginScreen") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onChangeInfoClick = { } // TODO()
            )
        }
    }
}