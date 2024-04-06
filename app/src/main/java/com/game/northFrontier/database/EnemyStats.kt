package com.game.northFrontier.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EnemyStats(
    @PrimaryKey
    val id: Int = 1
) {
    var enemyReservePower = (5000..30000).random()
    var enemyAvangardPower = 15000
    var raidedLast=""
    fun showAllData(): String {
        return "Reserve: $enemyReservePower\n Avangard: $enemyAvangardPower. Raided last $raidedLast"
    }
}