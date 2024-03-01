package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class LocalRuler (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val rulerName: String
    )
