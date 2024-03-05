package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity()
data class Location (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val locationName: String,
    var rulerName: String


){
    var town: Boolean = (0..10).random() == 0 //fog 0
    var manor: Boolean = (0..5).random() == 0 //fog 0
    var locationLoyalty: Int = (50..70).random() //0-100 //fog 5
    var locationCoffer: Int = (10..30).random() //gold //fog 5
    var workersNatural: Int = (200..1000).random() //pop  //fog 1
    var workersExtraction: Int = (100..400).random() //pop //fog 1
    var workersProfs: Int = if (town) (50..300).random() else workersNatural/100//pop //fog 1
    var workersTraders: Int = if (town) (25..150).random() else workersNatural/50 //pop //fog 1
    var workersCommodities: Int = (100..300).random() //pop //fog 1
    var civilLvl: Int = (10..20).random() //0-100 //fog 3
    var militaryLvl: Int = if (id in 1..5) (10..30).random() else (0..10).random() //0-100 //fog 3 //if id on frontier
    var militia: Int = if (town) (10..20).random() else 0
    var feudalPower: Int = if (manor) (50..100).random() else 0
    var civilFunds: Int = 0 //gold //fog 4
    var militaryFunds: Int = 200 //gold //fog 4
    var fertility: Int = (1..10).random() //1-10 //fog 2
    var abundance: Int = (1..10).random() //1-10 //fog 2
    var starvation: Int = 0 //pop //fog 1
    var climateLastYear: Int = (-5..5).random() //-5 to 5 //fog 1
    var barbarianRaids: Int = 0 //0-100 //fog 1
    var crimeInfluence: Int = (1..50).random() //0-100 //fog 4
    var churchInfluence: Int = (40..80).random() //0-100 //fog 4
    var fogOfWar: Int = 0//0-5
    var plannedCivilFunds = 0 //fog 0
    var plannedMilitaryFunds = 0 //fog 0
    var taxesLastYear = 0 //fog 0

    fun showAllData(): String{
        var s = "Location: \n $locationName ruled by $rulerName. Town: $town with $militia. Manor: $manor with $feudalPower.  Loyalty: $locationLoyalty"
        s+="\n Workers: Nat $workersNatural Ex $workersExtraction Prof $workersProfs Trade $workersTraders Comm $workersCommodities"
        s+="\n Civlvl $civilLvl Millvl $militaryLvl civFunds $civilFunds Milfunds$militaryFunds"
        s+= "\n Fert $fertility Abun $abundance Starv $starvation Climate $climateLastYear Fog $fogOfWar"
        s+= "\n Planned civ/mil funds: $plannedCivilFunds/$plannedMilitaryFunds, Last taxes: $taxesLastYear."
        return s
    }
    val workersAll: Int
        get() = workersNatural+workersCommodities+workersExtraction+workersProfs+workersTraders
    val workersExeptNatural: Int
        get() = workersCommodities+workersExtraction+workersProfs+workersTraders
}
