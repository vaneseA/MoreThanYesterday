package com.example.morethanyesterday.record


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.morethanyesterday.record.fragments.*

private const val NUM_TABS = 10

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllFragment()
            1 -> return BackFragment()
            2 -> return ChestFragment()
            3 -> return BicepFragment()
            4 -> return TricepsFragment()
            5 -> return TrapeziusFragment()
            6 -> return ShoulderFragment()
            7 -> return LowerBodyFragment()
            8 -> return AbsFragment()
            9 -> return AerobicFragment()

        }
        return AllFragment()
    }
}