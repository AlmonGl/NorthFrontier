package com.example.korlearn2.ViewModel

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
    var selectedLocationId: Int by mutableStateOf(-1)
    var selectedSquad: Int by mutableStateOf(1)
    var selectedSquadInfo: String by mutableStateOf("")
    var selectedSpy: Int by mutableStateOf(1)
    fun changeBGcolor(){
        backgroundColor = Color.Red

    }
    var squadIdList: List<Int> = listOf()
    fun changeImage(id: Int){
        imageID=id
    }
    fun updateUri(ur: Uri?) {
        uri = ur
    }
}