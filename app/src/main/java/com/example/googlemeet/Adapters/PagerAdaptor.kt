package com.example.googlemeet.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googlemeet.MainScreenActivity

class PagerAdaptor(
    private val items: ArrayList<Fragment>,
    activity: MainScreenActivity,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}