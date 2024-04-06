package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.R

import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.database.generateAll

@Composable
fun StartScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    val im = context.getDrawable(R.drawable.im2)
    BackGround(id = 1)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly

        )
        {
        Text(
            text = "Continue",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            modifier = Modifier.clickable {
                navController.navigate(Screen.MainInfo.route)
            }

        )
            Text(
                text = "New game",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                modifier = Modifier.clickable {
                    generateAll(lifecycleScope, dao, viewModel, context)
                    viewModel.text1 = ""
                    viewModel.selectedLocationId=-1
                    navController.navigate(Screen.MainInfo.route)
                }

            )
    }
}