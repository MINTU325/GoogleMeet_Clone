package com.example.googlemeet.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.googlemeet.feedbackviewmodel.FeedbackRepo
import com.example.googlemeet.feedbackviewmodel.FeedbackViewModel


class FeedbackViewModelFactory(val repo: FeedbackRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedbackViewModel(repo) as T
    }

}