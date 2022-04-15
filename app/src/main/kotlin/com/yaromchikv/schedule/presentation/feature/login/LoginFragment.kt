package com.yaromchikv.schedule.presentation.feature.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentLoginBinding
import com.yaromchikv.schedule.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextChangedListeners()
        setupObservers()
        setupButtonClickListener()
    }

    private fun setupTextChangedListeners() {
        binding.usernameTextField.let {
            it.editText?.doAfterTextChanged { text ->
                loginViewModel.username = text.toString()
                it.error = null
            }
        }
        binding.passwordTextField.let {
            it.editText?.doAfterTextChanged { text ->
                loginViewModel.password = text.toString()
                it.error = null
            }
        }
    }

    private fun setupButtonClickListener() {
        binding.loginButton.setOnClickListener {
//            loginViewModel.username = "admin"
//            loginViewModel.password = "admin"
//            loginViewModel.getAccessPermission()

            binding.usernameTextField.let {
                if (it.editText?.text?.isBlank() == true) {
                    it.error = getString(R.string.input_login)
                }
            }
            binding.passwordTextField.let {
                if (it.editText?.text?.isBlank() == true) {
                    it.error = getString(R.string.input_password)
                }
            }
            if (binding.usernameTextField.editText?.text?.isNotBlank() == true &&
                binding.passwordTextField.editText?.text?.isNotBlank() == true
            ) {
                loginViewModel.getAccessPermission()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loginViewModel.access.collectLatest { rights ->
                        when (rights) {
                            is LoginViewModel.AccessState.Granted -> {
                                mainViewModel.accessRights = rights.accessRights
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToScheduleFragment())
                            }
                            is LoginViewModel.AccessState.Denied -> {
                                binding.passwordTextField.editText?.setText("")
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.user_not_found),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}