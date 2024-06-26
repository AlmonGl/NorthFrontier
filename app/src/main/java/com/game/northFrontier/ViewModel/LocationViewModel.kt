package com.game.northFrontier.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import android.net.Uri
import androidx.compose.runtime.remember
import com.game.northFrontier.database.Location


class LocationViewModel(): ViewModel() {

    var locationID: String by mutableStateOf("")

    var locationsID by mutableStateOf(listOf(0))
    var text1: String by mutableStateOf("")
    var selectedLocationId: Int by mutableIntStateOf(1)
    var selectedSquad: Int by mutableIntStateOf(1)
    var selectedSquadInfo: String by mutableStateOf("")
    var selectedRuler: Int by mutableIntStateOf(-1)
    var selectedRulerInfo: String by mutableStateOf("")
    var selectedRulerName: String by mutableStateOf("")
    var rulersFreeIdList: List<Int> = listOf()

    var currentMonth = 0
    var currentYear = 0
    var locAttacked = ""

    val locationsWithManors = mutableListOf<Int>()
    val locationsWithTowns = mutableListOf<Int>()
    var locationsWithSquads = mutableListOf<Int>()
    var locationsDepleted = mutableListOf<Int>()

    var raidsReport = "Rumors about barbarian raids:"
    var raidsReportBefore = ""
    var numberOfRequests = 0
    var squadIdList: List<Int> = listOf()
    var squadsSalary = 0
    var squadName: String by mutableStateOf("")
    var squadNextId = -1
    var thisTurnReports = "Other reports:\n"
    var endReason = ""
    var gameEnded = false

    var currentTaxesLocId = 1
    var currentLocationTotalTaxes = 0
    var listOfLocsToTax = mutableListOf<Int>()
    var foodUpkeep by  mutableIntStateOf(100)
    var foodUpkeepValue = 0
    var milUpkeep by  mutableIntStateOf(100)
    var milUpkeepValue = 0
    var civUpkeep by  mutableIntStateOf(100)
    var civUpkeepValue = 0
    var civUp by  mutableStateOf(false)
    var civUpValue= 0
    var milUp by  mutableStateOf(false)
    var milUpValue = 0



}