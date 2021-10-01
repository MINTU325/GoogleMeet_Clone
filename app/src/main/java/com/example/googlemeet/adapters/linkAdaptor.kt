package com.example.googlemeet.meetinglink

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemeet.R
import com.example.googlemeet.model.linkModel
import com.example.googlemeet.model.onClickListener
import kotlinx.android.synthetic.main.new_meeting_dialog.view.*

class linkAdaptor(
    val context: Context,
    val idlist: MutableList<linkModel>,
    val listener: onClickListener
) : RecyclerView.Adapter<linkHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): linkHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.link_item_layout, parent, false)
        return linkHolder(view)
    }

    override fun onBindViewHolder(holder: linkHolder, position: Int) {
        val list = idlist[position]
        holder.meetingid.text = list.meetid
        holder.createTime.text = list.time

        holder.meetingid.setOnClickListener {
            listener.onMeeting(list)
        }
    }

    override fun getItemCount(): Int {

        return idlist.size
    }
}

class linkHolder(view: View) : RecyclerView.ViewHolder(view) {


    var meetingid: TextView
    var createTime :TextView

    init {
        meetingid = view.findViewById(R.id.meetingitemlayoutid)
        createTime = view.findViewById(R.id.currenttimeanddate)
    }
}