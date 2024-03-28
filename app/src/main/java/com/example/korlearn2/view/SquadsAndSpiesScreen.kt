package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.BackButton
import com.example.korlearn2.BackGround
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.Squad
import com.example.korlearn2.ui.theme.PurpleNew
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
            //.background(Color(0xFF535353)),
        verticalArrangement = Arrangement.Top) {
    BackButton(navController)
    LocationMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao)
    Row() {
        Text(text = "Assign squad number ${viewModel.selectedSquad.toString()} to location #${viewModel.selectedLocationId.toString()}",color = Color(0xFFA9B7C6), fontSize = 25.sp)
        }
        Button(onClick = {
            if ((viewModel.selectedSquad!=-1)&&(viewModel.selectedLocationId!=-1))
            {
                lifecycleScope.launch {
                val squad: Squad = dao.getSquadById(viewModel.selectedSquad)
                squad.locationId=viewModel.selectedLocationId
                dao.updateSquad(squad)
                }
            }
        }) {
            Text(text = "assing")
        }

    Spacer(modifier = Modifier.height(30.dp))
    Text(text = viewModel.selectedSquadInfo,color = Color(0xFFA9B7C6), fontSize = 25.sp )
    Spacer(modifier = Modifier.height(30.dp))
    SquadsMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao, squadNumber = viewModel.squadIdList)
    }
}
@Composable
fun SquadsMosaic(
    viewModel: LocationViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    squadNumber: List<Int>

) {

    Column(Modifier.background(Color(0xFF166054))) {
        squadNumber.forEach {
            Text(text = " ${it.toString()} ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
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



