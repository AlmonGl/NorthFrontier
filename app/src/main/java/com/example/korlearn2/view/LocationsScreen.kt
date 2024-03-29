package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.BackGround
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao

@Composable
fun LocationsScreen(navController: NavController,
                    lifecycleScope: LifecycleCoroutineScope,
                    viewModel: LocationViewModel,
                    dao: LocationsDao,
                    context: Context
) {
    BackGround(id = 3)
    Column {


        Button(onClick = { navController.navigate(Screen.Month.route) }) {
            Text(text = "Back")
        }
        LocationMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao)
        Text(text = viewModel.text1, modifier = Modifier.verticalScroll(ScrollState(0)))
    }
}