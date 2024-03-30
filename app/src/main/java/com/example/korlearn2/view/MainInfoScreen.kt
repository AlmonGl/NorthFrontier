package com.example.korlearn2.view

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.BackGround
import com.example.korlearn2.InfoButtons

import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.generateAll
import com.example.korlearn2.database.nextMonth
import com.example.korlearn2.ui.theme.MosaicBack
import com.example.korlearn2.ui.theme.TileBack
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

@Composable
fun MainInfoScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    BackGround(3)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        // .background(Color(0xFF535353)),
        verticalArrangement = Arrangement.Top


    )

    {

        DropDown(
            text = "Show LocationDetails: ",
            viewModel = viewModel,
            dao = dao,
            lifecycleScope = lifecycleScope
        ) {
            LocationMosaic(viewModel, lifecycleScope, dao)
        }
        //LocationList(locationsID = viewModel.locationsID, viewModel = viewModel)


        Row(
            modifier = Modifier
                .horizontalScroll(ScrollState(10))
        ) {
            InfoButtons(lifecycleScope = lifecycleScope, dao = dao, viewModel = viewModel)

        }
        Row() {
            Button(

                onClick =
                {

                    val listId = mutableListOf<Int>()
                    lifecycleScope.launch {
                        dao.getSquad().forEach {
                            listId.add(it.id)
                        }
                    }
                    viewModel.squadIdList = listId
                    navController.navigate(Screen.SquadsAndSpies.route)

                }
            ) {
                Text(text = "Assigning screen")
            }
            Button(

                onClick =
                {

                    navController.navigate(Screen.Map.route)

                }
            ) {
                Text(text = "Map screen")
            }
            Button(
                onClick =
                {
                    lifecycleScope.launch {
                    dao.getLocation().forEach {
                        viewModel.locationsCivUpkeep[it.id]=it.plannedCivilFunds
                        viewModel.locationsMilUpkeep[it.id]=it.plannedMilitaryFunds
                    }

                }
                    navController.navigate(Screen.Upkeep.route)


                }
            ) {
                Text(text = "Upkeep")
            }
        }
        Row {
            Button(
                onClick =
                {
                    nextMonth(lifecycleScope, dao, viewModel)
                    navController.navigate(Screen.Month.route)


                }
            ) {
                Text(text = "next month")
            }
        }
        Box(modifier = Modifier.verticalScroll(ScrollState(0))) {
            Text(
                text = viewModel.text1,
                textAlign = TextAlign.Start,
                fontSize = 25.sp

            )
        }


    }
}


@Composable
fun LocationMosaic(
    viewModel: LocationViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao

) {
    Column(modifier = Modifier.background(MosaicBack)) {
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 1..5) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 6..10) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 11..15) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 16..20) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 21..25) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
    }
}

@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    lifecycleScope: LifecycleCoroutineScope,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = text,
                fontSize = 25.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.Black,
                modifier = Modifier
                    .clickable {
                        isOpen = !isOpen
                    }
                    .scale(3.5f, if (isOpen) -3.5f else 3.5f)
            )
        }

        AnimatedVisibility(visible = isOpen) {
            content()
        }

    }
}

@Composable
fun LocationCard(
    viewModel: LocationViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    id: Int
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .background(TileBack)
            .border(7.dp, MosaicBack)
            .clickable {
                lifecycleScope.launch {
                    viewModel.text1 = dao
                        .getLocationById(id)
                        .showFogData()
                    dao
                        .getSquadsInLocation(id)
                        .forEach {
                            viewModel.text1 += "\n${it.showAllData()}"
                        }
                    viewModel.selectedLocationId = id
                }
            },
        contentAlignment = Alignment.Center
    )
    {
        Text(text = " $id ", fontSize = 34.sp, fontFamily = FontFamily.Cursive)

    }
}