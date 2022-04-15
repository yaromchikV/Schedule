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
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PageFragment : Fragment(R.layout.fragment_page) {

    companion object {
        private const val DAY_INDEX = "day_index"

        @JvmStatic
        fun newInstance(dayIndex: Int): PageFragment {
            return PageFragment().apply {
                arguments = bundleOf(DAY_INDEX to dayIndex)
            }
        }
    }

    private val binding by viewBinding(FragmentPageBinding::bind)

    private val currentFragmentIndex by lazy { arguments?.getInt(DAY_INDEX) }

    private val scheduleViewModel by lazy { requireParentFragment().getViewModel<ScheduleViewModel>() }
    private val pageViewModel by viewModel<PageViewModel> { parametersOf(currentFragmentIndex) }

    private val scheduleAdapter: ScheduleAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackNextButtons()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupBackNextButtons() {
        when (currentFragmentIndex) {
            0 -> binding.backButton.isVisible = false
            6 -> binding.forwardButton.isVisible = false
        }

        binding.backButton.setOnClickListener {
            scheduleViewModel.prevFragmentClick(currentFragmentIndex)
        }

        binding.forwardButton.setOnClickListener {
            scheduleViewModel.nextFragmentClick(currentFragmentIndex)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = scheduleAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    scheduleViewModel.daysOfWeek.collectLatest {
                        binding.dayOfWeek.text =
                            pageViewModel.getCurrentDayName(it, currentFragmentIndex)
                    }
                }
                launch {
                    pageViewModel.lessons.collectLatest {
                        it?.let {
                            binding.noLesson.visibility =
                                if (it.isEmpty()) View.VISIBLE else View.GONE
                            scheduleAdapter.submitList(it)
                        }
                    }
                }
            }
        }
    }
}