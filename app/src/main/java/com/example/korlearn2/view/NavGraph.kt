package com.example.korlearn2.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route

        )
    {
        composable(
            route = Screen.Start.route
        )
        {
            StartScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.Map.route
        )
        {
            MapScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.MainInfo.route
        )
        {
            MainInfoScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.SquadsAndSpies.route
        )
        {
            SquadsAndSpiesScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.Month.route
        )
        {
            MonthScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.Locations.route
        )
        {
            LocationsScreen(navController,lifecycleScope,viewModel,dao,context)
        }
    }

}