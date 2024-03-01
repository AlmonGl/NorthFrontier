package com.example.korlearn2.ViewModel

import android.media.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import android.net.Uri
import com.example.korlearn2.database.DataSome

class LocationViewModel(): ViewModel() {
    var imageID by mutableIntStateOf(1)
        private set
    var backgroundColor by mutableStateOf(Color.Blue)
        private set
    var uri: Uri? by mutableStateOf(null)
        private set
    var text: String by mutableStateOf("")
    var text1: String by mutableStateOf("")
    fun changeBGcolor(){
        backgroundColor = Color.Red

    }
    fun compute(){
        text += DataSome.items.toString()

    }
    fun changeImage(id: Int){
        imageID=id
    }
    fun updateUri(ur: Uri?) {
        uri = ur
    }
}