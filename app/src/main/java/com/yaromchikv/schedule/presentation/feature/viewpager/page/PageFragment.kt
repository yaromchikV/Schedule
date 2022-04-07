package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentPageBinding
import com.yaromchikv.schedule.presentation.MainActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class PageFragment : Fragment(R.layout.fragment_page) {

    private val binding by viewBinding(FragmentPageBinding::bind)
    private val pageViewModel by viewModel<PageViewModel>()

    lateinit var scheduleAdapter: ScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_SECTION_NUMBER)

        if (position == 1) {
            binding.backButton.isVisible = false
        }
        if (position == 7) {
            binding.forwardButton.isVisible = false
        }

        binding.backButton.setOnClickListener {
            (activity as MainActivity).openPrev()
        }

        binding.forwardButton.setOnClickListener {
            (activity as MainActivity).openNext()
        }

        scheduleAdapter = ScheduleAdapter()
        binding.recyclerView.apply {
            adapter = scheduleAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launchWhenStarted {
            pageViewModel.subjects.collectLatest {
                scheduleAdapter.submitList(it)
            }
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PageFragment {
            return PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}