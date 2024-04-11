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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

@Composable
fun MonthScreen(navController: NavController,
                lifecycleScope: LifecycleCoroutineScope,
                viewModel: LocationViewModel,
                dao: LocationsDao,
                context: Context
) {

    BackGround(id = 1)
    Column(modifier = Modifier.verticalScroll(ScrollState(0))) {




        Text(text = " Year ${viewModel.currentYear}, Month ${viewModel.currentMonth}\n Another month, passed...\n Locations № ${viewModel.locAttacked} has been attacked by barbarians...")
        Button(onClick = { navController.navigate(Screen.Locations.route) }) {
            Text(text = "Let me see province map")
        }





        Text(text = "\n"+viewModel.raidsReport)
        Text(text = viewModel.thisTurnReports)

        if (viewModel.currentMonth != 1) {
            
            Button(onClick = {

                viewModel.raidsReportBefore = viewModel.raidsReport
                viewModel.raidsReport = "Rumors about barbarian raids:"
                navController.navigate(Screen.MainInfo.route)

            }) {
                Text(text = "Leave me for now")
            }
        }
        Text(text = viewModel.listOfLocsToTax.toString())
        if (viewModel.currentMonth==1) {
            Button(onClick = {

                viewModel.raidsReportBefore = viewModel.raidsReport
                viewModel.raidsReport = "Rumors about barbarian raids:"
                lifecycleScope.launch {
                    viewModel.listOfLocsToTax = emptyList<Int>().toMutableList()
                    viewModel.listOfLocsToTax = dao.getNotDepletedLocationsIds().toMutableList()
                    viewModel.currentTaxesLocId = viewModel.listOfLocsToTax[0]
                    viewModel.listOfLocsToTax.remove(viewModel.currentTaxesLocId)
                    val location = dao.getLocationById(viewModel.currentTaxesLocId)
                    viewModel.currentLocationTotalTaxes = location.taxesLastYear
                    viewModel.civUpkeepValue = location.civUpkeep
                    viewModel.milUpkeepValue = location.milUpkeep
                    viewModel.foodUpkeepValue = location.foodUpkeep
                    viewModel.civUpValue = location.civUpFunds
                    viewModel.milUpValue = location.milUpFunds
                    viewModel.text1 = location.showFogData()
                    delay(500)
                }
                navController.navigate(Screen.LocationTaxes.route)

            }) {
                Text(text = "Let's see location reports")
            }
        }
    }
}