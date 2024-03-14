package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.generateAll
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
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
                    navController.navigate(Screen.MainInfo.route)
                }

            )
    }
}