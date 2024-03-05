package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EnemyStats(
    @PrimaryKey
    val id: Int = 1
) {
    var enemyReservePower = (5000..30000).random()
    var enemyAvangardPower = (500..3000).random()
}