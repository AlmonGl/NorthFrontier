package com.game.northFrontier.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.Location
import com.game.northFrontier.database.LocationsDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LocationTaxesScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao
) {
    BackGround(id = 4)
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0))) {
        Text(text = viewModel.listOfLocsToTax.toString())

        Text(text = "Sir, we, ${viewModel.currentTaxesLocId} collect ${viewModel.currentLocationTotalTaxes} this year.")
        Text(text = "Please, allow us to spend some of it on our needs!")
        //if taxes > or < we need more / can we have Y of it?
        var expandedStateFood by remember { mutableStateOf(false) }
        var expandedStateCiv by remember { mutableStateOf(false) }
        var expandedStateMil by remember { mutableStateOf(false) }
        Box {
            Row {
                Text("Choose food upkeep: ${viewModel.foodUpkeep}%, ${viewModel.foodUpkeepValue*viewModel.foodUpkeep/100} gold",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .border(2.dp, Color.Black)
                        .clickable { expandedStateFood = true })
            }
            DropdownMenu(
                expanded = expandedStateFood,
                onDismissRequest = { expandedStateFood = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.foodUpkeep=100
                        expandedStateFood = false },
                    text = { Text("100%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.foodUpkeep=75
                        expandedStateFood = false},
                    text = { Text("75%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.foodUpkeep=50
                        expandedStateFood = false},
                    text = { Text("50%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.foodUpkeep=25
                        expandedStateFood = false},
                    text = { Text("25%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.foodUpkeep=0
                        expandedStateFood = false},
                    text = { Text("ZERO!") }
                )
            }
        }
        Box {
            Row {
                Text("Choose civil upkeep: ${viewModel.civUpkeep}%, ${viewModel.civUpkeepValue*viewModel.civUpkeep/100} gold",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .border(2.dp, Color.Black)
                        .clickable { expandedStateCiv = true })
            }
            DropdownMenu(
                expanded = expandedStateCiv,
                onDismissRequest = { expandedStateCiv = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.civUpkeep=100
                        expandedStateCiv = false },
                    text = { Text("100%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.civUpkeep=75
                        expandedStateCiv = false},
                    text = { Text("75%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.civUpkeep=50
                        expandedStateCiv = false},
                    text = { Text("50%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.civUpkeep=25
                        expandedStateCiv = false},
                    text = { Text("25%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.civUpkeep=0
                        expandedStateCiv = false},
                    text = { Text("ZERO!") }
                )
            }
        }
        Box {
            Row {
                Text("Choose mil upkeep: ${viewModel.milUpkeep}%, ${viewModel.milUpkeepValue*viewModel.milUpkeep/100} gold",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .border(2.dp, Color.Black)
                        .clickable { expandedStateMil = true })
            }
            DropdownMenu(
                expanded = expandedStateMil,
                onDismissRequest = { expandedStateMil = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.milUpkeep=100
                        expandedStateMil = false },
                    text = { Text("100%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.milUpkeep=75
                        expandedStateMil = false},
                    text = { Text("75%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.milUpkeep=50
                        expandedStateMil = false},
                    text = { Text("50%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.milUpkeep=25
                        expandedStateMil = false},
                    text = { Text("25%") }
                )
                Divider()
                DropdownMenuItem(
                    onClick = { viewModel.milUpkeep=0
                        expandedStateMil = false},
                    text = { Text("ZERO!") }
                )
            }
        }
        var milUp by remember {mutableStateOf(false)}
        var civUp by remember {mutableStateOf(false)}
        Row{
            Checkbox(
                checked = milUp,
                onCheckedChange = { 
                    milUp = it
                    viewModel.milUp = it
                }
            )
            Text("Spend ${if (viewModel.milUp) viewModel.milUpValue else 0} for mil level up", fontSize = 24.sp, modifier = Modifier.padding(4.dp))
        }
        Row{
            Checkbox(
                checked = civUp,
                onCheckedChange = {
                    civUp = it
                    viewModel.civUp = it
                }
            )
            Text("Spend ${if (viewModel.civUp) viewModel.civUpValue else 0} for civ level up", fontSize = 24.sp, modifier = Modifier.padding(4.dp))
        }
        var x = viewModel.currentLocationTotalTaxes - (if (viewModel.civUp) viewModel.civUpValue else 0)
        x-= (if (viewModel.milUp) viewModel.milUpValue else 0)
        x-= viewModel.foodUpkeepValue*viewModel.foodUpkeep/100
        x-= viewModel.civUpkeepValue*viewModel.civUpkeep/100
        x-= viewModel.milUpkeepValue*viewModel.milUpkeep/100
        Text(text = "Total change to our coffer: ${x}")
        Button(onClick = {
            lifecycleScope.launch {
                calculateLocTaxes(
                    lifecycleScope,
                    dao,
                    viewModel,
                    navController
                )
                milUp = false
                civUp = false
            }
        }


        )

        {
            Text(text = "Ok")
        }
        Text(text = viewModel.text1)

    }
}

fun calculateLocTaxes(
                lifecycleScope: LifecycleCoroutineScope,
                dao: LocationsDao,
                viewModel: LocationViewModel,
                navController: NavController

                      )

{
    lifecycleScope.launch {
        val location = dao.getLocationById(viewModel.currentTaxesLocId)
        var goldSpent = 0
        //////////////CALCULATIONS TODO?
        when (viewModel.foodUpkeep) {
            0-> location.decreasePop(location.workersAll/5)
            25-> location.decreasePop(location.workersAll/15)
            50-> location.decreasePop(location.workersAll/30)
            75-> location.decreasePop(location.workersAll/50)
        }
        goldSpent+= (viewModel.foodUpkeepValue * viewModel.foodUpkeep) / 100
        viewModel.foodUpkeep=0
        when (viewModel.civUpkeep) {
            0-> location.changeCivLvl(-10)
            25-> location.changeCivLvl(-3)
            50-> location.changeCivLvl(-2)
            75-> location.changeCivLvl(-1)
        }
        goldSpent+= (viewModel.civUpkeep * viewModel.civUpkeep) / 100
        viewModel.civUpkeep=0
        when (viewModel.milUpkeep) {
            0-> location.changeMilLvl(-10)
            25-> location.changeMilLvl(-3)
            50-> location.changeMilLvl(-2)
            75-> location.changeMilLvl(-1)
        }
        goldSpent+= (viewModel.milUpkeep * viewModel.milUpkeep) / 100
        viewModel.milUpkeep=0
        if (viewModel.milUp) {
            location.changeMilLvl(1)
            goldSpent+=viewModel.milUpValue
        }
        viewModel.milUpValue = 0
        viewModel.milUp=false
        if (viewModel.civUp) {
            location.changeCivLvl(1)
            goldSpent+=viewModel.civUpValue
        }
        viewModel.civUpValue = 0
        viewModel.civUp=false

        //////////
        val stats = dao.getYourStats()[0]
        location.upkeepBeforeLastYear = location.upkeepLastYear
        location.upkeepLastYear = goldSpent
        stats.taxesLastYear+=viewModel.currentLocationTotalTaxes-goldSpent
        stats.gold+=viewModel.currentLocationTotalTaxes-goldSpent
        stats.upkeepLastYear+=goldSpent
        dao.updateLocation(location)
        dao.updateYourStats(stats)
        if (viewModel.listOfLocsToTax.isNotEmpty())
        {
            viewModel.currentTaxesLocId = viewModel.listOfLocsToTax[0]
            viewModel.listOfLocsToTax.remove(viewModel.currentTaxesLocId)
            val newLocation = dao.getLocationById(viewModel.currentTaxesLocId)
            viewModel.currentLocationTotalTaxes = newLocation.taxesLastYear
            viewModel.civUpkeepValue = newLocation.civUpkeep
            viewModel.milUpkeepValue = newLocation.milUpkeep
            viewModel.foodUpkeepValue = newLocation.foodUpkeep
            viewModel.civUpValue = newLocation.civUpFunds
            viewModel.milUpValue = newLocation.milUpFunds
            viewModel.text1 = newLocation.showFogData()
            delay(500)
        } else {
            viewModel.text1 = ""
            navController.navigate(Screen.MainInfo.route)
        }

    }
}