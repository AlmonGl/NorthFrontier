package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Squad (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var number: Int,
    val squadName: String


){
    var locationId = -1

    //leader
    var seed = 0
    var northPath = false  //don't get lost in the north //0 or 1
    var carefull=false //don't engage if <3  //0 or 1
    var woundedTreatment =0 //decrease deaths //1 to 4
    var tactics = 0 //buff strength  //1 to 4
    var trainingSkill = 0//buff training //1 to 4
    var managerSkills = 0 //decrease maintenance //1 to 4




    fun showAllData(): String {
        var s = ""
        s+= "Squad Id $id, \n $number soldiers, $strength milPower"
        if (locationId!=-1) s+= "\n located in $locationId"
        s+= "\n EXP: $experience, training: $inAction\n"
        return s
    }
    fun showSquadLeaderData(): String {
        var s= "Leader of $squadName is:\n"
             if (northPath) s+=  "pathfinder $northPath\n"
            if (carefull) s+="careful $carefull\n"
           s+=     "Tactic: $tactics\n" +
                "Training: $trainingSkill\n" +
                "Managing: $managerSkills\n" +
                "Med: $woundedTreatment\n"
        return s
    }
    fun training(){
        if (!inAction) {
            experience+=trainingSkill
            if (experience>50f) experience=50f
        } else {
            inAction=false
        }
    }
    fun applyCasualities(damage: Int){
        val x = damage/woundedTreatment
        val expDecrease: Float = number.toFloat()/x.toFloat()
        experience*=(1-1/expDecrease)
        if (experience<1f) experience=1f
    }
    var experience = arrayListOf(1f,1f,1f,2f,2f,2f,2f,3f,3f,3f,4f,4f,4f,5f,5f,5f,6f,6f,7f,7f,8f,9f,10f).random() + 10f
    var inAction = false
    val strength: Int
        get() = (number*(1+ experience/10)).toInt()
    fun assingNewLeader() {
        seed = (6..10).random()
        northPath = seed==10
        carefull = seed==9
        tactics = arrayListOf(1,1,1,1,1,1,1,1,2,2,2,3,3,4).random()
        trainingSkill= arrayListOf(1,1,1,1,1,1,1,1,2,2,2,3,3,4).random()
        managerSkills = arrayListOf(1,1,1,1,1,1,1,1,2,2,2,3,3,4).random()
        woundedTreatment = arrayListOf(1,1,1,1,1,1,1,1,2,2,2,3,3,4).random()
    }


}

