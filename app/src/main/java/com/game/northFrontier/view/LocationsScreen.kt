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

@Composable
fun LocationsScreen(navController: NavController,
                    lifecycleScope: LifecycleCoroutineScope,
                    viewModel: LocationViewModel,
                    dao: LocationsDao,
                    context: Context
) {
    BackGround(id = 3)
    Column {


        Button(onClick = { navController.navigate(Screen.Month.route) }) {
            Text(text = "Back")
        }
        LocationMosaic(viewModel = viewModel, lifecycleScope = lifecycleScope, dao = dao)
        Text(text = viewModel.text1, modifier = Modifier.verticalScroll(ScrollState(0)))
    }
}