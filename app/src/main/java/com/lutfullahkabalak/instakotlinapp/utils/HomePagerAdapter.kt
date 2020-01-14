package com.lutfullahkabalak.instakotlinapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class HomePagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mFragmentList:ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment:Fragment){
        mFragmentList.add(fragment)
    }
}