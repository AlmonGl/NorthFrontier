package com.example.korlearn2.database


import android.content.Context

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.room.Transaction
import com.example.korlearn2.R

import com.example.korlearn2.ViewModel.LocationViewModel
import kotlinx.coroutines.launch
import kotlin.math.pow

@Transaction
fun generateAll(
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    viewModel: LocationViewModel,
    context: Context
) {
    val arrayOfLocationNames =
        context.resources.getStringArray(R.array.location_names).toMutableList()
    val arrayOfLeaderNames = context.resources.getStringArray(R.array.leader_names).toMutableList()
    arrayOfLeaderNames.shuffle()
    arrayOfLocationNames.shuffle()
    val locations = Array(25) { i -> Location(i + 1, arrayOfLocationNames[i], "null") }
    val localRulers = Array(40) { i -> LocalRuler(i + 1, arrayOfLeaderNames[i]) }


    val squads = listOf(
        Squad(1, "null", "null", 30),
        Squad(2, "null", "null", 20),
        Squad(3, "null", "null", 24),
        Squad(4, "null", "null", 15)
    )


    lifecycleScope.launch {
        locations.forEach {
            it.generate()
            dao.insertLocation(it)
        }
        localRulers.forEach { dao.insertLocalRuler(it) }
        squads.forEach { dao.insertSquad(it) }

        val freeLeaders =
            dao.getFreeLocalRulers(dao.getRulersOnSquads() + dao.getRulersOnLocations())
                .toMutableList()
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
fun nextMonth(
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    viewModel: LocationViewModel
) {

    lifecycleScope.launch {
        ////raids
        val enemyStats = dao.getEnemyStats()[0]
        enemyStats.raidedLast=""
        val raidSize =
            (enemyStats.enemyAvangardPower / 100..enemyStats.enemyAvangardPower / 50).random()
        var numberOfLocations = (3..5).random()
        while (numberOfLocations > 1) {
            val seed = (1..100).random()
            var locationID = 0
            when (seed) {
                in (1..50) -> locationID = (1..5).random()
                in (51..75) -> locationID = (6..10).random()
                in (76..90) -> locationID = (11..15).random()
                in (91..98) -> locationID = (16..20).random()
                in (99..100) -> locationID = (21..25).random()
            }
            val location = dao.getLocationById(locationID)
            //enemyStats.enemyAvangardPower -= raidSize //TEMPORARY
            enemyStats.raidedLast+= " $locationID"
            location.incomingBarbarians += raidSize
            numberOfLocations--
            dao.updateLocation(location)
        }
        dao.updateEnemyStats(enemyStats)
        ////raids
        val stats = dao.getYourStats()[0]
        stats.monthNumber += 1
        if (stats.monthNumber == 13) {
            stats.monthNumber = 1
            stats.yearNumber += 1
            stats.taxesBeforeLastYear = stats.taxesLastYear
            stats.taxesLastYear = 0
        }

        dao.getLocation().forEach { it ->
            val ruler = dao.getLocalRulerByName(it.rulerName)
            //TO REMOVE
            it.plannedCivilFunds = (it.workersExeptNatural * 1.1).toInt()
            it.plannedMilitaryFunds = it.militaryLvl
            stats.gold-=(it.plannedCivilFunds+it.plannedMilitaryFunds)
            //TO REMOVE
            ///MILITARY
            it.militaryFunds += it.plannedMilitaryFunds * (100 - ruler.fundsDecrease) / 100
            it.plannedMilitaryFunds = 0
            if (stats.monthNumber == 1) {
                if (it.militaryLvl * 12 <= it.militaryFunds) {
                    it.militaryFunds -= it.militaryLvl * 12
                    it.locationCoffer += it.militaryFunds * (100 - ruler.decreaseCofferIncoming) / 100
                } else if (ruler.militaryEnthusiasm) {
                } else {

                    var decreaseLvl = 6
                    if (it.militaryFunds != 0) {
                        decreaseLvl = when (it.militaryLvl * 12 / it.militaryFunds) {
                            1 -> 1
                            2 -> 2
                            3 -> 3
                            4 -> 4
                            5 -> 5
                            else -> 6
                        }
                    }
                    it.militaryLvl -= decreaseLvl
                    if (it.militaryLvl <= 0) it.militaryLvl = 1
                }
                it.militaryFunds = 0
            }
            ///CIVILIAN
            it.civilFunds += it.plannedCivilFunds * (100 - ruler.fundsDecrease) / 100
            it.civilFunds = it.civilFunds * (5 + ruler.civilCompetence) / 10
            it.plannedCivilFunds = 0
            if (it.workersExeptNatural <= it.civilFunds) {
                it.civilFunds -= it.workersExeptNatural
                it.locationCoffer += it.civilFunds * (100 - ruler.decreaseCofferIncoming) / 100
            } else if (ruler.addCivilShortageFromCoffer and (it.locationCoffer >= it.workersExeptNatural - it.civilFunds)) {
                it.locationCoffer -= (it.workersExeptNatural - it.civilFunds)
            } else if (it.workersExeptNatural > it.civilFunds) {
                it.civilLvl -= 1
                if (it.civilLvl<1) it.civilLvl=1
            }

            it.civilFunds = 0
            ///RESOLVE RAIDS
            it.barbariansLastMonth = it.incomingBarbarians
            if (it.incomingBarbarians != 0) {
                var militaryPower = it.militia + it.feudalPower
                dao.getSquadsInLocation(it.locationName).forEach {
                    militaryPower += it.number
                }
                if (militaryPower == 0) {
                    it.barbarianRaids += 25
                } else {
                    if ((it.incomingBarbarians / militaryPower > 1) and (it.incomingBarbarians / militaryPower < 1.5)) {
                        it.barbarianRaids += 10
                    } else if ((it.incomingBarbarians / militaryPower >= 1.5) and (it.incomingBarbarians / militaryPower < 2)) {
                        it.barbarianRaids += 15
                    } else if (it.incomingBarbarians / militaryPower >= 2) it.barbarianRaids += 20
                }

                it.incomingBarbarians = 0
                if (it.barbarianRaids > 100) it.barbarianRaids = 100
                if (it.barbarianRaids < 0) it.barbarianRaids = 0
            }
            ///TAXES

            if (stats.monthNumber == 1) {
                it.taxesBeforeLastYear = it.taxesLastYear
                it.taxesLastYear = 0
                it.climateLastYear = arrayListOf(
                    -5,
                    -4,
                    -3,
                    -2,
                    -2,
                    -1,
                    -1,
                    -1,
                    -1,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    1,
                    1,
                    1,
                    1,
                    2,
                    2,
                    3,
                    4,
                    5
                ).random()
                var climateInfluence = it.climateLastYear
                if ((climateInfluence < 0) and ruler.climateAdapting) climateInfluence = 0

                var naturalProduction =
                    (it.civilLvl + 70) * it.workersNatural * it.fertility *
                            (climateInfluence + 6) * (101 - it.barbarianRaids) / 60000 - it.workersAll / 2
                if (naturalProduction < 0) {
                    if (ruler.giveFromCofferIfStarvation) {
                        if (it.locationCoffer + naturalProduction >= 0) {
                            it.locationCoffer += naturalProduction
                        } else {
                            it.starvation = it.locationCoffer + naturalProduction
                            it.locationCoffer = 0
                        }
                    } else {
                        it.starvation = naturalProduction
                    }
                    naturalProduction = 0
                }
                val commoditiesProduction =
                    (it.civilLvl + 100) * it.workersCommodities * (it.fertility + 1) *
                            (climateInfluence + 6) * 3 * (101 - it.barbarianRaids) / 120000
                val profProduction = it.workersProfs * it.civilLvl
                val tradeProduction = it.workersTraders * it.civilLvl
                val extractionProduction =
                    it.abundance * it.workersExtraction * 5 * (it.civilLvl + 25) * (101 - it.barbarianRaids) / 10000
                it.taxesLastYear =
                    ((naturalProduction + commoditiesProduction + profProduction + tradeProduction +
                            extractionProduction) * (101 - it.crimeInfluence) * (101 - it.barbarianRaids)) / 10000
                if (it.taxesLastYear > 0) stats.taxesLastYear += it.taxesLastYear

                it.barbarianRaidsYearBefore = it.barbarianRaids
                it.barbarianRaids = 0
                it.commoditiesTaxesLastYear = commoditiesProduction
                it.tradeTaxesLastYear = tradeProduction
                it.professionsTaxesLastYear = profProduction
                it.naturalTaxesLastYear = naturalProduction
                it.extractionTaxesLastYear = extractionProduction

                ///leader traits
                if (ruler.profAttraction) it.workersProfs+=1
                if (ruler.tradersAttraction) it.workersTraders+=1
                if (ruler.agriAttraction) it.workersNatural+=10
                if (ruler.extractionAttraction) it.workersExtraction+=5
                if (ruler.commodityAttraction) it.workersCommodities+=5
                it.crimeInfluence-=ruler.lawsAttitude
                if (it.crimeInfluence<0) it.crimeInfluence=0
                if (it.crimeInfluence>100) it.crimeInfluence=100


            }
            ///EVENTS
            //TODO("CHECK COFFER, THEN INITIATIVE, THEN OFFER MIL LVL INCREASE OR CIV LVL INCREASE (FOR x5 from maintained cost")
            dao.updateLocation(it)

        }
        if (stats.monthNumber == 1) stats.gold += stats.taxesLastYear
        stats.loan += if (stats.loan >= 100) stats.loan / 100 else if (stats.loan in 1..99) 1 else 0

        stats.loanPercent =
            if (stats.loan >= 80) stats.loan / 80 else if (stats.loan in 1..79) 1 else 0
        stats.loan -= stats.loanPercent
        stats.gold -= stats.loanPercent
        dao.updateYourStats(stats)
    }
}



fun raids(
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    viewModel: LocationViewModel
) {

    lifecycleScope.launch {
        val enemyStats = dao.getEnemyStats()[0]
        val raidSize =
            (enemyStats.enemyAvangardPower / 100..enemyStats.enemyAvangardPower / 50).random()
        var numberOfLocations = (3..5).random()
        while (numberOfLocations > 1) {
            val seed = (1..100).random()
            var locationID = 0
            when (seed) {
                in (1..50) -> locationID = (1..5).random()
                in (51..75) -> locationID = (6..10).random()
                in (76..90) -> locationID = (11..15).random()
                in (91..98) -> locationID = (16..20).random()
                in (99..100) -> locationID = (21..25).random()
            }
            var location = dao.getLocationById(locationID)
            enemyStats.enemyAvangardPower -= raidSize
            enemyStats.raidedLast+= " $locationID"
            location.incomingBarbarians += raidSize
            numberOfLocations--
            dao.updateLocation(location)
        }
        dao.updateEnemyStats(enemyStats)

    }


}
