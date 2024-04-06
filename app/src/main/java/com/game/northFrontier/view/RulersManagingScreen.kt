package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.ui.theme.MosaicBack
import com.game.northFrontier.ui.theme.TileBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RulersManagingScreen(navController: NavController,
                         lifecycleScope: LifecycleCoroutineScope,
                         viewModel: LocationViewModel,
                         dao: LocationsDao,
                         context: Context
) {
    BackGround(id = 2)
    Column {
        BackButton(navController = navController)
        Spacer(modifier = Modifier.height(5.dp))
        LocationMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao)


        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
        if (viewModel.selectedRuler!=-1) {
            lifecycleScope.launch {
                val loc = dao.getLocationById(viewModel.selectedLocationId)
                loc.rulerName = viewModel.selectedRulerName
                dao.updateLocation(loc)
                refreshRulerManager(lifecycleScope,viewModel,dao)
                delay(1500)
                navController.navigate(Screen.RulersManaging.route)
            }
        }
        }) {
            Text(text = "Appoint ${viewModel.selectedRulerName} to ${viewModel.selectedLocationId}")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Choose ruler:")
        Spacer(modifier = Modifier.height(5.dp))
        RulerMosaic(
            lifecycleScope = lifecycleScope,
            viewModel = viewModel,
            dao = dao,
            rulerList = viewModel.rulersFreeIdList
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Ruler info: \n${viewModel.selectedRulerInfo}", fontSize = 25.sp, modifier = Modifier.verticalScroll(
            ScrollState(0)
        ) )
    }




}

fun refreshRulerManager(
                           lifecycleScope: LifecycleCoroutineScope,
                           viewModel: LocationViewModel,
                           dao: LocationsDao
                           ) {
    lifecycleScope.launch {
        viewModel.selectedRuler=-1
        viewModel.selectedRulerInfo=""
        viewModel.selectedRulerName=""
        viewModel.rulersFreeIdList = dao.getFreeLocalRulersId(dao.getRulersOnLocations())
    }
}

@Composable
fun RulerMosaic(lifecycleScope: LifecycleCoroutineScope,
                viewModel: LocationViewModel,
                dao: LocationsDao,
                rulerList: List<Int>) {
    LazyVerticalGrid(columns = GridCells.Fixed(6), modifier = Modifier
        .background(MosaicBack)
        .fillMaxWidth())
    {
        rulerList.forEach {
            item(it) {
                Text(text = " ${it.toString()} ",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .padding(7.dp)
                        .background(TileBack)
                        .clickable {
                            lifecycleScope.launch {
                                val ruler = dao.getLocalRulerById(it)
                                viewModel.selectedRulerName = ruler.rulerName
                                viewModel.selectedRulerInfo = ruler.showFullInfo()
                                viewModel.selectedRuler = it
                            }
                        })
            }
        }
    }
}
