package com.example.korlearn2

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.korlearn2.ui.theme.KorLearn2Theme

class SecondActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KorLearn2Theme {
                Image(painter = painterResource(id = R.drawable.im3) , contentDescription = null)
            }
        }
    }
}