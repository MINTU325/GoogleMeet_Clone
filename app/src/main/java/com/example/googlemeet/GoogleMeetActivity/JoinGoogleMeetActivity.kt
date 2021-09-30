package com.example.googlemeet.GoogleMeetActivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.googlemeet.databinding.ActivityJoinGoogleMeetBinding
import org.jitsi.meet.sdk.*
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

class JoinGoogleMeetActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinGoogleMeetBinding


    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinGoogleMeetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serverURL: URL
        serverURL = try {
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }



        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        registerForBroadcastMessages()
    }







    fun onButtonClick(v: View?) {
//            val editText = findViewById<EditText>(R.id.conferenceName)
        val text = binding.conferenceName.text.toString()
        if (text.length > 0) {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(text)
                // Settings for audio and video
                //.setAudioMuted(true)
                //.setVideoMuted(true)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(this, options)
        }
    }


    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()


        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.getType()) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i(
                    "Conference Joined with url%s", event.getData().get(
                        "url"
                    )
                )
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i(
                    "Participant joined%s", event.getData().get(
                        "name"
                    )
                )
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(org.webrtc.ContextUtils.getApplicationContext())
            .sendBroadcast(
                hangupBroadcastIntent
            )
    }




}