package com.example.korlearn2.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class YourStats (
    @PrimaryKey
    val name: String = "YourName"

){
    var monthNumber = 1
    var yearNumber = 1000
    var gold: Int = 10000
    var age: Int = (58..60).random()
    var loan: Int = 0
    var taxesBeforeLastYear: Int = 0
    var taxesLastYear: Int = 0
    var loanPercent: Int = 0
    var spyOnLocation: Int = -1
    fun showAllData(): String{
        return  "Year/Month: ${yearNumber}/${monthNumber} \n" +
                "Gold: $gold, Age: $age, Loan: $loan\n Taxes last year = $taxesLastYear \n" +
                " Taxes before last year = $taxesBeforeLastYear" +
                "\n Percent last month =  $loanPercent" +
                "\nSpy on location # $spyOnLocation"
    }
}
