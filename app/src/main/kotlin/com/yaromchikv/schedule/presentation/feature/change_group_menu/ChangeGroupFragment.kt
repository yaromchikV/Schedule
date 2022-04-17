package com.yaromchikv.schedule.presentation.feature.change_group_menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentChangeGroupBinding
import com.yaromchikv.schedule.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChangeGroupFragment : Fragment(R.layout.fragment_change_group) {

    private val binding by viewBinding(FragmentChangeGroupBinding::bind)

    private val mainViewModel by sharedViewModel<MainViewModel>()
    //private val changeGroupViewModel by viewModel<ChangeGroupViewModel>()

    private val groupsAdapter: GroupsAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = groupsAdapter
        }

        groupsAdapter.setOnItemClickListener {
            mainViewModel.selectGroupClick(it)
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeGroups()
            }
        }
    }

    private suspend fun observeGroups() {
        mainViewModel.groups.collectLatest {
            it?.let {
                groupsAdapter.submitList(it)
            }
        }
    }
}