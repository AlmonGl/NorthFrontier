package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.ScrollState
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
import com.example.korlearn2.database.nextMonth
import kotlinx.coroutines.launch

@Composable
fun MonthScreen(navController: NavController,
                lifecycleScope: LifecycleCoroutineScope,
                viewModel: LocationViewModel,
                dao: LocationsDao,
                context: Context
) {

    BackGround(id = 1)
    Column(modifier = Modifier.verticalScroll(ScrollState(0))) {




        Text(text = " ${viewModel.currentDate}\n Another month, passed...\n Locations № ${viewModel.locAttacked} has been attacked by barbarians...")
        Button(onClick = { navController.navigate(Screen.Locations.route) }) {
            Text(text = "Let me see province map")
        }
        if (viewModel.rulersActionsLastMonth!="") {
            Text(text = "Local rulers sent the following reports this month:")
            Text(text = viewModel.rulersActionsLastMonth)

        }
        Text(text = viewModel.locationWithCivDec)
        Text(text = viewModel.locationWithMilDec)
        if (viewModel.numberOfRequests == 0) {
            
            Button(onClick = {
                viewModel.rulersActionsMonthBeforeLast = viewModel.rulersActionsLastMonth
                viewModel.rulersActionsLastMonth=""
                navController.navigate(Screen.MainInfo.route)

            }) {
                Text(text = "Leave me for now")
            }
        }
    }
}