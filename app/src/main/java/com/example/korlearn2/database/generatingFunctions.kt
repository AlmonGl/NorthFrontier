package com.example.korlearn2.database

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.korlearn2.ViewModel.LocationViewModel
import kotlinx.coroutines.launch

fun generateAll(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel){

    val locations = listOf(
        Location(1,"London", "Gerold"),
        Location(2,"Paris", "null"),
        Location(3,"Milan", "null")
    )
    val localRulers = listOf(

        LocalRuler(1,"Gerold"),
        LocalRuler(2,"Andrew"),
        LocalRuler(3,"Toolchain"),
        LocalRuler(4,"Jon"),
        LocalRuler(6,"Jac"),
        LocalRuler(7,"Lori"),
        LocalRuler(8,"Mads"),
        LocalRuler(9,"Henry")
    )
    val squads = listOf(
        Squad(1,"null","null",30),
        Squad(2,"null","null",20),
        Squad(3,"null","null",24),
        Squad(4,"null","null",15)
    )


    lifecycleScope.launch {
        locations.forEach { dao.insertLocation(it) }
        localRulers.forEach { dao.insertLocalRuler(it) }
        squads.forEach { dao.insertSquad(it) }

        val freeLeaders =
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

fun nextMonth(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel) {
    lifecycleScope.launch {
        dao.getLocation().forEach {
            it.militaryFunds+=10
            it.civilFunds+=10
            dao.updateLocation(it)
        }
    }
}