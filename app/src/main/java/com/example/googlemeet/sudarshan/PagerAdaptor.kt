package com.example.googlemeet.sudarshan

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdaptor(
    val items: ArrayList<Fragment>,
    activity: MainScreenActivity,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}