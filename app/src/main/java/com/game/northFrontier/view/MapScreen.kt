package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.view.BackButton
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao

@Composable
fun MapScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    BackGround(4)
    BackButton(navController)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {

        Text(
            text = "Map1",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,

            )
    }
}