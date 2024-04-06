package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao

@Composable
fun EndScreen(navController: NavController,
              viewModel: LocationViewModel,

) {
    BackGround(id = 6)
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
        Text(text = viewModel.endReason)
        Button(onClick = {
            navController.navigate(Screen.Start.route)

        }) {
            Text(text = "Ok")
        }
    }

}