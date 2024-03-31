package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Squad (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var number: Int


){
    var locationId = -1
    var seed = (1..10).random()

    //dont get lost in the north
    //dont engage if <3
    fun showAllData(): String {
        var s = ""
        s+= "Squad Id $id, \n $number soldiers, $strength milPower"
        if (locationId!=-1) s+= "\n located in $locationId"
        s+= "\n EXP: $experience, training: $inAction"
        return s
    }
    var experience = 1f
    var inAction = false
    val strength: Int
        get() = (number*(1+ experience/10)).toInt()


}
