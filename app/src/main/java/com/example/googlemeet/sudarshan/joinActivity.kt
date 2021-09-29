package com.example.googlemeet.sudarshan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlemeet.R
import kotlinx.android.synthetic.main.activity_join.*

class joinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        backbtnMain.setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
    }
}