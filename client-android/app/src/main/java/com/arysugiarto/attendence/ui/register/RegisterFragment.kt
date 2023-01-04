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
import cn.pedant.SweetAlert.SweetAlertDialog
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
        initSpinnerGender()
        onInputTextChanged()

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
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(context?.getString(R.string.success))
                        .setContentText(context?.getString(R.string.register_employee))
                        .setConfirmClickListener {
                            it.dismissWithAnimation()
                            navController.navigateOrNull(
                                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                            )
                        }
                        .show()
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


    private fun onInputTextChanged() {
        binding.boxEmployeId.editText?.addTextChangedListener {
            binding.boxEmployeId.error = null
        }
        binding.boxNameEmployee.editText?.addTextChangedListener {
            binding.boxNameEmployee.error = null
        }
        binding.boxPhone.editText?.addTextChangedListener {
            binding.boxPhone.error = null
        }
        binding.boxEmail.editText?.addTextChangedListener {
            binding.boxEmail.error = null
        }
        binding.boxPassword.editText?.addTextChangedListener {
            binding.boxPassword.error = null
        }
    }

    private fun initOnClick() {
        binding.apply {
            btnRegister.setOnClickListener(onClickCallback)
            tvToLogin.setOnClickListener(onClickCallback)
        }
    }


    private val onClickCallback = View.OnClickListener { view ->
        when {
            binding.boxEmployeId.textIsEmpty() -> binding.boxEmployeId.warn(
                context?.getString(R.string.register_employeid_hint)
            )
            binding.boxNameEmployee.textIsEmpty() -> binding.boxNameEmployee.warn(
                context?.getString(R.string.register_name_hint)
            )
            binding.boxPhone.textIsEmpty() -> binding.boxPhone.warn(
                context?.getString(R.string.register_phone_hint)
            )
            binding.boxEmail.textIsEmpty() -> binding.boxEmail.warn(
                context?.getString(R.string.register_email_hint)
            )
            binding.boxPassword.textIsEmpty() -> binding.boxPassword.warn(
                context?.getString(R.string.login_password_hint)
            )
            else -> {
                activity.hideKeyboard(view)
            }

        }
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

                    viewModel.requestRegister(registerModel)
                    Timber.e(registerModel.toString())
                }

            }
            binding.tvToLogin ->{
                navController.navigateOrNull(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }
        }

    }

}