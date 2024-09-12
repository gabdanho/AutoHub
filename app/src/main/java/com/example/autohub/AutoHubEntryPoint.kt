package com.example.autohub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.autohub.ui.navigation.AutoHubNavGraph

@Composable
fun AutoHubEntryPoint(
    navController: NavHostController = rememberNavController()
) {
    AutoHubNavGraph(navController)
}