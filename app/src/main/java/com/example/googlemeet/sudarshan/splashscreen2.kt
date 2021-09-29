package com.example.googlemeet.sudarshan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.googlemeet.R
import kotlinx.android.synthetic.main.activity_splashscreen2.*

class splashscreen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen2)
        imageviewsplashscreen.animate().translationX(5000f).translationY(5000f).startDelay = 5000

        Handler().postDelayed({
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
            finish()

            //the current activity will get finished.
        }, 5000)
    }
}