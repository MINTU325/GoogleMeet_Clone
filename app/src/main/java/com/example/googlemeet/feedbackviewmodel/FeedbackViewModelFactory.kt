package com.example.googlemeet.feedbackviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FeedbackViewModelFactory(val repo: FeedbackRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedbackViewModel(repo) as T
    }

}