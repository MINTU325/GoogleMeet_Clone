package com.example.googlemeet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "meeitngid")
data class linkModel(

    @ColumnInfo(name = "meetid") var meetid : String,
    @ColumnInfo(name = "time") var time : String,
    @Ignore var isShow :Boolean = false,
    @Ignore var calendar : Boolean = false
) {
    constructor():this(",","",false,false){

    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}