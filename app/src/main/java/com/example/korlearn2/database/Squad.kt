package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Squad (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var rulerName: String,
    var number: Int


){
    var locationId = -1
    fun showAllData(): String {
        var s = ""
        s+= "Squad Id $id, leader: $rulerName, $number soldiers"
        if (locationId!=-1) s+= ", located in $locationId"
        return s
    }
}
