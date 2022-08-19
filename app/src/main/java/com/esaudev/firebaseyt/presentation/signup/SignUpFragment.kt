package com.esaudev.firebaseyt.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.esaudev.firebaseyt.R
import com.esaudev.firebaseyt.databinding.FragmentSignUpBinding
import com.esaudev.firebaseyt.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    handleLoading(isLoading = false)
                    activity?.onBackPressed()
                    Toast.makeText(
                        requireContext(),
                        "Sign up success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Error -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> handleLoading(isLoading = true)
                else -> Unit
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            bSignUp.setOnClickListener {
                handleSignUp()
            }
        }
    }

    private fun handleSignUp() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.signUp(email, password)
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                bSignUp.text = ""
                bSignUp.isEnabled = false
                pbSignUp.visibility = View.VISIBLE
            } else {
                pbSignUp.visibility = View.GONE
                bSignUp.text = getString(R.string.login__signup_button)
                bSignUp.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}