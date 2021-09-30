package com.example.googlemeet.repository

import com.example.googlemeet.model.FeedBack
import com.example.googlemeet.model.FeedbackDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackRepo(val feedbackDao: FeedbackDao) {


    fun addFeedbackRoom(feedBack: FeedBack) {
        CoroutineScope(Dispatchers.IO).launch {
            feedbackDao.addFeedback(feedBack)
        }
    }
}