package com.example.korlearn2.view

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.korlearn2.InfoButtons

import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.generateAll
import com.example.korlearn2.database.nextMonth
import kotlinx.coroutines.launch

@Composable
fun MainInfoScreen(
    navController: NavController,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: LocationViewModel,
    dao: LocationsDao,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF535353)),
        verticalArrangement = Arrangement.Top

    )

    {

        DropDown(text = "Show LocationDetails: ", viewModel = viewModel, dao = dao, lifecycleScope = lifecycleScope) {
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

                    nextMonth(lifecycleScope, dao, viewModel)

                }
            ) {
                Text(text = "next month")
            }
        }
        Box(modifier = Modifier.verticalScroll(ScrollState(0))) {
            Text(
                text = viewModel.text1,
                color = Color(0xFFA9B7C6),
                fontSize = 25.sp

            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationList(
    locationsID: List<Int>,
    viewModel: LocationViewModel

) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select Location") }
    LocalSoftwareKeyboardController.current?.hide()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                locationsID.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },

                        onClick = {
                            selectedText = item.toString()
                            viewModel.locationID = item.toString()
                            expanded = false

                        }
                    )


                }
            }
        }
    }
}

@Composable
fun LocationMosaic(
    viewModel: LocationViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao

) {
    Column(Modifier.background(Color(0xFF166054))) {
        Row() {
            Text(text = " 1 ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
                .clickable {
                    lifecycleScope.launch {
                        viewModel.text1 = dao
                            .getLocationById(1)
                            .showAllData()
                        dao.getSquadsInLocation(1).forEach {
                            viewModel.text1+= "\n${it.showAllData()}"
                        }
                        viewModel.selectedLocationId=1
                    }
                })
            Text(text = " 2 ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
                .clickable {
                    lifecycleScope.launch {
                        viewModel.text1 = dao
                            .getLocationById(2)
                            .showAllData()
                        dao.getSquadsInLocation(2).forEach {
                            viewModel.text1+= "\n${it.showAllData()}"
                        }
                        viewModel.selectedLocationId=2
                    }
                })
            Text(text = " 3 ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
                .clickable {
                    lifecycleScope.launch {
                        viewModel.text1 = dao
                            .getLocationById(3)
                            .showAllData()
                        dao.getSquadsInLocation(3).forEach {
                            viewModel.text1+= "\n${it.showAllData()}"
                        }
                        viewModel.selectedLocationId=3
                    }
                })
            Text(text = " 4 ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
                .clickable {
                    lifecycleScope.launch {
                        viewModel.text1 = dao
                            .getLocationById(4)
                            .showAllData()
                        dao.getSquadsInLocation(4).forEach {
                            viewModel.text1+= "\n${it.showAllData()}"
                        }
                        viewModel.selectedLocationId=4
                    }
                })
            Text(text = " 5 ", fontSize = 32.sp, fontFamily= FontFamily.Cursive, modifier = Modifier
                .padding(3.dp)
                .background(Color(0xFF705050))
                .clickable {
                    lifecycleScope.launch {
                        viewModel.text1 = dao
                            .getLocationById(5)
                            .showAllData()
                        dao.getSquadsInLocation(5).forEach {
                            viewModel.text1+= "\n${it.showAllData()}"
                        }
                        viewModel.selectedLocationId=5
                    }
                })
        }
        Row {
            Text(text = "6", modifier = Modifier.padding(3.dp))
            Text(text = "7", modifier = Modifier.padding(3.dp))
            Text(text = "8", modifier = Modifier.padding(3.dp))
            Text(text = "9", modifier = Modifier.padding(3.dp))
            Text(text = "10", modifier = Modifier.padding(3.dp))
        }
        Row {
            Text(text = "11", modifier = Modifier.padding(3.dp))
            Text(text = "12", modifier = Modifier.padding(3.dp))
            Text(text = "13", modifier = Modifier.padding(3.dp))
            Text(text = "14", modifier = Modifier.padding(3.dp))
            Text(text = "15", modifier = Modifier.padding(3.dp))
        }
        Row {
            Text(text = "16", modifier = Modifier.padding(3.dp))
            Text(text = "17", modifier = Modifier.padding(3.dp))
            Text(text = "18", modifier = Modifier.padding(3.dp))
            Text(text = "19", modifier = Modifier.padding(3.dp))
            Text(text = "00", modifier = Modifier.padding(3.dp))
        }
        Row {
            Text(text = "21", modifier = Modifier.padding(3.dp))
            Text(text = "22", modifier = Modifier.padding(3.dp))
            Text(text = "23", modifier = Modifier.padding(3.dp))
            Text(text = "24", modifier = Modifier.padding(3.dp))
            Text(text = "25", modifier = Modifier.padding(3.dp))
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
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        isOpen = !isOpen
                    }
                    .scale(2.5f, if (isOpen) -2.5f else 2.5f)
            )
        }

        AnimatedVisibility(visible = isOpen) {
            content()
        }

    }
}