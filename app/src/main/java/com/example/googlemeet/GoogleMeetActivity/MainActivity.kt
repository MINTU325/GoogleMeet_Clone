package com.example.googlemeet.GoogleMeetActivity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlemeet.FeedBackActivity
import com.example.googlemeet.R
import com.example.googlemeet.databinding.ActivityMainscreenBinding
import com.example.googlemeet.NavDrawer.PagerAdaptor
import com.example.googlemeet.NavDrawer.ViewPagerFragment1
import com.example.googlemeet.NavDrawer.ViewPagerFragment2
import com.example.googlemeet.feedbackviewmodel.*
import com.example.googlemeet.meetinglink.linkAdaptor
import com.example.googlemeet.meetinglink.linkModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_mainscreen.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.view.*
import kotlinx.android.synthetic.main.new_meeting_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainscreenBinding // binding


    private var tasklist = mutableListOf<linkModel>()
    lateinit var roomDataBase: FeedbackRoomDataBase
    lateinit var feedbackDao: FeedbackDao
    lateinit var linkAdaptor: linkAdaptor

    //MVVM
    lateinit var viewmodel: FeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainscreenBinding.inflate(layoutInflater) // binding

        setContentView(binding.root) // binding

        //MVVM
        roomDataBase = FeedbackRoomDataBase.getDataBaseObject(this)
        feedbackDao = roomDataBase.getFeedbackDao()
        val repo = FeedbackRepo(feedbackDao)
        val viewModelFactory = FeedbackViewModelFactory(repo)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(FeedbackViewModel::class.java)

        viewmodel = ViewModelProviders.of(this).get(FeedbackViewModel::class.java)

        // Password Generator
        var passwordGenerator = PasswordGenerator()

        // Nav Bar Code
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Nav Bar code
        binding.navview1.setNavigationItemSelectedListener {
                if(it.itemId == R.id.Setting) run { ->
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
            bottomSheetView.startmeeting.setOnClickListener {
                val intent  = Intent(this, NewMeetActivity::class.java)
                startActivity(intent)
            }

            bottomSheetView.getmettinglink.setOnClickListener {
                val mDialogview = LayoutInflater.from(this).inflate(R.layout.new_meeting_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogview)
                val mAlertDialog = mBuilder.show()
                val password = passwordGenerator.generatelinkid(length = 12, specialWord = "-")
                mDialogview.tvmeetingId.text = password

                //MVVM REcycler VIew
                var idgenearate = linkModel(password)
                viewmodel.addid(idgenearate)


                mDialogview.closeBtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }
                bottomDialog.dismiss()
            }
            bottomDialog.setContentView(bottomSheetView)
            bottomDialog.show()
        }

        // link Adaptor
        linkAdaptor = linkAdaptor(this, tasklist)
        recyclerView.adapter = linkAdaptor
        recyclerView.layoutManager = LinearLayoutManager(this)

        // linkmodel
        viewmodel.getTasksFromDB().observe(this, Observer {
            if(it.size!=0){
                recyclerView.visibility = View.VISIBLE
                tvchecking.visibility = View.VISIBLE
                viewPager2.visibility = View.GONE
            }else {
                recyclerView.visibility = View.INVISIBLE
                tvchecking.visibility = View.INVISIBLE
                viewPager2.visibility = View.VISIBLE

            }
            tasklist.clear()
            tasklist.addAll(it)
            tasklist.reverse()
            CoroutineScope(Dispatchers.Main).launch {
                linkAdaptor.notifyDataSetChanged()
            }
        })


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