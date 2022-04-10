package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentPageBinding
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleFragment
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PageFragment : Fragment(R.layout.fragment_page) {

    private val binding by viewBinding(FragmentPageBinding::bind)

    private val mainViewModel by sharedViewModel<ScheduleViewModel>()
    private val pageViewModel by viewModel<PageViewModel> {
        parametersOf(arguments?.getInt(DAY_INDEX))
    }

    private val scheduleAdapter: ScheduleAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayIndex = arguments?.getInt(DAY_INDEX)

        if (dayIndex == 1) {
            binding.backButton.isVisible = false
        }
        if (dayIndex == 7) {
            binding.forwardButton.isVisible = false
        }

        binding.backButton.setOnClickListener {
            (parentFragment as ScheduleFragment).openPrev()
        }

        binding.forwardButton.setOnClickListener {
            (parentFragment as ScheduleFragment).openNext()
        }

        binding.recyclerView.apply {
            adapter = scheduleAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.daysOfWeek.collectLatest {
                        val currentDay = it.find { day -> day.id == dayIndex }?.name
                        binding.dayOfWeek.text = currentDay
                    }
                }
                launch {
                    pageViewModel.subjects.collectLatest {
                        scheduleAdapter.submitList(it)
                        binding.noLesson.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    companion object {
        private const val DAY_INDEX = "day_index"

        @JvmStatic
        fun newInstance(dayIndex: Int): PageFragment {
            return PageFragment().apply {
                arguments = bundleOf(DAY_INDEX to dayIndex)
            }
        }
    }
}