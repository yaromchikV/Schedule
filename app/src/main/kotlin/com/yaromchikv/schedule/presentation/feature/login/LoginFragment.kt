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
        with(binding) {
            usernameEditText.doAfterTextChanged { text ->
                loginViewModel.username = text.toString()
                usernameTextField.error = null
            }

            passwordEditText.doAfterTextChanged { text ->
                loginViewModel.password = text.toString()
                passwordTextField.error = null
            }

        }
    }

    private fun setupButtonClickListener() {
        with(binding) {
            loginButton.setOnClickListener {
                loginViewModel.username = "admin"
                loginViewModel.password = "admin"
                loginViewModel.getAccessPermission()

//                if (usernameEditText.text?.isBlank() == true) {
//                    usernameTextField.error = getString(R.string.input_login)
//                }
//                if (passwordEditText.text?.isBlank() == true) {
//                    passwordTextField.error = getString(R.string.input_password)
//                }
//
//                if (usernameEditText.text?.isNotBlank() == true &&
//                    passwordEditText.text?.isNotBlank() == true
//                ) {
//                    loginViewModel.getAccessPermission()
//                }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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