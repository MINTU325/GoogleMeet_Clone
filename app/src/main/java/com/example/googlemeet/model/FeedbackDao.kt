package com.example.googlemeet.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.googlemeet.model.FeedBack

@Dao
interface FeedbackDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFeedback(feedBack: FeedBack)
}