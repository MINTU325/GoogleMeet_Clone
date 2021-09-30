package com.example.googlemeet.feedbackviewmodel

import androidx.lifecycle.ViewModel

class FeedbackViewModel(val repo : FeedbackRepo) : ViewModel(){

    fun addTask(feedBack: FeedBack) {
        repo.addFeedbackRoom(feedBack)
    }

}