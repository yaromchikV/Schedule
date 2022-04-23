package com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.domain.model.ClassroomModel
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonTypeModel
import com.yaromchikv.domain.model.TeacherModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentChoosingListBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ListMode
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ModifyLessonViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChoosingModelFragment : Fragment(R.layout.fragment_choosing_list) {

    private val binding by viewBinding(FragmentChoosingListBinding::bind)

    private val args by navArgs<ChoosingModelFragmentArgs>()

    private val modifyLessonViewModel by sharedViewModel<ModifyLessonViewModel>()
    private val choosingViewModel by viewModel<ChoosingModelViewModel> {
        parametersOf(ListMode.values()[args.listMode])
    }

    private val choosingModelAdapter: ChoosingModelAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

        with(binding) {
            if (args.listMode != NULL_ID) {
                val listMode = ListMode.values()[args.listMode]
                appBarTitle.text = when (listMode) {
                    ListMode.CLASSROOMS -> getString(R.string.classrooms_title)
                    ListMode.DAYS_OF_WEEK -> getString(R.string.days_of_week_title)
                    ListMode.LESSON_TYPES -> getString(R.string.lesson_types_title)
                    ListMode.TEACHERS -> getString(R.string.teachers_title)
                }
            }

            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = choosingModelAdapter
        }

        choosingModelAdapter.apply {
            setSelectedId(args.valueId)
            setOnItemClickListener {
                when (ListMode.values()[args.listMode]) {
                    ListMode.CLASSROOMS -> modifyLessonViewModel.setClassroom(it as ClassroomModel?)
                    ListMode.DAYS_OF_WEEK -> modifyLessonViewModel.setDayOfWeek(it as DayOfWeekModel?)
                    ListMode.LESSON_TYPES -> modifyLessonViewModel.setLessonType(it as LessonTypeModel?)
                    ListMode.TEACHERS -> modifyLessonViewModel.setTeacher(it as TeacherModel?)
                }
                findNavController().navigateUp()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeModels()
            }
        }
    }

    private suspend fun observeModels() {
        choosingViewModel.modelsState.collectLatest {
            when (it) {
                is ChoosingModelViewModel.UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is ChoosingModelViewModel.UiState.Ready -> {
                    binding.progressBar.isVisible = false
                    val list = it.data.toMutableList()
                    list.add(0, null)
                    choosingModelAdapter.submitList(list)
                }
                else -> Unit
            }
        }
    }
}