package com.example.googlemeet.meetinglink

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meeitngid")
data class linkModel(
    @ColumnInfo(name = "meetid") var meetid : String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}