package com.example.googlemeet.feedbackviewmodel

import androidx.lifecycle.LiveData
import com.example.googlemeet.model.linkModel
import com.example.googlemeet.model.FeedBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackRepo(val feedbackDao: FeedbackDao) {


    fun addFeedbackRoom(feedBack: FeedBack) {
        CoroutineScope(Dispatchers.IO).launch {
            feedbackDao.addFeedback(feedBack)
        }
    }

   fun addmeetingid(linkModel: linkModel){
       CoroutineScope(Dispatchers.IO).launch {
           feedbackDao.addmeetingid(linkModel)
       }
   }

    fun getallTask(): LiveData<List<linkModel>> {
        return feedbackDao.getAllTask()
    }
}