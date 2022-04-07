package com.yaromchikv.schedule.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ActivityMainBinding
import com.yaromchikv.schedule.presentation.feature.viewpager.ViewPagerAdapter
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewPager.apply {
            adapter = viewPagerAdapter
            currentItem = 0
            offscreenPageLimit = 2
        }
    }

    fun openNext() {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
    }

    fun openPrev() {
        binding.viewPager.currentItem = binding.viewPager.currentItem - 1
    }
}