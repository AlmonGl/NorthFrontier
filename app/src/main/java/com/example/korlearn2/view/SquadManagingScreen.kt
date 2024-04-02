package com.example.korlearn2.view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Transaction
import com.example.korlearn2.BackButton
import com.example.korlearn2.BackGround
import com.example.korlearn2.ViewModel.LocationViewModel
import com.example.korlearn2.database.LocationsDao
import com.example.korlearn2.database.Squad
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
        TextField(
            value = viewModel.squadName,
            label = {
                    Text(text = "Enter squad name")
            },
            onValueChange =
        {
            viewModel.squadName = it
        })
        Button(onClick = {
        addNewSquad(lifecycleScope,viewModel,dao)
    }) {
        Text(text = "Create new squad")
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
        dao.insertSquad(squad)
    }
}