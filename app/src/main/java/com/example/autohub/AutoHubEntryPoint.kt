package com.example.autohub

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autohub.ui.navigation.AutoHubNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AutoHubEntryPoint(
    navController: NavHostController = rememberNavController()
) {
    AutoHubNavGraph(navController = navController)
}