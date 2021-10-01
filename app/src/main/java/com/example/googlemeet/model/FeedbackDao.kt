package com.example.googlemeet.feedbackviewmodel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googlemeet.meetinglink.linkModel
import com.example.googlemeet.model.FeedBack

@Dao
interface FeedbackDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFeedback(feedBack: FeedBack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addmeetingid(linkModel: linkModel)

    @Query("select * from meeitngid")
    fun getAllTask() : LiveData<List<linkModel>>
}