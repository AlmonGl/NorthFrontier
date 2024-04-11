package com.game.northFrontier.view

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.game.northFrontier.R

import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.database.LocationsDao
import com.game.northFrontier.database.nextMonth
import com.game.northFrontier.ui.theme.MosaicBack
import com.game.northFrontier.ui.theme.TileBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                    updateSquadViewList(lifecycleScope,dao,viewModel)
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

        }
        Row {
            Button(
                onClick =
                {
                    refreshRulerManager(lifecycleScope,viewModel,dao)
                    navController.navigate(Screen.RulersManaging.route)
                }
            ) {
                Text(text = "rulers")
            }
            Button(
                onClick =
                {
                    viewModel.squadName = ""
                    updateSquadViewList(lifecycleScope,dao,viewModel)
                    navController.navigate(Screen.SquadManager.route)
                }
            ) {
                Text(text = "squads")
            }
        }
        Row {
            Button(
                onClick =
                {
                    nextMonth(lifecycleScope, dao, viewModel, navController)
                    lifecycleScope.launch { delay(1000) }
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
            for (i in 1..4) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 5..8) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 9..12) LocationCard(
                viewModel = viewModel,
                lifecycleScope = lifecycleScope,
                dao = dao,
                id = i
            )
        }
        Row(modifier = Modifier.background(MosaicBack)) {
            for (i in 13..16) LocationCard(
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
                            viewModel.text1 += "\n\n${it.showAllData()}"
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

fun updateSquadViewList(lifecycleScope: LifecycleCoroutineScope,dao: LocationsDao,viewModel: LocationViewModel) {
    val listId = mutableListOf<Int>()
    lifecycleScope.launch {
        dao.getSquad().forEach {
            listId.add(it.id)
        }
    }
    viewModel.squadIdList = listId
}

@Composable
fun InfoButtons(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel){

    Button(
        onClick =
        {

            lifecycleScope.launch {
                val squadDislocation = dao.getSquadsLocations()


                viewModel.text1 = "Your stats: ${dao.getYourStats()[0].showAllData()} "
                viewModel.text1+="\n\nTotal squads cost: ${viewModel.squadsSalary}"
                viewModel.text1+="\nSquads in: ${squadDislocation.sorted()}"

                //viewModel.text1 += "\n Enemy stats: ${dao.getEnemyStats()[0].showAllData()}"


                viewModel.text1+="\n${viewModel.raidsReportBefore}"
                viewModel.text1+="\n${viewModel.thisTurnReports}"

            }
        }
    ) {
        Text(text = "Stats")
    }
    Button(
        onClick =
        {

            lifecycleScope.launch {
                var s = "Locations"
                dao.getLocation().forEach {
                    s = s.plus("\n").plus(it.id).plus(" ").plus(it.locationName)
                        .plus(" ").plus(it.rulerName)
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Loc. list")
    }
    Button(
        onClick =
        {

            lifecycleScope.launch {
                var s = "Leaders:"
                dao.getLocalRuler().forEach {
                    s = s.plus("\n").plus(it.showFullInfo())
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Ruler list")
    }

    Button(
        onClick =
        {
            lifecycleScope.launch {
                var s = "Squads:"
                dao.getSquad().forEach {
                    s = s.plus("\n").plus(it.showAllData()).plus(it.showSquadLeaderData())
                }
                viewModel.text1 = s
            }
        }
    ) {
        Text(text = "Squad list")
    }
}

@Composable
fun BackGround(id: Int){
    Box(modifier = Modifier.fillMaxSize()){
        val p: Painter = when (id) {
            1-> painterResource(id = R.drawable.im1)
            2-> painterResource(id = R.drawable.im2)
            3-> painterResource(id = R.drawable.im3)
            4-> painterResource(id = R.drawable.im4)
            5-> painterResource(id = R.drawable.im5)
            6-> painterResource(id = R.drawable.im6)
            else -> painterResource(id = R.drawable.im5)
        }
        Image(
            painter = p,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.Crop

        )

    }
}

@Composable
fun BackButton(navController: NavController){
    Button(
        onClick = { navController.navigate(Screen.MainInfo.route) }) {
        Text(
            text = "Back",
            fontSize = 25.sp
        )
    }
}