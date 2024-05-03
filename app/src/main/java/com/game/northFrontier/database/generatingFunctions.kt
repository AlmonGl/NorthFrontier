package com.game.northFrontier.database


import android.content.Context

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import androidx.room.Transaction
import com.game.northFrontier.R

import com.game.northFrontier.ViewModel.LocationViewModel
import com.game.northFrontier.view.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Transaction
fun generateAll(
    lifecycleScope: LifecycleCoroutineScope,
    dao: LocationsDao,
    viewModel: LocationViewModel,
    context: Context
) {

    lifecycleScope.launch {
        dao.deleteSquads()
    }
    val arrayOfLocationNames =
        context.resources.getStringArray(R.array.location_names).toMutableList()
    val arrayOfLeaderNames = context.resources.getStringArray(R.array.leader_names).toMutableList()
    arrayOfLeaderNames.shuffle()

    val locations = Array(16) { i -> Location(i + 1, arrayOfLocationNames[i], "null") }
    val localRulers = Array(20) { i -> LocalRuler(i + 1, arrayOfLeaderNames[i]) }


    val squads = listOf(
        Squad(1,   (15..100).random(), "1st Squad"),
        Squad(2,   (15..100).random(),"2nd Squad"),
        Squad(3,   (15..100).random(),"3th Squad"),
        Squad(4,   (15..100).random(),"4th Squad")
    )


    lifecycleScope.launch {
        locations.forEach {
            it.generate()
            dao.insertLocation(it)
            if (it.manor) viewModel.locationsWithManors.add(it.id)
            if (it.town) viewModel.locationsWithTowns.add(it.id)
        }
        localRulers.forEach { dao.insertLocalRuler(it) }
        squads.forEach {
            it.assingNewLeader()
            dao.insertSquad(it)
        }

        val freeLeaders =
            dao.getFreeLocalRulers(dao.getRulersOnLocations())
                .toMutableList()
        dao.getLocationsWithoutRuler().forEach {

            val l = freeLeaders.random()
            it.rulerName = l.rulerName
            dao.updateLocation(it)
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
    viewModel: LocationViewModel,
    navController: NavController
) {


    viewModel.thisTurnReports = "Other reports:"
    lifecycleScope.launch {
        ////PLANNING RAIDS
        val enemyStats = dao.getEnemyStats()[0]
        enemyStats.raidedLast=""
        val r = enemyStats.enemyAvangardPower / (90..110).random()
        val raidSize = arrayListOf(r,r,r,r,r,r,r,2*r,2*r,2*r,2*r,2*r,3*r,3*r,4*r,5*r).random()
        var numberOfLocations = arrayListOf(1,1,2,2,2,2,2,2,2,3,3,3,4,4,5).random()
        val locIds = dao.getNotDepletedLocationsIds()
        locIds.sorted()
        if (locIds.size<8) {
            viewModel.endReason = "You lose half of your province, to barbarians. News about it reached emperor and you have been fired."
            viewModel.gameEnded=true
            delay(1000)
            navController.navigate(Screen.End.route)

        }
        val firstRowLocs = mutableListOf<Int>(locIds[0],locIds[1],locIds[2],locIds[3])
        val secondRowLocs = mutableListOf<Int>(locIds[4],locIds[5],locIds[6],locIds[7])
        while (numberOfLocations >= 1) {
            val seed = (1..100).random()
            var locationID = 0

            when (seed) {
                in (1..90) -> locationID = firstRowLocs.random()
                in (91..100) -> locationID = secondRowLocs.random()
            }
            val location = dao.getLocationById(locationID)
            //enemyStats.enemyAvangardPower -= raidSize //TEMPORARY
            enemyStats.raidedLast+= "$locationID, "
            location.incomingBarbarians += raidSize
            numberOfLocations--
            dao.updateLocation(location)
        }
        viewModel.locAttacked =enemyStats.raidedLast
        dao.updateEnemyStats(enemyStats)
        ////
        val stats = dao.getYourStats()[0]
        ///CALENDAR
        stats.monthNumber += 1
        if (stats.monthNumber == 13) {
            stats.monthNumber = 1
            stats.yearNumber += 1
            stats.taxesBeforeLastYear = stats.taxesLastYear
            stats.taxesLastYear = 0
            stats.upkeepBeforeLastYear = stats.upkeepLastYear
            stats.upkeepLastYear=0
        }
        viewModel.currentMonth = stats.monthNumber
        viewModel.currentYear = stats.yearNumber

        ////SPYING
        if (stats.spyOnLocation!=-1){
            val locSpying = dao.getLocationById(stats.spyOnLocation)
            locSpying.fogOfWar++
            if (locSpying.fogOfWar>5)  locSpying.fogOfWar=5
            dao.updateLocation(locSpying)
        }
        ////FOR EACH LOCATION

        dao.getNotDepletedLocations().forEach { it ->
            val ruler = dao.getLocalRulerByName(it.rulerName)
            //


            ///RESOLVE RAIDS
            it.barbariansLastMonth = it.incomingBarbarians
            if (it.incomingBarbarians != 0) {
                var militaryPower = it.militia + it.feudalPower
                var militaryNumber = militaryPower
                var overwhelmed = false
                var tactics = 1f
                dao.getSquadsInLocation(it.id).forEach {
                    militaryPower += it.strength
                    militaryNumber += it.number
                    it.inAction=true
                    dao.updateSquad(it)
                    if (it.tactics>tactics) tactics=it.tactics
                }
                militaryPower=(militaryPower.toFloat()*(1f+ (it.militaryLvl*tactics) /35f)).toInt()
                var razed = 0
                var deaths =0
                var combatantLossesPercent = 0
                if (militaryPower == 0) {
                    razed = (30..35).random()
                    deaths = (30..100).random()

                } else {
                    when (it.incomingBarbarians.toFloat() / militaryPower.toFloat()){
                        in (0f..1f) -> {
                            combatantLossesPercent = (1..10).random()
                            viewModel.thisTurnReports+="\nBattle against ${it.incomingBarbarians} men was won at ${it.locationName}(${it.id}) with $militaryPower our power!"
                        }
                        in (1.01f..1.5f) -> {
                            razed = (1..9).random()
                            deaths = (1..10).random()
                            combatantLossesPercent = (5..20).random()
                            viewModel.thisTurnReports+="\nBattle against ${it.incomingBarbarians} men was lost(1 to 1.5) at ${it.locationName}(${it.id}) with $militaryPower our power."
                        }
                        in (1.51f..3f) -> {
                            razed = (10..14).random()
                            deaths = (11..30).random()
                            combatantLossesPercent = (10..30).random()
                            viewModel.thisTurnReports+="\nBattle against ${it.incomingBarbarians} men was lost(1.5 to 3) at ${it.locationName}(${it.id}) with $militaryPower our power."
                        }
                        else -> { ///>3f
                            razed = (25..30).random()
                            deaths = (30..100).random()
                            overwhelmed = true
                            combatantLossesPercent = (31..99).random()
                            viewModel.thisTurnReports+="\nBattle against ${it.incomingBarbarians} men was lost(>3) at ${it.locationName}(${it.id}) with $militaryPower our power."
                        }
                    }
                }
                if (combatantLossesPercent>0) {
                    dao.getSquadsInLocation(it.id).forEach {
                        if ((!overwhelmed)||(!it.carefull)) {
                            it.applyCasualities(it.number*combatantLossesPercent/100)
                            dao.updateSquad(it)
                        }
                    }
                }
                viewModel.raidsReport+="\n ${it.locationName}(${it.id}) suffered $deaths pop loss and $razed% of economy was razed.\n Squads losses was $combatantLossesPercent%"
                it.barbarianRaids += razed
                it.decreasePop(deaths)
                it.incomingBarbarians = 0
                if (it.barbarianRaids > 100) it.barbarianRaids = 100
                if (it.barbarianRaids < 0) it.barbarianRaids = 0
            }
            ///UPKEEP
            if (stats.monthNumber == 1) {

                ///MILITARY
                it.milUpkeep = (it.militaryLvl*12*ruler.milUpkeepEff)/100
                ///FOOD
                it.foodUpkeep = (it.workersAll * ruler.foodUpkeepEff)/100
                ///CIVILIAN
                it.civUpkeep = (it.civilLvl*12*ruler.civUpkeepEff)/100
                it.civUpFunds = (it.civilLvl*(1..12).random()*ruler.civUpEfficiency)/100
                it.milUpFunds = (it.militaryLvl*(1..12).random()*ruler.milUpEfficiency)/100
            }

            ///TAXES

            if (stats.monthNumber == 1) {
                it.taxesBeforeLastYear = it.taxesLastYear
                it.taxesLastYear = 0
                it.climateLastYear = arrayListOf(-5, -4, -3, -2, -2, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 4, 5
                ).random()
                val climateInfluence = it.climateLastYear

                val naturalProduction =
                    (((it.civilLvl + 70) * it.workersNatural * it.fertility *
                            (climateInfluence + 6) * (101 - it.barbarianRaids) / 60000)*ruler.naturalTaxesEff)/100

                val commoditiesProduction =
                    (((it.civilLvl + 100) * it.workersCommodities * (it.fertility + 1) *
                            (climateInfluence + 6) * 3 * (101 - it.barbarianRaids) / 120000)*ruler.commodTaxesEff)/100
                val profProduction = (it.workersProfs * it.civilLvl * (101 - it.barbarianRaids)*ruler.profTaxesEff)/10000
                val tradeProduction = (it.workersTraders * it.civilLvl *(101 - it.barbarianRaids)* ruler.tradersTaxesEff)/10000
                val extractionProduction =
                    ((it.abundance * it.workersExtraction * 5 * (it.civilLvl + 25) * (101 - it.barbarianRaids) / 10000)*ruler.extractionTaxesEff)/100
                it.taxesLastYear =
                    naturalProduction + commoditiesProduction + profProduction + tradeProduction +
                            extractionProduction



                it.barbarianRaidsYearBefore = it.barbarianRaids
                it.barbarianRaids = 0
                it.commoditiesTaxesLastYear = commoditiesProduction
                it.tradeTaxesLastYear = tradeProduction
                it.professionsTaxesLastYear = profProduction
                it.naturalTaxesLastYear = naturalProduction
                it.extractionTaxesLastYear = extractionProduction

                ///POPULATION GROWTH
                if (it.barbarianRaidsYearBefore< 1)
                {
                    when ((1..5).random()) {
                        1-> it.workersProfs+=1
                        2-> it.workersTraders+=1
                        3-> it.workersNatural+=10
                        4-> it.workersExtraction+=5
                        5-> it.workersCommodities+=5

                    }
                }
            }

            dao.updateLocation(it)

        }
        ////TRAINING AN PAYING SQUADS
        viewModel.squadsSalary=0
        dao.getSquad().forEach {

            it.training()
            viewModel.squadsSalary+=it.salary
            stats.gold-=it.salary
            dao.updateSquad(it)
        }



        stats.loan += if (stats.loan >= 100) stats.loan / 100 else if (stats.loan in 1..99) 1 else 0
        ///////LOAN
        stats.loanPercent =
            if (stats.loan >= 80) stats.loan / 80 else if (stats.loan in 1..79) 1 else 0
        stats.loan -= stats.loanPercent
        stats.gold -= stats.loanPercent
        if (stats.gold<0) {
            stats.loan-=stats.gold
            stats.gold=0
        }
        if (stats.loan>50000) {
            viewModel.endReason = "Your debt exceed 50000 gold. It's time to pay."
            viewModel.gameEnded=true
            delay(1000)
            navController.navigate(Screen.End.route)
        }
        dao.updateYourStats(stats)
        viewModel.locationsDepleted = dao.getDepletedLocationsIds().toMutableList()
    }
}


