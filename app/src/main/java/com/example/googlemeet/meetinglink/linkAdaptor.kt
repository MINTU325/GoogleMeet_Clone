package com.example.googlemeet.meetinglink

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemeet.R

class linkAdaptor(
    val context: Context,
    val idlist: MutableList<linkModel>,
) : RecyclerView.Adapter<linkHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): linkHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.link_item_layout, parent, false)
        return linkHolder(view)
    }

    override fun onBindViewHolder(holder: linkHolder, position: Int) {
        val list = idlist[position]
        holder.meetingid.text = list.meetid
    }

    override fun getItemCount(): Int {

        return idlist.size
    }
}

class linkHolder(view: View) : RecyclerView.ViewHolder(view) {


    var meetingid: TextView

    init {
        meetingid = view.findViewById(R.id.meetingitemlayoutid)
    }
}