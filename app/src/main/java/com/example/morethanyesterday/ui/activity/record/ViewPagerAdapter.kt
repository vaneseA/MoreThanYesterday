package com.example.morethanyesterday.ui.activity.record


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.morethanyesterday.ui.fragments.*

private const val NUM_TABS = 11

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        var getFragment:Fragment? = null
        when (position) {
            0 -> getFragment = AllFragment()
            1 -> getFragment =  BackFragment()
            2 -> getFragment =  ChestFragment()
            3 -> getFragment =  BicepFragment()
            4 -> getFragment =  TricepsFragment()
            5 -> getFragment =  TrapeziusFragment()
            6 -> getFragment =  ShoulderFragment()
            7 -> getFragment =  LowerBodyFragment()
            8 -> getFragment =  AbsFragment()
            9 -> getFragment =  CardioFragment()
            10 -> getFragment =  RecordListFragment()

        }
        return getFragment!!
    }
}