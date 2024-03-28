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
        var seed = (1..16).random()
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
        var lawsAttitude = if (seed==6) (1..2).random() else if (seed==7) (-2..-1).random() else 0

        ///SEED 8 Traders attraction
        var tradersAttraction = seed==8

        ///SEED 9 Prof attraction
        var profAttraction = seed==9

        ///SEED 10 Agri attraction
        var agriAttraction = seed==10

        ///SEED 11 Commodity attraction
        var commodityAttraction = seed==11

        ///SEED 12 Extraction attraction
        var extractionAttraction = seed==12

        ///SEED 13 Religious//something with church
        var religious = seed==13

        ///SEED 14 Vigilant //something with raids resolve?
        var vigilant = seed==14

        //SEED 15 Cruel
        var giveFromCofferIfStarvation = seed!=14

        //SEED 16 Embezzle
        var embezzlement = if (seed==16) arrayListOf(1,1,1,1,1,2,2,2,2,3,3,3,4,4,5,6).random() else 0

        fun showFullInfo():String {
               var s = "$id. $rulerName, "
                when (seed) {
                    1-> s+= "Stinginess"
                    2-> s+= "Greed $decreaseCofferIncoming"
                    3-> s+= "Corruption $fundsDecrease"
                    4-> s+=  "Military enthusiast"
                    5-> s+= "Climate adapting"
                    6-> s+= "Lawful"
                    7-> s+= "Perpetrator"
                    8-> s+= "Traders attraction"
                    9-> s+= "Prof attraction"
                    10-> s+= "Agri attraction"
                    11-> s+= "Commodity attraction"
                    12-> s+= "Extraction attraction"
                    13-> s+= "Religious"
                    14-> s+= "Vigilant"
                    15-> s+= "Cruel"
                    16-> s+= "Embezzle $embezzlement"

                }
                return  s
        }
}
