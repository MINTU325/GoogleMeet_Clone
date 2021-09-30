package com.example.googlemeet.sudarshan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.googlemeet.JoinGoogleMeetActivity
import com.example.googlemeet.R
import com.example.googlemeet.databinding.ActivityMainscreenBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_mainscreen.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.view.*

class MainScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainscreenBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navview1.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Setting -> Toast.makeText(applicationContext,
                    "settingclcked",
                    Toast.LENGTH_SHORT).show()
            }
            true
        }

        NewMeetingButton.setOnClickListener {
            val bottomDialog =BottomSheetDialog(
                this@MainScreenActivity, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                    R.layout.bottom_sheet_activity,
                findViewById(R.id.bottomsheet) as LinearLayout?
            )

            bottomSheetView.close.setOnClickListener {
                bottomDialog.dismiss()
            }
            bottomSheetView.startmeeting.setOnClickListener {
                val intent = Intent(this, NewMeetActivity::class.java)
                startActivity(intent)
            }

            bottomDialog.setContentView(bottomSheetView)
            bottomDialog.show()
        }
        // Start in gmeet


        val fragments : ArrayList<Fragment> = arrayListOf(
            ViewPagerFragment1(),
            ViewPagerFragment2()
        )

        val adapter = PagerAdaptor(fragments,this)
        viewPager2.adapter = adapter
        indicator.setViewPager(viewPager2)

        joinButton.setOnClickListener {
            val intent = Intent(this, JoinGoogleMeetActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}