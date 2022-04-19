package com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom

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

class ChoosingClassroomFragment : Fragment(R.layout.choosing_list_fragment) {

    private val binding by viewBinding(ChoosingListFragmentBinding::bind)

    private val args by navArgs<ChoosingClassroomFragmentArgs>()

    private val editLessonViewModel by sharedViewModel<EditLessonViewModel>()
    private val choosingViewModel by viewModel<ChoosingClassroomViewModel>()

    private val choosingClassroomAdapter: ChoosingClassroomAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

        binding.appBarTitle.text = "Аудитории"

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = choosingClassroomAdapter
        }

        choosingClassroomAdapter.apply {
            setSelectedId(args.classroomId)
            setOnItemClickListener {
                editLessonViewModel.setClassroom(it)
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
        choosingViewModel.classrooms.collectLatest {
            val list = it.toMutableList()
            list.add(0, null)
            choosingClassroomAdapter.submitList(list)
        }
    }
}