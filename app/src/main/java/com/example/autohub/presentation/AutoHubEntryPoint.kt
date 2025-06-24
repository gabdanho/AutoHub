package com.example.autohub.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autohub.presentation.navigation.AutoHubNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AutoHubEntryPoint(
    navController: NavHostController = rememberNavController()
) {
    AutoHubNavGraph(navController = navController)
}