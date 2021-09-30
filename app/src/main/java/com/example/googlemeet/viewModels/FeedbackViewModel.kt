package com.example.googlemeet.viewModels

import androidx.lifecycle.ViewModel
import com.example.googlemeet.model.FeedBack
import com.example.googlemeet.repository.FeedbackRepo

class FeedbackViewModel(val repo : FeedbackRepo) : ViewModel(){

    fun addTask(feedBack: FeedBack) {
        repo.addFeedbackRoom(feedBack)
    }

}