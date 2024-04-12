package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.view.BackButton
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.database.Squad
import com.game.northFrontier.ui.theme.MosaicBack
import com.game.northFrontier.ui.theme.TileBack
import kotlinx.coroutines.launch

@Composable
fun SquadsAndSpiesScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    BackGround(id = 2)
    Column(horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize(),
            //.verticalScroll(ScrollState(0)),
        verticalArrangement = Arrangement.Top) {
    Spacer(modifier = Modifier.height(5.dp))
    BackButton(navController)
    Spacer(modifier = Modifier.height(5.dp))
    Text(text = "Locations:",fontSize = 25.sp, fontStyle = FontStyle.Italic)
    LocationMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao)

        Text(text = "Assign ${viewModel.selectedSquad.toString()} squad to ${viewModel.selectedLocationId.toString()} loc.", fontSize = 20.sp)

        Button(onClick = {
            if ((viewModel.selectedSquad!=-1)&&(viewModel.selectedLocationId!=-1))
            {
                lifecycleScope.launch {
                val squad: Squad = dao.getSquadById(viewModel.selectedSquad)
                squad.locationId=viewModel.selectedLocationId

                dao.updateSquad(squad)
                viewModel.locationsWithSquads = dao.getSquadsLocations().toMutableList()
                }
            }
        }) {
            Text(text = "Assign")
        }
        Text(text = "Send spy to ${viewModel.selectedLocationId}.",fontSize = 20.sp)
        Button(onClick = {
            if (viewModel.selectedLocationId!=-1)
            {
                lifecycleScope.launch {
                    val yourStats = dao.getYourStats()
                    yourStats[0].spyOnLocation = viewModel.selectedLocationId
                    dao.updateYourStats(yourStats[0])
                }
            }
        }) {
            Text(text = "Send")
        }



    Text(text = "Squads:",fontSize = 25.sp, fontStyle = FontStyle.Italic)
    SquadsMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao, squadNumber = viewModel.squadIdList)
    Spacer(modifier = Modifier.height(5.dp))
    Text(text = "Squad info: \n${viewModel.selectedSquadInfo}", fontSize = 25.sp, modifier = Modifier.verticalScroll(
        ScrollState(0)
    ) )
    }
}
@Composable
fun SquadsMosaic(
    viewModel: LocationViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    squadNumber: List<Int>

) {

    LazyVerticalGrid(columns = GridCells.Fixed(6), modifier = Modifier
            .background(MosaicBack)
        .fillMaxWidth())
    {
        squadNumber.forEach {
            item(it) {
                Text(text = " ${it.toString()} ",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .padding(7.dp)
                        .background(TileBack)
                        .clickable {
                            lifecycleScope.launch {
                                viewModel.selectedSquadInfo = dao
                                    .getSquadById(it)
                                    .showAllData()
                                viewModel.selectedSquad = it
                            }
                        })
            }
        }
    }
    /*Row(modifier = Modifier
        .background(MosaicBack)
        .fillMaxWidth()) {

        squadNumber.forEach {

            Text(text = " ${it.toString()} ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(13.dp)
                .background(TileBack)
                .clickable {
                    lifecycleScope.launch {
                        viewModel.selectedSquadInfo = dao
                            .getSquadById(it)
                            .showAllData()
                        viewModel.selectedSquad = it
                    }
                })

        }
    }*/

}



