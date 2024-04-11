package com.game.northFrontier.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Location (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val locationName: String,
    var rulerName: String


){
    var town: Boolean = false//(0..10).random() == 0 //fog 0
    var manor: Boolean = false//(0..5).random() == 0 //fog 0
    var locationLoyalty: Int = (50..70).random() //0-100 //fog 5

    var depleted = 0

    var workersNatural: Int = 0//(200..1000).random() //pop  //fog 1
    var workersExtraction: Int = 0//(100..400).random() //pop //fog 1
    var workersProfs: Int = 0//if (town) (50..300).random() else workersNatural/100//pop //fog 1
    var workersTraders: Int = 0//if (town) (25..150).random() else workersNatural/50 //pop //fog 1
    var workersCommodities: Int = 0//(100..300).random() //pop //fog 1
    var civilLvl: Int = 0//(10..20).random() //0-100 //fog 3
    var militaryLvl: Int = 0//if (id in 1..5) (10..30).random() else (0..10).random() //0-100 //fog 3 //if id on frontier
    var militia: Int = 0//if (town) (10..20).random() else 0
    var feudalPower: Int = 0//if (manor) (50..100).random() else 0

    var fertility: Int = arrayListOf(1,1,1,1,1,1,2,2,2,3).random() //1-35 //fog 2
    var abundance: Int = arrayListOf(1,1,1,1,1,1,2,2,2,3).random() //1-3 //fog 2
    var climateLastYear: Int = arrayListOf(-5,-4,-3,-2,-2,-1,-1,-1,-1,0,0,0,0,0,0,1,1,1,1,2,2,3,4,5).random() //-5 to 5 //fog 1
    var barbarianRaids: Int = 0 //1-100 //fog 1
    var barbarianRaidsYearBefore: Int = 0 //0-100 //fog 1


    var fogOfWar: Int = 5//0-5, 6 - unreachable

    var taxesLastYear = 0 //fog 0
    var taxesBeforeLastYear = 0 //fog 0
    var incomingBarbarians = 0 ////fog 6
    var barbariansLastMonth = 0 //fog 1
    var naturalTaxesLastYear = 0 //fog 2
    var extractionTaxesLastYear = 0 //fog 2
    var commoditiesTaxesLastYear = 0 //fog 2
    var tradeTaxesLastYear = 0 //fog 2
    var professionsTaxesLastYear = 0 //fog 2
    var upkeepLastYear = 0 //fog 0
    var upkeepBeforeLastYear = 0 //fog 0
    var civUp = false //fog 6
    var civUpFunds = 0 //fog 6
    var milUp = false //fog 6
    var milUpFunds = 0 //fog 6
    var civUpkeep = 0 //fog 4
    var milUpkeep = 0 //fog 4
    var foodUpkeep = 0 //fog 4

    fun showAllData(): String{
        var s = "Location: $locationName, ID_$id,\n ruled by $rulerName."
        if (town) s+="\n Town with $militia militia"
        if (manor) s+="\n Manor with $feudalPower solders"
        s+= " \n\n  Loyalty: $locationLoyalty"
        s+="\n Workers: Nat $workersNatural Ex $workersExtraction Prof $workersProfs Trade $workersTraders Comm $workersCommodities ALL: $workersAll"
        s+="\n Civlvl $civilLvl Millvl $militaryLvl"
        s+= "\n Fert $fertility Abun $abundance Climate $climateLastYear Fog $fogOfWar"

        s+= "\n Taxes last year/before last year $taxesLastYear/$taxesBeforeLastYear"
        s+= "\n Taxes natural: $naturalTaxesLastYear. Taxes extraction: $extractionTaxesLastYear."
        s+= "\n Taxes trade: $tradeTaxesLastYear. Taxes prof: $professionsTaxesLastYear."
        s+= "\n Taxes commodities: $commoditiesTaxesLastYear. "
        s+= "\n Barbarians this/last year: $barbarianRaids/$barbarianRaidsYearBefore"
        s+= "\n Barbarians last month: $barbariansLastMonth"
        return s
    }
    fun showFogData(): String {
        if (depleted==1) return "Location: $locationName, ID_$id, is DEPLETED!"
        var s = "Location: $locationName, ID_$id, \nruled by $rulerName, infoLVL $fogOfWar"
        ///FOG==0
        if (town) s += "\n Town with $militia militia"
        if (manor) s += "\n Manor with $feudalPower solders"
        s += "\n\n Taxes last year $taxesLastYear gold"
        s += "\n Taxes before last year $taxesBeforeLastYear gold"
        s += "\n\n Upkeep last year $upkeepLastYear gold"
        s += "\n Upkeep before last year $upkeepBeforeLastYear gold"

        ///FOG==1
        if (fogOfWar >= 1) {
            s += "\n\n Barbarians razed $barbarianRaids % this year."
            s += "\n Barbarians razed  $barbarianRaidsYearBefore % last year."
            s += "\n Barbarians attacked last month: $barbariansLastMonth mens"
            s += "\n Climate last year: $climateLastYear"
            s += "\n\n Population: $workersAll \n " +
                    "Agriculture: $workersNatural\n Extraction: $workersExtraction\n Professions $workersProfs\n Traders: $workersTraders\n Commodities: $workersCommodities  "
        }
        ///FOG==2
        if (fogOfWar >= 2) {
            s += "\n\n Fertility: $fertility \n Natural abundance: $abundance "
            s += "\n "
            s += "\n Taxes natural: $naturalTaxesLastYear gold.\n Taxes extraction: $extractionTaxesLastYear gold."
            s += "\n Taxes trade: $tradeTaxesLastYear gold.\n Taxes prof: $professionsTaxesLastYear gold."
            s += "\n Taxes commodities: $commoditiesTaxesLastYear gold. "
        }
        ///FOG==3
        if (fogOfWar >= 3) {
            s += "\n\n Civilian infrastructure: $civilLvl\n Military infrastructure: $militaryLvl"
        }
        ///FOG==4
        if (fogOfWar >= 4) {
            s += "\n\n Upkeep: civ/mil/food: $civUpkeep/$milUpkeep/$foodUpkeep"

        }
        ///FOG==5
        if (fogOfWar >= 5) {
            s += " \n \nLoyalty: $locationLoyalty% ."
        }




        return s


    }
    fun generate() {
        val seed = (1..100).random()
        when (seed) {
            in 1..5 -> {
                workersNatural = (200..400).random()
            }
            in 6..10 -> {
                workersNatural = (250..350).random()
                workersExtraction = (50..100).random()
            }
            in 11..15 -> {
                workersNatural = (250..350).random()
                workersCommodities = (50..100).random()
            }
            in 16..35 -> {
                workersNatural = (400..800).random()
            }
            in 36..40 -> {
                workersNatural = (800..1500).random()
            }
            in 41..45 -> {
                workersNatural = (550..750).random()
                workersCommodities = (100..150).random()
            }
            in 46..50 -> {
                workersNatural = (550..750).random()
                workersExtraction = (100..150).random()
            }
            in 51..55 -> {
                workersNatural = (650..850).random()
                workersExtraction = (100..150).random()
                workersCommodities = (100..150).random()
            }
            in 56..60 -> {
                workersNatural = (650..850).random()
                workersCommodities = (200..350).random()
            }
            in 61..65 -> {
                workersNatural = (650..850).random()
                workersExtraction = (200..350).random()
            }
            in 66..80 -> {
                workersNatural = (300..500).random()
            }
            in 81..94 -> {
                manor = true
                feudalPower = (30..150).random()
                workersNatural = (200..1000).random()
                workersTraders = (10..20).random() + workersNatural/50
                workersProfs = (20..30).random() + workersNatural/100
            }
            in 95..96 -> {
                town = true
                militia = (40..50).random()
                workersNatural = (600..1000).random()
                workersTraders = (40..60).random()
                workersProfs = (80..100).random()
            }
            in 97..98 -> {
                town = true
                militia = (30..40).random()
                workersNatural = (600..800).random()
                workersTraders = (30..50).random()
                workersProfs = (50..80).random()
            }
            in 99..100 -> {
                town = true
                militia = (25..35).random()
                workersNatural = (500..600).random()
                workersTraders = (30..40).random()
                workersProfs = (30..50).random()
            }
        }
        if (seed in 1..80) {
            workersTraders = (workersNatural+workersCommodities+workersExtraction)/50
            workersProfs = (workersNatural+workersCommodities*3+workersExtraction*10)/100
            civilLvl = (1..30).random()
            militaryLvl = (1..25).random()
        }
        if (seed in 81..100) abundance = 1
        if (seed in 81..94) {
            civilLvl = (20..30).random()
            militaryLvl = (25..50).random()

        }
        if (seed in 95..100) {
            civilLvl = (20..40).random()
            militaryLvl = (15..35).random()

        }

    }
    val workersAll: Int
        get() = workersNatural+workersCommodities+workersExtraction+workersProfs+workersTraders
    val workersExeptNatural: Int
        get() = workersCommodities+workersExtraction+workersProfs+workersTraders

    val totalUpkeep: Int
        get() = foodUpkeep+civUpkeep+milUpkeep
    private fun deplete() {
        workersNatural=0
        workersExtraction=0
        workersTraders=0
        workersCommodities=0
        workersProfs=0
        civilLvl=1
        town=false
        manor=false
        militia=0
        feudalPower=0

    }
    fun decreasePop(x1: Int) {
        var x =x1
        if (x>= workersAll) {
            depleted=1
            deplete()
            return
        }
        if (workersNatural>=x) {
            workersNatural-=x
            return
        } else {
            x-=workersNatural
            workersNatural=0
        }
        if (workersCommodities>=x){
            workersCommodities-=x
            return
        } else {
            x-=workersCommodities
            workersCommodities=0
        }
        if (workersExtraction>=x){
            workersExtraction-=x
            return
        } else {
            x-=workersExtraction
            workersExtraction=0
        }
        if (workersProfs>=x){
            workersProfs-=x
            return
        } else {
            x-=workersProfs
            workersProfs=0
        }
        if (workersTraders>=x){
            workersTraders-=x
            return
        } else {
            depleted=1
            deplete()
        }
    }
    fun changeMilLvl(x: Int) {
        militaryLvl+=x
        if (militaryLvl<1) militaryLvl = 1
        if (militaryLvl>100) militaryLvl = 100
    }
    fun changeCivLvl(x: Int) {
        civilLvl+=x
        if (civilLvl<1) civilLvl = 1
        if (civilLvl>100) civilLvl = 100
    }
}

