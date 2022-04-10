package com.yaromchikv.schedule.presentation.feature.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentScheduleBinding
import com.yaromchikv.schedule.presentation.feature.viewpager.ViewPagerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val binding by viewBinding(FragmentScheduleBinding::bind)

    private val viewModel by viewModel<ScheduleViewModel>()

    private val viewPagerAdapter by inject<ViewPagerAdapter> {
        parametersOf(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.apply {
            adapter = viewPagerAdapter
            currentItem = 0
            offscreenPageLimit = 2
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedGroup.collectLatest { group ->
                        if (group != null) {
                            val title = getString(R.string.group_name, group.name, group.speciality)
                            binding.appBarTitle.text = title
                        }
                    }
                }
            }
        }
    }

    fun openNext() {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
    }

    fun openPrev() {
        binding.viewPager.currentItem = binding.viewPager.currentItem - 1
    }
}