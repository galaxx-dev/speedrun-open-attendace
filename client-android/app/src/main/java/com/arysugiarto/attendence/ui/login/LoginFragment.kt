package com.arysugiarto.attendence.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.databinding.FragmentHomeBinding
import com.arysugiarto.attendence.databinding.FragmentLoginBinding
import com.arysugiarto.attendence.util.*
import com.arysugiarto.attendence.util.animatedtext.attachTextChangeAnimator
import com.arysugiarto.attendence.util.animatedtext.bindProgressButton
import com.arysugiarto.attendence.viewmodel.AuthViewModel
import com.arysugiarto.attendence.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//class LoginFragment : Fragment(R.layout.fragment_login) {
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding<FragmentLoginBinding>()
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initCallback()
        initOnClick()
        onInputTextChanged()
        initViewModelCallback()
        initOnClick()
        attachButtonAnimation()

    }

    private fun initViewModel() {
        viewModel.requestLogin(
            binding.etEmployeeId.text.toString(),
            binding.etPassword.text.toString(),
        )
    }

    private fun initCallback() {

    }

    private fun initViewModelCallback() {
        initLoginCallback()
    }

    private fun initLoginCallback() {
        viewModel.login.observe(viewLifecycleOwner) { result ->

            result.attachLoadingButton(
                button = binding.btnLogin,
                endLoadingText = requireContext().getString(LOGIN_STRING_RES)
            ) {
                this.progressColor = Color.WHITE
            }

            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    context?.toast("Berhasil Login")
                    clearUserInput()
                }
                is Result.Error -> {
                    requireView().snack(result.message)
                }
                else -> Unit
            }
        }

        viewModel.token.observe(viewLifecycleOwner) {
            if (it.length > 8) {
                userSuccessLogin()
            }
        }
    }

    private fun userSuccessLogin() {
        navController.navigateOrNull(
            direction = LoginFragmentDirections.actionLoginFragmentToMainFragment(isLoggedIn = true),
            clearTask = true
        )
    }

    private fun attachButtonAnimation() {
        viewLifecycleOwner.bindProgressButton(binding.btnLogin)

        binding.btnLogin.attachTextChangeAnimator {
            textColorRes = COLOR_WHITE_RES
            useCurrentTextColor = !useCurrentTextColor
        }
    }

    private fun onInputTextChanged() {
        binding.boxUsername.editText?.addTextChangedListener {
            binding.boxUsername.error = null
        }
        binding.boxPassword.editText?.addTextChangedListener {
            binding.boxPassword.error = null
        }
    }

    private fun initOnClick() {
        binding.apply {
            btnLogin.setOnClickListener(onClickCallback)
            btnLogin.setOnClickListener(onClickCallback)
            tvRegister.setOnClickListener(onClickCallback)
        }
    }

    private fun clearUserInput() {
        binding.boxUsername.setText()
        binding.boxPassword.setText()
    }


    private val onClickCallback = View.OnClickListener { view ->
        when {
            binding.boxUsername.textIsEmpty() -> binding.boxUsername.warn(
                context?.getString(R.string.login_username_hint)
            )
            binding.boxPassword.textIsEmpty() -> binding.boxPassword.warn(
                context?.getString(R.string.login_password_hint)
            )
            else -> {
                initViewModel()
                activity.hideKeyboard(view)
            }


        }
        when (view) {
            binding.tvRegister -> {
                navController.navigateOrNull(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                )
            }
        }

    }

    companion object {
        const val COLOR_WHITE_RES = R.color.white
        const val LOGIN_STRING_RES = R.string.login_login
    }


}