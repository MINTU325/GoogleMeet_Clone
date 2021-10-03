package com.example.googlemeet.Views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.example.googlemeet.FeedBackActivity
import com.example.googlemeet.R
import com.example.googlemeet.databinding.ActivityMainscreenBinding
import com.example.googlemeet.NavDrawer.PagerAdaptor
import com.example.googlemeet.NavDrawer.ViewPagerFragment1
import com.example.googlemeet.NavDrawer.ViewPagerFragment2
import com.example.googlemeet.feedbackviewmodel.*
import com.example.googlemeet.meetinglink.linkAdaptor
import com.example.googlemeet.model.linkModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_mainscreen.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_activity.view.*
import kotlinx.android.synthetic.main.new_meeting_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.googlemeet.viewModels.FeedbackViewModelFactory

import android.content.ComponentName
import com.example.googlemeet.utils.PasswordGenerator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.link_item_layout.*
import kotlinx.android.synthetic.main.link_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), onClickListener{

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainscreenBinding // binding

    // google auth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 100

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
        val passwordGenerator = PasswordGenerator()

        // Nav Bar Code
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //profile image
        val image = intent.getStringExtra("photo")
        Glide.with(applicationContext).load(image).into(profileimage)

        // Nav Bar code
        binding.navview1.setNavigationItemSelectedListener {
            if (it.itemId == R.id.Setting) run { ->
                Toast.makeText(applicationContext,
                    "SettingClicked",
                    Toast.LENGTH_SHORT).show()
            } else if (it.itemId == R.id.Feedback) run { ->
                var emailname = intent.getStringExtra("email")
                intent = Intent(this, FeedBackActivity::class.java)
                intent.putExtra("emailname",emailname)
                startActivity(intent)
            } else {
                if (it.itemId == R.id.Help) run { ->
                    Toast.makeText(applicationContext,
                        "HelpClicked",
                        Toast.LENGTH_SHORT).show()
                }
                if(it.itemId == R.id.Setting) run { ->
                    intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
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
                val intent = Intent(this, NewMeetActivity::class.java)
                startActivity(intent)
            }
            bottomSheetView.schedule.setOnClickListener {
                val cn: ComponentName
                val i = Intent()
                cn = ComponentName("com.google.android.calendar",
                    "com.android.calendar.LaunchActivity")
                i.component = cn
                startActivity(i)
            }

            bottomSheetView.getmettinglink.setOnClickListener {
                val mDialogview =
                    LayoutInflater.from(this).inflate(R.layout.new_meeting_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogview)
                val mAlertDialog = mBuilder.show()

                //Random password generator
                val password = passwordGenerator.generatelinkid(length = 12, specialWord = "-")
                mDialogview.tvmeetingId.text = password

                //CopyBoard Text
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =ClipData.newPlainText("EditText",mDialogview.tvmeetingId?.getText().toString())
                clipboard.setPrimaryClip(clip)

                Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()

                // date and time display
                var calendar = Calendar.getInstance()
                var simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                var data = simpleDateFormat.format(calendar.time)

                //MVVM REcycler VIew
                var generate = linkModel(password, data)
                viewmodel.addid(generate)


                mDialogview.closeBtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }

                mDialogview.btnlinkshare.setOnClickListener {
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\n Here is the Linkid \n\n"
                        shareMessage =
                            """
                            ${shareMessage}Here is the Meeting link to join with the fellow people $password
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                    true
                }
                bottomDialog.dismiss()
            }
            bottomDialog.setContentView(bottomSheetView)
            bottomDialog.show()
        }

        // link Adaptor
        linkAdaptor = linkAdaptor(this, tasklist,this)
        recyclerView.adapter = linkAdaptor
        recyclerView.layoutManager = LinearLayoutManager(this)

        // linkmodel
        viewmodel.getTasksFromDB().observe(this, Observer {
            if (it.size != 0) {
                recyclerView.visibility = View.VISIBLE
                tvchecking.visibility = View.VISIBLE
                viewPager2.visibility = View.GONE
            } else {
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

        // meeting itemlayout
        val mMeetingLayout  =
            LayoutInflater.from(this).inflate(R.layout.link_item_layout, null)

        mMeetingLayout.rejoin.setOnClickListener{
            val intent = Intent(this, NewMeetActivity::class.java)
            startActivity(intent)
        }

        binding.profileimage.setOnClickListener {
            googleSignIN()
        }
    }


    // google sign in
    private fun googleSignIN() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val account = GoogleSignIn.getLastSignedInAccount(this)

//        binding.button.setSize(SignInButton.SIZE_STANDARD)
        binding.profileimage.setOnClickListener {
            signIn()

        }


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personphoto = acct.photoUrl.toString()

                Toast.makeText(this, "Welcome " + personName, Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("photo",personphoto)
//                startActivity(intent)
            }
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }

    }


    // Nav Bar Code
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // onclickListener
    override fun onMeeting(linkModel: linkModel) {
        val intent = Intent(this, NewMeetActivity::class.java)
        startActivity(intent)
        CoroutineScope(Dispatchers.Main).launch {
            linkAdaptor.notifyDataSetChanged()
        }
    }

    override fun rejoin(linkModel: linkModel) {
      fun onMeeting(linkModel: linkModel) {
        rejoin.visibility = View.VISIBLE
        val intent = Intent(this, NewMeetActivity::class.java)
        startActivity(intent)
    }
}