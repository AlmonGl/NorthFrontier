package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Location (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val locationName: String,
    var rulerName: String
)
