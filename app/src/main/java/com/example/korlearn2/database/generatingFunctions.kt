package com.example.korlearn2.database

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.korlearn2.ViewModel.LocationViewModel
import kotlinx.coroutines.launch

fun generateAll(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel){
    lifecycleScope.launch{
        var freeLeaders =
            dao.getFreeLocalRulers(dao.getRulersOnSquads() + dao.getRulersOnLocations()).toMutableList()
        dao.getLocationsWithoutRuler().forEach {

            val l = freeLeaders.random()
            it.rulerName = l.rulerName
            dao.updateLocation(it)
            freeLeaders.remove(l)
        }
        dao.getSquadsWithoutRuler().forEach {
            val l = freeLeaders.random()
            it.rulerName = l.rulerName
            dao.updateSquad(it)
            freeLeaders.remove(l)
        }
    }
}