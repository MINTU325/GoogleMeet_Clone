package com.example.googlemeet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Feedback")
class FeedBack (@ColumnInfo (name = "feedback")var feedBack:String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}