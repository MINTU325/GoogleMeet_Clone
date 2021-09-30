package com.example.googlemeet.feedbackviewmodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface FeedbackDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFeedback(feedBack: FeedBack)
}