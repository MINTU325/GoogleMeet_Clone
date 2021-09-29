package com.example.googlemeet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.googlemeet.feedbackviewmodel.*
import com.example.googlemeet.databinding.ActivityFeedBackBinding
import org.jetbrains.anko.toast

class FeedBackActivity : AppCompatActivity() {

    lateinit var roomDataBase: FeedbackRoomDataBase
    lateinit var feedbackDao: FeedbackDao
    lateinit var viewmodel: FeedbackViewModel

    lateinit var binding: ActivityFeedBackBinding // binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedBackBinding.inflate(layoutInflater) // binding

        setContentView(binding.root) // binding

        roomDataBase = FeedbackRoomDataBase.getDataBaseObject(this)
        feedbackDao = roomDataBase.getFeedbackDao()
        val repo = FeedbackRepo(feedbackDao)
        val viewModelFactory = FeedbackViewModelFactory(repo)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(FeedbackViewModel::class.java)

        binding.btnclosefeedback.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.forwardtoclose.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            var adding = FeedBack(binding.etfeedback.text.toString() + " from the mail id " + binding.feedloginname.text.toString())
            viewmodel.addTask(adding)

            Toast.makeText(this, "Thank you for the feedback will look into it ", Toast.LENGTH_SHORT).show()

            startActivity(intent)
        }

    }
}