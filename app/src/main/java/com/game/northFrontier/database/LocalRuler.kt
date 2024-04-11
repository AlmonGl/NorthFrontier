package com.game.northFrontier.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class LocalRuler(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val rulerName: String

) {
    var civUpEfficiency = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var milUpEfficiency = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var civUpkeepEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var milUpkeepEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var foodUpkeepEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var naturalTaxesEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var commodTaxesEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var extractionTaxesEff = arrayListOf(100, 100, 100, 105, 105, 110, 110, 115).random()
    var tradersTaxesEff = arrayListOf(100,100,100,105,105,110,110,115).random()
    var profTaxesEff = arrayListOf(100,100,100,105,105,110,110,115).random()

    fun showFullInfo(): String {
        var s = "$id. $rulerName, "
        s+= "\nTaxes eff: $tradersTaxesEff/$profTaxesEff/$extractionTaxesEff/$commodTaxesEff/$naturalTaxesEff"
        s+= "\nUpkeep eff: $civUpkeepEff/$milUpkeepEff/$foodUpkeepEff"
        s+= "\nUpgrades eff: $civUpEfficiency/$milUpEfficiency"
        return s
    }
}
