package com.example.googlemeet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemeet.meetinglink.linkModel

class linkAdaptor(
    val context: Context,
    val idlist: MutableList<linkModel>,
    val listener: MainActivity

) : RecyclerView.Adapter<linkHolder>() {

    val linkModel = linkModel()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): linkHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.link_item_layout, parent, false)
        return linkHolder(view)
    }

    override fun onBindViewHolder(holder: linkHolder, position: Int) {
        val list = idlist[position]
        holder.meetingid.text = list.meetid
        holder.reJoin.setOnClickListener {
            listener.rejoin(
                linkModel,
                position
            )

        }


    }

    override fun getItemCount(): Int {

        return idlist.size
    }
}

class linkHolder(view: View) : RecyclerView.ViewHolder(view) {


    var meetingid: TextView
    var reJoin: TextView

    init {
        meetingid = view.findViewById(R.id.meetingitemlayoutid)
        reJoin = view.findViewById(R.id.rejoin)
    }
}
