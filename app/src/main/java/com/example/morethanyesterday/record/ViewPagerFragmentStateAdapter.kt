package com.example.morethanyesterday.record

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.morethanyesterday.record.fragments.*

class ViewPagerFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    // ViewPager에서 이동할 Fragment list
    private var fragments: ArrayList<Fragment> = arrayListOf(
        AllFragment(),
        BackFragment(),
        ChestFragment(),
        AbsFragment(),
        AerobicFragment(),
        BicepFragment(),
        LowerBodyFragment(),
        ShoulderFragment(),
        TrapeziusFragment(),
        TricepsFragment(),

    )

    // FragmentStateAdapter 상속 시 무조건 override 해야하는 fun
    override fun getItemCount(): Int {
        return fragments.size
    }

    // FragmentStateAdapter 상속 시 무조건 override 해야하는 fun (View Pager의 position에 해당하는 fragment return)
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}