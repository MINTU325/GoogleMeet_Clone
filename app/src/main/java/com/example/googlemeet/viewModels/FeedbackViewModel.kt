package com.example.googlemeet.feedbackviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.googlemeet.model.linkModel
import com.example.googlemeet.model.FeedBack

class FeedbackViewModel(val repo : FeedbackRepo) : ViewModel(){

    fun addTask(feedBack: FeedBack) {
        repo.addFeedbackRoom(feedBack)
    }


    fun addid(linkModel: linkModel){
        repo.addmeetingid(linkModel)
    }

    fun getTasksFromDB(): LiveData<List<linkModel>> {
        return repo.getallTask()
    }
}