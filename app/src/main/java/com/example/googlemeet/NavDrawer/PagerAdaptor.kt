package com.example.googlemeet.NavDrawer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googlemeet.GoogleMeetActivity.MainActivity

class PagerAdaptor(
    val items: ArrayList<Fragment>,
    activity: MainActivity,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}