package com.arysugiarto.attendence.ui.register

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.databinding.FragmentHomeBinding
import com.arysugiarto.attendence.databinding.FragmentLoginBinding
import com.arysugiarto.attendence.databinding.FragmentRegisterBinding
import com.arysugiarto.attendence.ui.login.LoginFragment
import com.arysugiarto.attendence.util.*
import com.arysugiarto.attendence.util.animatedtext.attachTextChangeAnimator
import com.arysugiarto.attendence.util.animatedtext.bindProgressButton
import com.arysugiarto.attendence.viewmodel.AuthViewModel
import com.arysugiarto.attendence.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding<FragmentRegisterBinding>()
    private val viewModel by viewModels<AuthViewModel>()

    var gender = emptyString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initOnClick()
        initViewModelCallback()
        initOnClick()
        initSpinnerGender()

    }

    private fun initViewModelCallback() {
        initRegisterCallback()
    }

    private fun initRegisterCallback() {
        viewModel.register.observe(viewLifecycleOwner) { result ->

            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    Timber.e("Berhasil")
                }
                is Result.Error -> {

                }
                else -> Unit
            }
        }

    }

    private fun initSpinnerGender() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_choose,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sGender.adapter = adapter

        binding.sGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gender = binding.sGender.selectedItem.toString()
                Timber.e(gender)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    private fun initOnClick() {
        binding.apply {
            btnRegister.setOnClickListener(onClickCallback)
            tvRegister.setOnClickListener(onClickCallback)
        }
    }


    private val onClickCallback = View.OnClickListener { view ->
        when (view) {
            binding.btnRegister -> {
                binding.apply {
                    val registerModel = RegisterModel(
                        employeeId = etEmployeeId.text.toString(),
                        fullname = etNameEmployee.text.toString(),
                        email = etEmail.text.toString(),
                        phone = etPhone.text.toString(),
                        address = etAddress.text.toString(),
                        gender = gender,
                        password = etPassword.text.toString(),

                    )

//                    viewModel.requestRegister(registerModel)
                    Timber.e(registerModel.toString())
                }

            }
            binding.tvRegister ->{
                navController.navigateOrNull(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }
        }

    }

    companion object {
        const val COLOR_WHITE_RES = R.color.white
        const val LOGIN_STRING_RES = R.string.login_login
    }


}