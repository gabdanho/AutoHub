package com.example.autohub.ui.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.autohub.data.model.ad.CarAd
import com.example.autohub.data.model.user.User
import com.example.autohub.data.model.user.UserStatus
import com.example.autohub.ui.ChatViewModel
import com.example.autohub.ui.account.AccountSettings
import com.example.autohub.ui.account.AnotherAccountScreen
import com.example.autohub.ui.account.AuthUserAccountScreen
import com.example.autohub.ui.ads.AdCreateScreen
import com.example.autohub.ui.ads.AdScreen
import com.example.autohub.ui.ads.AdsMainScreen
import com.example.autohub.ui.ads.FiltersScreen
import com.example.autohub.ui.login.LoginScreen
import com.example.autohub.ui.login.RegisterScreen
import com.example.autohub.ui.messenger.ChattingScreen
import com.example.autohub.ui.messenger.MessengerScreen
import com.example.autohub.utils.changeUserStatus
import com.example.autohub.utils.createAd
import com.example.autohub.utils.getAllAds
import com.example.autohub.utils.getAuthUserUID
import com.example.autohub.utils.getCurrentUserAds
import com.example.autohub.utils.getUserData
import com.example.autohub.utils.loginUser
import com.example.autohub.utils.registerUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AutoHubNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val fsAuth = Firebase.auth
    val adsOnMainScreen = remember { mutableStateOf(listOf<CarAd>()) }
    val ads = remember { mutableStateOf(listOf<CarAd>()) }
    val selectedAd = remember { mutableStateOf(CarAd()) }
    val userData = remember { mutableStateOf(User()) }
    val buyerUID = remember { mutableStateOf("") }
    val authUserData = remember { mutableStateOf(User()) }
    val authUser = fsAuth.currentUser
    val viewModel: ChatViewModel = viewModel()
    val filters = remember { mutableStateOf(mapOf<String, String>()) }

    if (authUser != null) {
        getUserData(authUser.uid) { data ->
            authUserData.value = data
        }
    }
    getAllAds { dataList -> adsOnMainScreen.value = dataList }

    NavHost(
        navController = navController,
        startDestination = if (authUser == null) ScreenRoutes.LOGIN.name else ScreenRoutes.ALL_ADS.name,
        modifier = modifier
    ) {
        composable(route = ScreenRoutes.LOGIN.name) {
            val isLoading = remember { mutableStateOf(false) }
            val isShowSendMessageEmailText = remember { mutableStateOf(false) }

            LoginScreen(
                onRegisterClick = { navController.navigate(route = ScreenRoutes.REGISTER.name) },
                onLoginClick = { email, password ->
                    loginUser(
                        navController = navController,
                        context = context,
                        email = email,
                        password = password,
                        changeLoadingState = { isLoading.value = it },
                        changeShowTextToSendMessageToEmail = { isShowSendMessageEmailText.value = it }
                    )
                },
                isProgressBarWork = isLoading.value,
                isShowSendEmailText = isShowSendMessageEmailText.value
            )
        }

        composable(route = ScreenRoutes.REGISTER.name) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { email, password, user ->
                    registerUser(
                        email = email,
                        password = password,
                        user = user.copy(image = "https://i.pinimg.com/736x/4c/85/31/4c8531dbc05c77cb7a5893297977ac89.jpg"),
                        navController = navController,
                        context = context
                    )
                }
            )
        }

        composable(route = ScreenRoutes.ALL_ADS.name) {
            AdsMainScreen(
                adsList = adsOnMainScreen.value,
                filters = filters.value,
                onAdListClick = {
                    getAllAds(filters.value) { dataList -> adsOnMainScreen.value = dataList }
                },
                onAdClick = { ad ->
                    selectedAd.value = ad
                    navController.navigate(route = ScreenRoutes.AD_INFO.name)
                },
                onMessageClick = { navController.navigate(ScreenRoutes.MESSENGER.name) },
                onAccountClick = { navController.navigate(ScreenRoutes.AUTH_USER_ACCOUNT.name) },
                onFiltersClick = { navController.navigate(ScreenRoutes.FILTERS.name) },
                onDoneClick = {
                    adsOnMainScreen.value = it
                }
            )
        }

        composable(route = ScreenRoutes.MESSENGER.name) {
            MessengerScreen(
                viewModel = viewModel,
                onMessageClick = { },
                onAccountClick = { navController.navigate(ScreenRoutes.AUTH_USER_ACCOUNT.name) },
                onAdListClick = { navController.navigate(ScreenRoutes.ALL_ADS.name) },
                onAnswerClick = {
                    println(it)
                    buyerUID.value = it
                    navController.navigate(route = ScreenRoutes.CHATTING.name)
                }
            )
        }

        composable(route = ScreenRoutes.AUTH_USER_ACCOUNT.name) {
            getCurrentUserAds(getAuthUserUID()) { ads.value = it }

            AuthUserAccountScreen(
                yourAds = ads.value,
                onMessageClick = { navController.navigate(ScreenRoutes.MESSENGER.name) },
                onAccountClick = { },
                onAdListClick = { navController.navigate(ScreenRoutes.ALL_ADS.name) },
                onAdClick = { carAd ->
                    selectedAd.value = carAd
                    navController.navigate(route = ScreenRoutes.AD_INFO.name)
                },
                onSignOutClick = {
                    changeUserStatus(UserStatus.OFFLINE)
                    fsAuth.signOut()
                    navController.navigate(ScreenRoutes.LOGIN.name) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onChangeInfoClick = { navController.navigate(ScreenRoutes.ACCOUNT_SETTINGS.name) },
                onAdCreateClick = { navController.navigate(ScreenRoutes.AD_CREATE.name) }
            )
        }

        composable(route = ScreenRoutes.ACCOUNT_SETTINGS.name) {
            AccountSettings(
                onBackButtonClick = { navController.popBackStack() }
            )
        }

        composable(route = ScreenRoutes.AD_CREATE.name) {
            AdCreateScreen(
                onBackButtonClick = { navController.popBackStack() },
                onCreateAdClick = { carAd, images ->
                    createAd(carAd, authUserData.value, context, images, navController)
                }
            )
        }

        composable(route = ScreenRoutes.AD_INFO.name) {
            getUserData(selectedAd.value.userUID) {
                userData.value = it
            }
            buyerUID.value = selectedAd.value.userUID

            AdScreen(
                user = userData.value,
                carAd = selectedAd.value,
                onBackButtonClick = { navController.popBackStack() },
                onCallClick = { startCalling(context, userData.value.phoneNumber) },
                onMessageClick = { navController.navigate(ScreenRoutes.CHATTING.name) },
                onUserClick = { navController.navigate(ScreenRoutes.ANOTHER_ACCOUNT.name) }
            )
        }

        composable(route = ScreenRoutes.ANOTHER_ACCOUNT.name) {
            getUserData(buyerUID.value) {
                userData.value = it
            }

            getCurrentUserAds(buyerUID.value) {
                ads.value = it
            }

            AnotherAccountScreen(
                user = userData.value,
                ads = ads.value,
                onAdClick = {
                    selectedAd.value = it
                    navController.navigate(ScreenRoutes.AD_INFO.name)
                },
                onBackButtonClick = { navController.popBackStack() },
                onCallClick = { startCalling(context, userData.value.phoneNumber) },
                onWriteClick = { navController.navigate(ScreenRoutes.CHATTING.name) }
            )
        }

        composable(route = ScreenRoutes.CHATTING.name) {
            ChattingScreen(
                viewModel = viewModel,
                buyerUID = buyerUID.value,
                onBuyerClick = {
                    uid -> buyerUID.value = uid
                    navController.navigate(ScreenRoutes.ANOTHER_ACCOUNT.name)
                }
            )
        }

        composable(route = ScreenRoutes.FILTERS.name) {
            FiltersScreen(
                filters = filters.value,
                onBackButtonClick = { navController.popBackStack() },
                onConfirmClick = {
                    filters.value = it
                    getAllAds(filters.value) { dataList -> adsOnMainScreen.value = dataList }
                    navController.popBackStack()
                },
                onClearFiltersClick = {
                    filters.value = it
                    getAllAds { dataList -> adsOnMainScreen.value = dataList }
                    navController.popBackStack()
                }
            )
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun startCalling(context: Context, phoneNumber: String) {
    val callIntent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null))

    if (callIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(callIntent)
    }
}