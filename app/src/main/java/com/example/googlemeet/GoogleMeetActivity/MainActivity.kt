package com.example.googlemeet

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
import com.example.googlemeet.databinding.ActivityMainBinding
import com.example.googlemeet.feedbackviewmodel.*
import com.example.googlemeet.meetinglink.linkModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.view.*
import kotlinx.android.synthetic.main.new_meeting_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding // binding

    private var tasklist = mutableListOf<linkModel>()
    lateinit var roomDataBase: FeedbackRoomDataBase
    lateinit var feedbackDao: FeedbackDao
    lateinit var linkAdaptor: linkAdaptor


    //MVVM
    lateinit var viewmodel: FeedbackViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // binding

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
            if (it.itemId == R.id.Setting) run { ->
                Toast.makeText(applicationContext,
                    "SettingClicked",
                    Toast.LENGTH_SHORT).show()
            } else if (it.itemId == R.id.Feedback) run { ->
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



            //bootom sheet close
            bottomSheetView.close.setOnClickListener {
                bottomDialog.dismiss()
            }

            //bottom sheet meeting link
            bottomSheetView.getmettinglink.setOnClickListener {
                val mDialogview =
                    LayoutInflater.from(this).inflate(R.layout.new_meeting_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogview)
                val mAlertDialog = mBuilder.show()
                tvchecking.visibility = View.VISIBLE
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
            Log.d("kunal","${it.size}")
            if(it.size!=0){
                recyclerView.visibility = View.VISIBLE
                viewPager2.visibility = View.GONE
            }else {
                recyclerView.visibility = View.INVISIBLE
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
            val intent = Intent(this, joinActivity::class.java)
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