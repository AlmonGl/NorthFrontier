package com.game.northFrontier.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import android.net.Uri


class LocationViewModel(): ViewModel() {
    var imageID by mutableIntStateOf(1)
        private set
    var backgroundColor by mutableStateOf(Color.Blue)
        private set
    var uri: Uri? by mutableStateOf(null)
        private set
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
    fun changeBGcolor(){
        backgroundColor = Color.Red

    }
    var currentDate = ""
    var locAttacked = ""
    var rulersActionsCivUp = "Civ. level increased in: "
    var rulersActionsMilUp = "Mil. level increased in: "
    var rulersActionsMonthBeforeLast = ""
    var locationWithCivDec = ""
    var locationWithMilDec = ""
    var locationsCivUpkeep = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)

    var locationsMilUpkeep= arrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
    var civUpkeepChange: Int by mutableIntStateOf(0)
    var milUpkeepChange: Int by mutableIntStateOf(0)
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


}