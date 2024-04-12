package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import androidx.room.Transaction
import com.game.northFrontier.view.BackButton
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.database.Squad
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SquadManagingScreen(navController: NavController,
                        lifecycleScope: LifecycleCoroutineScope,
                        viewModel: LocationViewModel,
                        dao: LocationsDao,
                        context: Context) {
    BackGround(id = 1)

    Column {
        BackButton(navController = navController)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = viewModel.squadName,
            label = {
                    Text(text = "Enter new squad name")
            },
            onValueChange =
        {
            viewModel.squadName = it
        })
        Button(onClick = {
        lifecycleScope.launch {
            addNewSquad(lifecycleScope,viewModel,dao)
            delay(500)
            updateSquadViewList(lifecycleScope,dao,viewModel)
            navController.navigate(Screen.SquadManager.route)
        }

    }) {
        Text(text = "Create new squad")
    }
        Spacer(modifier = Modifier.height(10.dp))
        SquadsMosaic(
            viewModel = viewModel,
            lifecycleScope = lifecycleScope,
            dao = dao, squadNumber = viewModel.squadIdList)
        Text(text = "Squad info: \n${viewModel.selectedSquadInfo}",
            fontSize = 25.sp,
            modifier = Modifier.verticalScroll(ScrollState(0)) )
        Button(onClick = {
        lifecycleScope.launch {
            if ((viewModel.selectedSquad in viewModel.squadIdList)) {
                val squad = dao.getSquadById(viewModel.selectedSquad)
                squad.assingNewLeader()
                val stats = dao.getYourStats()[0]
                stats.gold -= 25
                viewModel.selectedSquadInfo = squad.showAllData()
                dao.updateSquad(squad)
                dao.updateYourStats(stats)
            }
        }
        }) {
            Text(text = "Hire new leader for ${viewModel.selectedSquad} (50 gold)")
        }
        Text(text = "Change squad size:")
        Row {
            ElevatedButton(onClick = {
                changeSquadSize(lifecycleScope,dao,viewModel,-5)
            }) {
                Text(text = "-5")
            }
            ElevatedButton(onClick = {
                changeSquadSize(lifecycleScope,dao,viewModel,-1)
            }) {
                Text(text = "-1")
            }
            ElevatedButton(onClick = {
                changeSquadSize(lifecycleScope,dao,viewModel,1)
            }) {
                Text(text = "+1")
            }
            ElevatedButton(onClick = {
                changeSquadSize(lifecycleScope,dao,viewModel,5)
            }) {
                Text(text = "+5")
            }
        }
        Button(onClick = {
        lifecycleScope.launch{
            if (viewModel.selectedSquad in viewModel.squadIdList) {
                dao.deleteSquadById(viewModel.selectedSquad)
                delay(500)
                updateSquadViewList(lifecycleScope, dao, viewModel)
                navController.navigate(Screen.SquadManager.route)
            }
            viewModel.locationsWithSquads = dao.getSquadsLocations().toMutableList()
        } }) {
            Text(text = "Delete ${viewModel.selectedSquad} squad")
        }
}
}

@Transaction
fun addNewSquad(lifecycleScope: LifecycleCoroutineScope,
                viewModel: LocationViewModel,
                dao: LocationsDao) {
    lifecycleScope.launch {
        var newId = 1
        dao.getSquadsId().forEach {
            if (it>=newId) newId=it+1
        }
        if (viewModel.squadName=="") viewModel.squadName = "Squad №$newId"
        val squad = Squad(newId,1, viewModel.squadName)
        viewModel.squadName = ""
        squad.assingNewLeader()
        squad.experience=1f
        dao.insertSquad(squad)
    }
}

fun changeSquadSize(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel, x: Int){
    if (viewModel.selectedSquad in viewModel.squadIdList) {
        lifecycleScope.launch {
            val squad = dao.getSquadById(viewModel.selectedSquad)
            squad.changeNumber(x)
            viewModel.selectedSquadInfo = squad.showAllData()
            dao.updateSquad(squad)
        }
    }
}


