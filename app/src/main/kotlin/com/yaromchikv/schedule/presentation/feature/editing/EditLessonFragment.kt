package com.yaromchikv.schedule.presentation.feature.editing

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
import com.yaromchikv.schedule.databinding.FragmentEditLessonBinding
import com.yaromchikv.schedule.presentation.common.NULL_ID
import java.util.Calendar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EditLessonFragment : Fragment(R.layout.fragment_edit_lesson) {

    private val binding by viewBinding(FragmentEditLessonBinding::bind)

    private val lessonId: Int by lazy {
        val args by navArgs<EditLessonFragmentArgs>()
        args.selectedLessonId
    }

    private val editLessonViewModel by sharedViewModel<EditLessonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editLessonViewModel.setLessonId(lessonId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        with(binding) {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            applyButton.setOnClickListener {
                val lesson = generateLessonFromFields()
                lesson?.let {
                    editLessonViewModel.applyChangesClick(lesson)
                }
            }
            deleteCard.setOnClickListener {
                showDeleteAlertDialog {
                    editLessonViewModel.deleteButtonClick()
                    findNavController().navigateUp()
                }
            }

            classroomCard.setOnClickListener {
                findNavController().navigate(
                    EditLessonFragmentDirections.actionEditLessonFragmentToChoosingClassroomFragment(
                        editLessonViewModel.lesson.value?.classroomId ?: NULL_ID
                    )
                )
            }
            dayOfWeekCard.setOnClickListener {
                findNavController().navigate(
                    EditLessonFragmentDirections.actionEditLessonFragmentToChoosingDayOfWeekFragment(
                        editLessonViewModel.lesson.value?.dayOfWeekId ?: NULL_ID
                    )
                )
            }
            typeCard.setOnClickListener {
                findNavController().navigate(
                    EditLessonFragmentDirections.actionEditLessonFragmentToChoosingLessonTypeFragment(
                        editLessonViewModel.lesson.value?.typeId ?: NULL_ID
                    )
                )
            }
            teacherCard.setOnClickListener {
                findNavController().navigate(
                    EditLessonFragmentDirections.actionEditLessonFragmentToChoosingTeacherFragment(
                        editLessonViewModel.lesson.value?.teacherId ?: NULL_ID
                    )
                )
            }

            startTimeCard.setOnClickListener {
                showTimePicker(startTimeText)
            }
            endTimeCard.setOnClickListener {
                showTimePicker(endTimeText)
            }

        }
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
            val weeksList = mutableListOf<Int>()
            if (week1.isChecked) weeksList.add(1)
            if (week2.isChecked) weeksList.add(2)
            if (week3.isChecked) weeksList.add(3)
            if (week4.isChecked) weeksList.add(4)

            return editLessonViewModel.lesson.value?.copy(
                subject = subjectText.text.toString(),
                note = noteText.text.toString(),
                startTime = startTimeText.text.toString(),
                endTime = endTimeText.text.toString(),
                weeks = weeksList,
                subgroup = when (radioSubgroup.checkedRadioButtonId) {
                    subgroup1.id -> 1
                    subgroup2.id -> 2
                    else -> 0
                },
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
        editLessonViewModel.lesson.collectLatest { lesson ->
            Timber.i(lesson.toString())
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
        editLessonViewModel.updateSuccessfully.collectLatest {
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