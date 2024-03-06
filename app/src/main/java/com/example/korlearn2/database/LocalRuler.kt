package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class LocalRuler (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val rulerName: String

    ) {
        var initiative = (0..10).random()
        var civilCompetence = (1..10).random()
        var seed = (1..15).random()
        var description = "Traits are:"
        ///SEED 1 - Stinginess
        var addCivilShortageFromCoffer = seed != 1

        ///SEED 2 - Greed
        var decreaseCofferIncoming = if (seed==2) (10..50).random() else 0

        ///SEED 3 - Corruption
        var fundsDecrease = if (seed==3) (10..50).random() else 0

        ///SEED 4 Military enthusiast
        var militaryEnthusiasm = seed==4

        ///SEED 5 Climate adapting
        var climateAdapting = seed == 5

        ///SEED 6-7 Laws attitude
        var lawsAttitude = if (seed==6) (1..5).random() else if (seed==7) (-5..-1).random() else 0

        ///SEED 8 Traders attraction
        var tradersAttraction = seed==8

        ///SEED 9 Prof attraction
        var profAttraction = seed==9

        ///SEED 10 Agri attraction
        var agriAttraction = seed==10

        ///SEED 11 Commoditu attraction
        var commodityAttraction = seed==11

        ///SEED 12 Extraction attraction
        var extractionAttraction = seed==12

        ///SEED 13 Religious
        var religious = seed==13

        ///SEED 14 Vigilant
        var vigilant = seed==14

        //SEED 15 Cruel
        var giveFromCofferIfStarvation = seed!=14

        fun showFullInfo():String {
                return "$id. $rulerName, Seed $seed, greed $decreaseCofferIncoming, corruption $fundsDecrease, laws $lawsAttitude" +
                        "\nInit civil competence: $initiative, $civilCompetence"
        }
}
