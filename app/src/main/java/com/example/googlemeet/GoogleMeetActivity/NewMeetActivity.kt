package com.example.googlemeet.GoogleMeetActivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.googlemeet.databinding.ActivityJoinGoogleMeetBinding
import org.jitsi.meet.sdk.BroadcastEvent
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

class NewMeetActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinGoogleMeetBinding
    lateinit var inviteCode: String


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
        onButtonClickNewMeeting()

    }


    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, intentFilter)
    }

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


    fun onButtonClickNewMeeting() {
        var passsGen= passsGen()
        var inviteLink = passsGen.generatedL(12)
//        val code = "code123"
        if (inviteLink.length > 0) {
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(inviteLink)
                // Settings for audio and video
                //.setAudioMuted(true)
                //.setVideoMuted(true)
                .build()
            JitsiMeetActivity.launch(this, options)
        }
    }

}
    class passsGen {

        private val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        fun generatedL(length :Int) : String{
            val builder = StringBuilder(length)
            for( x in 0 until length){
                val random = (characters.indices).random()
                builder.append(characters[random])
            }

            builder.insert((0 until length).random(),"/")
            return builder.toString()
        }
}