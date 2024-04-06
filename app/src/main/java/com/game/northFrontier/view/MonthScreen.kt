package com.game.northFrontier.view

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
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao

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

            Text(text = "Local rulers sent the following reports this month:")
            Text(text = viewModel.rulersActionsCivUp +"\n"+ viewModel.rulersActionsMilUp)


        Text(text = viewModel.locationWithCivDec)
        Text(text = viewModel.locationWithMilDec)
        Text(text = "\n"+viewModel.raidsReport)
        Text(text = viewModel.thisTurnReports)
        if (viewModel.numberOfRequests == 0) {
            
            Button(onClick = {
                viewModel.rulersActionsMonthBeforeLast = viewModel.rulersActionsCivUp +"\n"+ viewModel.rulersActionsMilUp
                viewModel.rulersActionsMilUp="Mil. level increased in: "
                viewModel.rulersActionsCivUp="Civ. level increased in: "
                viewModel.raidsReportBefore = viewModel.raidsReport
                viewModel.raidsReport = "Rumors about barbarian raids:"
                navController.navigate(Screen.MainInfo.route)

            }) {
                Text(text = "Leave me for now")
            }
        }
    }
}