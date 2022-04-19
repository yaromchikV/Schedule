package com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.ChoosingListFragmentBinding
import com.yaromchikv.schedule.presentation.feature.editing.EditLessonViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChoosingDayOfWeekFragment : Fragment(R.layout.choosing_list_fragment) {

    private val binding by viewBinding(ChoosingListFragmentBinding::bind)

    private val args by navArgs<ChoosingDayOfWeekFragmentArgs>()

    private val editLessonViewModel by sharedViewModel<EditLessonViewModel>()
    private val choosingViewModel by viewModel<ChoosingDayOfWeekViewModel>()

    private val choosingDayOfWeekAdapter: ChoosingDayOfWeekAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

        binding.appBarTitle.text = "Дни недели"

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = choosingDayOfWeekAdapter
        }

        choosingDayOfWeekAdapter.apply {
            setSelectedId(args.dayOfWeekId)
            setOnItemClickListener {
                editLessonViewModel.setDayOfWeek(it)
                findNavController().navigateUp()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeTeachers()
            }
        }
    }

    private suspend fun observeTeachers() {
        choosingViewModel.daysOfWeek.collectLatest {
            val list = it.toMutableList()
            list.add(0, null)
            choosingDayOfWeekAdapter.submitList(list)
        }
    }
}