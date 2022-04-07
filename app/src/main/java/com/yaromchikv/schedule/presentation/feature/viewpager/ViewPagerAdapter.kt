package com.yaromchikv.schedule.presentation.feature.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(position + 1)
    }

    override fun getItemCount() = 7
}