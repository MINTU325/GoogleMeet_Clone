package com.example.googlemeet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.googlemeet.Views.MainActivity
import com.example.googlemeet.databinding.ActivityFeedBackBinding
import com.example.googlemeet.feedbackviewmodel.FeedbackDao
import com.example.googlemeet.feedbackviewmodel.FeedbackRepo
import com.example.googlemeet.feedbackviewmodel.FeedbackRoomDataBase
import com.example.googlemeet.feedbackviewmodel.FeedbackViewModel
import com.example.googlemeet.model.FeedBack
import com.example.googlemeet.viewModels.FeedbackViewModelFactory
import kotlinx.android.synthetic.main.activity_feed_back.*

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

        var email = intent.getStringExtra("emailname")
        feedloginname.text = email

        binding.btnclosefeedback.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        binding.forwardtoclose.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            var adding = FeedBack(binding.etfeedback.text.toString() + " from the mail id " + binding.feedloginname.text.toString())
            viewmodel.addTask(adding)

            if(etfeedback.text.toString().length >0) {
                Toast.makeText(this, "Thank you for the feedback will look into it ", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else {
                Toast.makeText(this, "please provide the feedback", Toast.LENGTH_SHORT).show()
            }
        }

    }
}