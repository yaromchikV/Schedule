package com.yaromchikv.schedule.presentation.feature.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(index: Int): Fragment {
        return PageFragment.newInstance(index)
    }

    override fun getItemCount() = 7
}