package com.yaromchikv.schedule.presentation.feature.modify_lessons

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentModifyLessonBinding
import com.yaromchikv.schedule.presentation.MainViewModel
import com.yaromchikv.schedule.presentation.common.NULL_ID
import java.util.Calendar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ModifyLessonFragment : Fragment(R.layout.fragment_modify_lesson) {

    private val binding by viewBinding(FragmentModifyLessonBinding::bind)

    private val args by navArgs<ModifyLessonFragmentArgs>()

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val modifyLessonViewModel by sharedViewModel<ModifyLessonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (ModifyMode.values()[args.mode]) {
            ModifyMode.EDIT -> modifyLessonViewModel.setLessonId(args.selectedLessonId)
            ModifyMode.ADD -> modifyLessonViewModel.clearLesson()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()

        with(binding) {
            when (ModifyMode.values()[args.mode]) {
                ModifyMode.EDIT -> {
                    appBarTitle.text = "Редактирование"
                    deleteCard.visibility = View.VISIBLE
                }
                ModifyMode.ADD -> {
                    appBarTitle.text = "Добавление"
                    deleteCard.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        generateLessonFromFields()?.let {
            modifyLessonViewModel.updateLesson(it)
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            applyButton.setOnClickListener {
                generateLessonFromFields()?.let {
                    modifyLessonViewModel.applyChangesClick(it)
                }
            }
            deleteCard.setOnClickListener {
                showDeleteAlertDialog {
                    modifyLessonViewModel.deleteButtonClick()
                    findNavController().navigateUp()
                }
            }

            classroomCard.setOnClickListener {
                navigateToChoosingList(ListMode.CLASSROOMS)
            }
            dayOfWeekCard.setOnClickListener {
                navigateToChoosingList(ListMode.DAYS_OF_WEEK)
            }
            typeCard.setOnClickListener {
                navigateToChoosingList(ListMode.LESSON_TYPES)
            }
            teacherCard.setOnClickListener {
                navigateToChoosingList(ListMode.TEACHERS)
            }

            startTimeCard.setOnClickListener {
                showTimePicker(startTimeText)
            }
            endTimeCard.setOnClickListener {
                showTimePicker(endTimeText)
            }

        }
    }

    private fun navigateToChoosingList(mode: ListMode) {
        val id = when (mode) {
            ListMode.CLASSROOMS -> modifyLessonViewModel.lesson.value?.classroomId
            ListMode.DAYS_OF_WEEK -> modifyLessonViewModel.lesson.value?.dayOfWeekId
            ListMode.LESSON_TYPES -> modifyLessonViewModel.lesson.value?.typeId
            ListMode.TEACHERS -> modifyLessonViewModel.lesson.value?.teacherId
        } ?: NULL_ID

        findNavController().navigate(
            ModifyLessonFragmentDirections.actionEditLessonFragmentToChoosingModelFragment(
                listMode = mode.ordinal, valueId = id
            )
        )
    }

    private fun showDeleteAlertDialog(action: () -> Unit) = AlertDialog.Builder(requireContext())
        .setIcon(R.drawable.ic_warning)
        .setTitle(getString(R.string.delete_alert_title))
        .setMessage(getString(R.string.delete_alert_message))
        .setPositiveButton(getString(R.string.yes)) { _, _ -> action() }
        .create()
        .show()

    private fun showTimePicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _: TimePicker?, hour: Int, minute: Int ->
                textView.text = getString(R.string.time_format, hour, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun generateLessonFromFields(): LessonModel? {
        with(binding) {
            return modifyLessonViewModel.lesson.value?.copy(
                subject = subjectText.text.toString(),
                note = noteText.text.toString(),
                startTime = startTimeText.text.toString(),
                endTime = endTimeText.text.toString(),
                weeks = mutableListOf<Int>().apply {
                    if (week1.isChecked) this.add(1)
                    if (week2.isChecked) this.add(2)
                    if (week3.isChecked) this.add(3)
                    if (week4.isChecked) this.add(4)
                },
                subgroup = when (radioSubgroup.checkedRadioButtonId) {
                    subgroup1.id -> 1
                    subgroup2.id -> 2
                    else -> 0
                },
                groupId = mainViewModel.selectedGroup.value?.id
            )
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeLesson() }
                launch { observeUpdateState() }
            }
        }
    }

    private suspend fun observeLesson() {
        modifyLessonViewModel.lesson.collectLatest { lesson ->
            lesson?.let {
                with(binding) {
                    subjectText.setText(lesson.subject)
                    typeText.text = lesson.type
                    classroomText.text = lesson.classroom
                    teacherText.text = lesson.teacher
                    startTimeText.text = lesson.startTime
                    endTimeText.text = lesson.endTime
                    dayOfWeekText.text = lesson.dayOfWeek
                    noteText.setText(lesson.note)

                    lesson.weeks.let {
                        week1.isChecked = it.contains(1)
                        week2.isChecked = it.contains(2)
                        week3.isChecked = it.contains(3)
                        week4.isChecked = it.contains(4)
                    }

                    when (lesson.subgroup) {
                        1 -> subgroup1.isChecked = true
                        2 -> subgroup2.isChecked = true
                        else -> subgroupBoth.isChecked = true
                    }
                }
            }
        }
    }

    private suspend fun observeUpdateState() {
        modifyLessonViewModel.applySuccessfully.collectLatest {
            if (it) {
                findNavController().navigateUp()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_not_fill_fields),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}