package com.example.smarthome.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smarthome.constants.AppStructure
import com.example.smarthome.model.HomeViewModel

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = AppStructure.HOME_ROUTE
    ) {
        composable(AppStructure.HOME_ROUTE) {
            HomeScreen(viewModel = viewModel)
        }
        composable(AppStructure.DEVICES_ROUTE) {
            DevicesScreen(viewModel = viewModel)
        }
        composable(AppStructure.LIGHTING_ROUTE) {
            LightingScreen(viewModel = viewModel)
        }
        composable(AppStructure.STATS_ROUTE) {
            StatsScreen(viewModel = viewModel)
        }
        composable(AppStructure.SYSTEMS_ROUTE) {
            SystemsScreen()
        }
    }
}