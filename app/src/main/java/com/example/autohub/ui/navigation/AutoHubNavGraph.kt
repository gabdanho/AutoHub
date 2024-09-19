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
import com.example.autohub.data.CarAd
import com.example.autohub.data.User
import com.example.autohub.ui.account.AccountSettings
import com.example.autohub.ui.account.AuthUserAccountScreen
import com.example.autohub.ui.ads.AdCreateScreen
import com.example.autohub.ui.ads.AdsMainScreen
import com.example.autohub.ui.login.LoginScreen
import com.example.autohub.ui.login.RegisterScreen
import com.example.autohub.ui.messenger.MessengerScreen
import com.example.autohub.utils.getCurrentUserAds
import com.example.autohub.utils.getUserData
import com.example.autohub.utils.uploadAdsImagesToFirebase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AutoHubNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val isLoginSuccess = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val fsAuth = Firebase.auth
    val fsStore = Firebase.firestore

    val user = remember { mutableStateOf(User()) }
    if (fsAuth.currentUser != null) {
        getUserData(fsAuth.currentUser?.uid!!) { data ->
            user.value = data
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (fsAuth.currentUser == null) "LoginScreen" else "AdsMainScreen",
        modifier = modifier
    ) {
        composable(route = "LoginScreen") {
            val isLoading = remember { mutableStateOf(false) }
            val isShowSendEmailText = remember { mutableStateOf(false) }

            LoginScreen(
                onRegisterClick = { navController.navigate(route = "RegisterScreen") },
                onLoginClick = { email, password ->
                    isLoading.value = true
                    fsAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            isLoading.value = false
                            isShowSendEmailText.value = false
                            Toast.makeText(context, "Ошибка: ${task.exception?.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
                        } else {
                            if (!fsAuth.currentUser!!.isEmailVerified) {
                                Toast.makeText(context, "Аккаунт не верифицирован. Проверьте почту", Toast.LENGTH_SHORT).show()
                                isShowSendEmailText.value = true
                                fsAuth.signOut()
                                isLoading.value = false
                            } else {
                                isLoginSuccess.value = true
                                navController.navigate("AdsMainScreen") {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true

                                    isLoading.value = false
                                    isShowSendEmailText.value = false
                                }
                            }
                        }
                    }
                },
                isProgressBarWork = isLoading.value,
                isShowSendEmailText = isShowSendEmailText.value
            )
        }
        composable(route = "RegisterScreen") {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { email, password, user ->
                    fsAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userUID = fsAuth.currentUser?.uid
                            val docReference = fsStore.collection("users").document(userUID!!)
                            docReference.set(user).addOnSuccessListener {
                                Log.d("USER_INFO", "Created document for user: $userUID")
                            }

                            fsAuth.currentUser!!.sendEmailVerification()
                            Toast.makeText(context, "Для завершения регистрации перейдите по ссылке, отправленной на Вашу почту", Toast.LENGTH_SHORT).show()

                            navController.navigate(route = "LoginScreen") {
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
            val ads = remember { mutableStateOf(listOf<CarAd>()) }

            getCurrentUserAds(fsAuth.currentUser?.uid!!) { ads.value = it }

            AuthUserAccountScreen(
                yourAds = ads.value,
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
                onChangeInfoClick = { navController.navigate("AccountSettings") },
                onAdCreateClick = { navController.navigate("AdCreateScreen") }
            )
        }
        composable(route = "AccountSettings") {
            AccountSettings(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
        composable(route = "AdCreateScreen") {
            AdCreateScreen(
                onBackButtonClick = { navController.popBackStack() },
                onCreateAdClick = { carAd, images ->
                    val userUID = fsAuth.currentUser?.uid!!
                    val adReference = "${userUID}_${System.currentTimeMillis()}"
                    val docReference = fsStore
                        .collection("ads")
                        .document(adReference)

                    val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
                    val currentDate = dateFormat.format(Date())

                    docReference
                        .set(carAd.copy(
                            adId = adReference,
                            userUID = userUID,
                            city = user.value.city,
                            dateAdPublished = currentDate))
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("AD_INFO", "Ad is created: ${carAd.brand} ${carAd.model}")
                            } else {
                                Log.d("AD_INFO", "Error: ${task.exception?.message ?: "unknown"}")
                            }
                    }

                    uploadAdsImagesToFirebase(context, images, adReference)

                    navController.navigate(route = "AuthUserAccountScreen")
                }
            )
        }
    }
}