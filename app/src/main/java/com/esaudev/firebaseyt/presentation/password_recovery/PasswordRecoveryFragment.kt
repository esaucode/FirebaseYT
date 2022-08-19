package com.esaudev.firebaseyt.presentation.password_recovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.esaudev.firebaseyt.R
import com.esaudev.firebaseyt.databinding.FragmentPasswordRecoveryBinding
import com.esaudev.firebaseyt.presentation.login.LoginFragmentDirections
import com.esaudev.firebaseyt.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRecoveryFragment : Fragment() {

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRecoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.passwordSent.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    handleLoading(isLoading = false)
                    activity?.onBackPressed()
                    Toast.makeText(
                        requireContext(),
                        "Hemos enviado un link para reestablecer tu contraseña",
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
            bBack.setOnClickListener {
                activity?.onBackPressed()
            }
            bRecoverPassword.setOnClickListener {
                handlePasswordRecovery()
            }
        }
    }

    private fun handlePasswordRecovery() {
        val email = binding.etEmail.text.toString()

        viewModel.sendPasswordLink(email)
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                bRecoverPassword.text = ""
                bRecoverPassword.isEnabled = false
                pbRecoverPassword.visibility = View.VISIBLE
            } else {
                pbRecoverPassword.visibility = View.GONE
                bRecoverPassword.text = getString(R.string.login__password_recovery_button)
                bRecoverPassword.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}