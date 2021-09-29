package com.example.googlemeet.feedbackviewmodel

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