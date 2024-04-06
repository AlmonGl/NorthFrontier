package com.game.northFrontier.view

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.view.BackButton
import com.game.northFrontier.view.BackGround
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import kotlinx.coroutines.launch

@Composable
fun UpkeepScreen(navController: NavController,
                 lifecycleScope: LifecycleCoroutineScope,
                 viewModel: LocationViewModel,
                 dao: LocationsDao,
                 context: Context,


) {

    BackGround(id = 2)
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        BackButton(navController)
        Text(text = "Civilian infrastructure upkeep: ${viewModel.locationsCivUpkeep.sum() + viewModel.civUpkeepChange}")
        for (i in 1..16) {
            val x = viewModel.locationsCivUpkeep[i]
            LocationUpkeep(dao,lifecycleScope,viewModel,i,"Civ",x)
        }
        Text(text = "Military infrastructure upkeep: ${viewModel.locationsMilUpkeep.sum() + viewModel.milUpkeepChange}")
        for (i in 1..16) {
            val x = viewModel.locationsMilUpkeep[i]
            LocationUpkeep(dao,lifecycleScope,viewModel,i,"Mil",x)
        }
    }
}
    

@Composable
fun LocationUpkeep(dao: LocationsDao, lifecycleScope: LifecycleCoroutineScope, viewModel: LocationViewModel, id: Int, type: String, upkeep: Int) {

    Row (modifier = Modifier.border(2.dp, Color.Black)) {

        var x = remember {
            mutableStateOf(0)
        }
        ElevatedButton(onClick = {
            changeUpkeep(dao,lifecycleScope,viewModel,id, type, -25)
            x.value-=25
        }) {
            Text(text = "-25")
        }
        ElevatedButton(onClick = {
            changeUpkeep(dao,lifecycleScope,viewModel,id, type, -10)
            x.value-=10
        }) {
            Text(text = "-10")
        }
        ElevatedButton(onClick = {
            changeUpkeep(dao,lifecycleScope,viewModel,id, type, -1)
            x.value-=1 }) {
            Text(text = "-1")
        }
        Text(text = "Loc. $id\n " +
                "${if (upkeep+x.value>=0) {upkeep+x.value} else {0}}g."
        )
        ElevatedButton(onClick = {
            changeUpkeep(dao,lifecycleScope,viewModel,id, type, 1)
            x.value+=1
        }) {
            Text(text = "+1")
        }
        ElevatedButton(onClick = { changeUpkeep(dao,lifecycleScope,viewModel,id, type, 10)
            x.value+=10}) {
            Text(text = "+10")
        }

    }
}




fun changeUpkeep(dao: LocationsDao, lifecycleScope: LifecycleCoroutineScope,viewModel: LocationViewModel, id: Int, type: String, value: Int){
    lifecycleScope.launch {
        val loc = dao.getLocationById(id)
        when (type) {
            "Mil" -> {
                loc.plannedMilitaryFunds+=value
                if (loc.plannedMilitaryFunds<0) loc.plannedMilitaryFunds=0
                viewModel.milUpkeepChange+=value
                /*val array = viewModel.locationsMilUpkeep
                array[id]+=value
                viewModel.updateMil(array)*/



            }
            "Civ" -> {
                loc.plannedCivilFunds+=value
                if (loc.plannedCivilFunds<0) loc.plannedCivilFunds=0
                viewModel.civUpkeepChange+=value
                /*val array = viewModel.locationsCivUpkeep
                array[id]+=value
                viewModel.updateCiv(array)*/




            }
        }

        dao.updateLocation(loc)
    }
}

