package com.example.korlearn2.database


import android.content.Context

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.room.Transaction
import com.example.korlearn2.R

import com.example.korlearn2.ViewModel.LocationViewModel
import kotlinx.coroutines.launch
@Transaction
fun generateAll(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel, context: Context){
    val arrayOfLocationNames = context.resources.getStringArray(R.array.location_names).toMutableList()
    val arrayOfLeaderNames = context.resources.getStringArray(R.array.leader_names).toMutableList()
    arrayOfLeaderNames.shuffle()
    arrayOfLocationNames.shuffle()
    val locations = Array(25) {i ->Location(i+1,arrayOfLocationNames[i], "null")}
    val localRulers = Array(40) {i -> LocalRuler(i+1,arrayOfLeaderNames[i]) }


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
        dao.insertYourStats(YourStats())
        dao.insertEnemyStats(EnemyStats())


    }
}
@Transaction
fun nextMonth(lifecycleScope: LifecycleCoroutineScope, dao: LocationsDao, viewModel: LocationViewModel) {
    lifecycleScope.launch {
        val stats = dao.getYourStats()[0]
        stats.monthNumber+=1
        if (stats.monthNumber==13) {
            stats.monthNumber=1
            stats.yearNumber+=1
        }
        stats.taxesLastMonth = stats.taxesThisMonth
        stats.taxesThisMonth = 0
        dao.getLocation().forEach {
            val ruler = dao.getLocalRulerByName(it.rulerName)

            ///MILITARY
            it.militaryFunds += it.plannedMilitaryFunds*(100-ruler.fundsDecrease)/100
            it.plannedMilitaryFunds=0
            if (stats.monthNumber==1){
                if (it.militaryLvl*12<=it.militaryFunds)
                {
                    it.militaryFunds-=it.militaryLvl*12
                    it.locationCoffer+=it.militaryFunds*(100-ruler.decreaseCofferIncoming)/100
                } else if (ruler.militaryEnthusiasm) {
                } else {

                    var decreaseLvl = 6
                    if (it.militaryFunds!=0) {
                        decreaseLvl = when (it.militaryLvl * 12 / it.militaryFunds) {
                            1 -> 1
                            2 -> 2
                            3 -> 3
                            4 -> 4
                            5 -> 5
                            else -> 6
                        }
                    }
                    it.militaryLvl-= decreaseLvl
                    if (it.militaryLvl<=0) it.militaryLvl=1
                }
                it.militaryFunds=0
            }
            ///CIVILIAN
            it.civilFunds += it.plannedCivilFunds*(100-ruler.fundsDecrease)/100
            it.plannedCivilFunds = 0
            if (it.workersExeptNatural*2<=it.civilFunds) {
                it.civilFunds-=it.workersExeptNatural*2
                it.locationCoffer+=it.civilFunds*(100-ruler.decreaseCofferIncoming)/100
            } else if (ruler.addCivilShortageFromCoffer and (it.locationCoffer>=it.workersExeptNatural*2-it.civilFunds)) {
                it.locationCoffer-=(it.workersExeptNatural*2-it.civilFunds)
            } else if (it.workersExeptNatural>it.civilFunds) {
                it.civilLvl-=2
                it.locationCoffer+=it.civilFunds*(100-ruler.decreaseCofferIncoming)/100
            }
            else {
                it.civilLvl-=1
                it.locationCoffer+=(it.civilFunds-it.workersExeptNatural)*(100-ruler.decreaseCofferIncoming)/100
            }

            it.civilFunds=0

            ///EVENTS
            //TODO("CHECK COFFER, THEN INITIATIVE, THEN OFFER MIL LVL INCREASE OR CIV LVL INCREASE (FOR x5 from maintained cost")
            ///NATURE TAXES
            it.taxesLastYear=0
            it.climateLastYear = (-5..5).random()

            dao.updateLocation(it)
        }

        stats.loan+= if (stats.loan>=50) stats.loan/50 else if (stats.loan in 1..49) 1 else 0
        stats.gold+=stats.taxesThisMonth
        stats.loanPercent = if (stats.loan>=100) stats.loan/100 else if (stats.loan in 1..99) 1 else 0
        stats.loan-=stats.loanPercent
        stats.gold-=stats.loanPercent
        dao.updateYourStats(stats)
    }
}