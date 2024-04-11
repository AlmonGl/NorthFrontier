package com.game.northFrontier.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao

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
            viewModel.text1=""
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

        composable(
            route = Screen.SquadManager.route
        )
        {

            SquadManagingScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.End.route
        )
        {
            EndScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.RulersManaging.route
        )
        {
            RulersManagingScreen(navController,lifecycleScope,viewModel,dao,context)
        }
        composable(
            route = Screen.LocationTaxes.route
        )
        {
            LocationTaxesScreen(navController,lifecycleScope,viewModel,dao)
        }
    }

}

