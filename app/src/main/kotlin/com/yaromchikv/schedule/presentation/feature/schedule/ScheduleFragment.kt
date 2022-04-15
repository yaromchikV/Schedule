package com.yaromchikv.schedule.presentation.feature.schedule

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentScheduleBinding
import com.yaromchikv.schedule.presentation.MainViewModel
import com.yaromchikv.schedule.presentation.common.AccessRights
import com.yaromchikv.schedule.presentation.feature.viewpager.ViewPagerAdapter
import java.time.LocalDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val binding by viewBinding(FragmentScheduleBinding::bind)

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val scheduleViewModel by viewModel<ScheduleViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupObservers()

//        when (mainViewModel.accessRights) {
//            AccessRights.USER -> binding.settingsButton.isVisible = false
//            AccessRights.ADMIN -> binding.settingsButton.isVisible = true
//            else -> Unit
//        }

        binding.menuButton.setOnClickListener {
            findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToChangeGroupFragment())
        }
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@ScheduleFragment)
            currentItem = LocalDate.now().dayOfWeek.ordinal - 1
            offscreenPageLimit = 2
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.selectedGroup.collectLatest { group ->
                        group?.let {
                            binding.appBarTitle.text =
                                getString(R.string.group_name, group.name, group.speciality)
                        }
                    }
                }
                launch {
                    scheduleViewModel.events.collectLatest {
                        when (it) {
                            is ScheduleViewModel.Event.ChangeFragment -> {
                                binding.viewPager.currentItem = it.index
                            }
                        }
                    }
                }
            }
        }
    }
}