package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao

@Composable
fun MapScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Button(onClick = { navController.navigate(Screen.MainInfo.route) }) {
            Text(text = "Back", fontSize = 25.sp)
        }
        Text(
            text = "Map1",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,

            )
    }
}