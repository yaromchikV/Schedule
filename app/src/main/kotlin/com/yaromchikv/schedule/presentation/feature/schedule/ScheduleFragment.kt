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
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ModifyMode
import com.yaromchikv.schedule.presentation.feature.viewpager.ViewPagerAdapter
import java.time.LocalDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val binding by viewBinding(FragmentScheduleBinding::bind)

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val scheduleViewModel by viewModel<ScheduleViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupObservers()

        with(binding) {
            Timber.i(mainViewModel.accessRights.toString())
            when (mainViewModel.accessRights) {
                AccessRights.USER -> {
                    addButton.isVisible = false
                }
                AccessRights.ADMIN -> {
                    addButton.isVisible = true
                }
                else -> Unit
            }

            menuButton.setOnClickListener { scheduleViewModel.changeGroupClick() }
            addButton.setOnClickListener { scheduleViewModel.addLessonClick() }
        }
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@ScheduleFragment)
            setCurrentItem(LocalDate.now().dayOfWeek.ordinal - 1, false)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeGroups() }
                launch { observeEvents() }
            }
        }
    }

    private suspend fun observeGroups() {
        mainViewModel.selectedGroup.collectLatest { group ->
            with(binding) {
                if (group != null) {
                    addButton.isVisible = true
                    appBarTitle.text = getString(R.string.group_name, group.name, group.speciality)
                } else {
                    addButton.isVisible = false
                    appBarTitle.text = getString(R.string.group_not_selected)
                }
            }
        }
    }

    private suspend fun observeEvents() {
        scheduleViewModel.events.collectLatest {
            when (it) {
                is ScheduleViewModel.Event.ChangeGroup -> {
                    findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToChangeGroupFragment())
                }
                is ScheduleViewModel.Event.ChangeFragment -> {
                    binding.viewPager.currentItem = it.index
                }
                is ScheduleViewModel.Event.SelectLesson -> {
                    findNavController().navigate(
                        ScheduleFragmentDirections.actionScheduleFragmentToModifyLessonFragment(
                            mode = ModifyMode.EDIT.ordinal, selectedLessonId = it.lessonId
                        )
                    )
                }
                is ScheduleViewModel.Event.AddLesson -> {
                    findNavController().navigate(
                        ScheduleFragmentDirections.actionScheduleFragmentToModifyLessonFragment(
                            mode = ModifyMode.ADD.ordinal
                        )
                    )
                }
            }
        }
    }
}