package com.example.googlemeet

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.googlemeet.databinding.ActivityMainscreenBinding
import com.example.googlemeet.sudarshan.PagerAdaptor
import com.example.googlemeet.sudarshan.ViewPagerFragment1
import com.example.googlemeet.sudarshan.ViewPagerFragment2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_mainscreen.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.view.*
import kotlinx.android.synthetic.main.new_meeting_dialog.view.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainscreenBinding // binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainscreenBinding.inflate(layoutInflater) // binding

        setContentView(binding.root) // binding

        // Nav Bar Code
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Nav Bar code
        binding.navview1.setNavigationItemSelectedListener {
                if(it.itemId ==R.id.Setting) run { ->
                    Toast.makeText(applicationContext,
                        "SettingClicked",
                        Toast.LENGTH_SHORT).show()
                } else if (it.itemId== R.id.Feedback) run { ->
                    intent = Intent(this, FeedBackActivity::class.java)
                    startActivity(intent)
                } else {
                    if (it.itemId == R.id.Help) run { ->
                        Toast.makeText(applicationContext,
                            "HelpClicked",
                            Toast.LENGTH_SHORT).show()
                    }
            }
            true
        }

        ivmenu.setOnClickListener {
            drawerlayout.openDrawer(navview1)
        }

        // bottom sheet code
        binding.NewMeetingButton.setOnClickListener {
            val bottomDialog = BottomSheetDialog(
                this@MainActivity, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottom_sheet_activity,
                findViewById(R.id.bottomsheet) as LinearLayout?
            )



            bottomSheetView.close.setOnClickListener {
                bottomDialog.dismiss()
            }

            bottomSheetView.getmettinglink.setOnClickListener {
                val mDialogview = LayoutInflater.from(this).inflate(R.layout.new_meeting_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogview)
                val mAlertDialog = mBuilder.show()
                mDialogview.closeBtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }
                bottomDialog.dismiss()
            }
            bottomDialog.setContentView(bottomSheetView)
            bottomDialog.show()
        }

        // Get Meeting Link Code

        // fragments
        val fragments: ArrayList<Fragment> = arrayListOf(
            ViewPagerFragment1(),
            ViewPagerFragment2()
        )
        val adapter = PagerAdaptor(fragments, this)
        binding.viewPager2.adapter = adapter

        binding.indicator.setViewPager(viewPager2)

        binding.joinButton.setOnClickListener {
            val intent = Intent(this, JoinGoogleMeetActivity::class.java)
            startActivity(intent)
        }
    }


    // Nav Bar Code
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}