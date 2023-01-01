package com.arysugiarto.attendence.ui.edit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.databinding.FragmentEditBinding
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
class UpdateFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding<FragmentEditBinding>()
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initOnClick()
        initViewModelCallback()
        initOnClick()

    }



    private fun initViewModelCallback() {

    }


    private fun initOnClick() {
        binding.apply {
            btnRegister.setOnClickListener(onClickCallback)
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
                        gender = "male",
                        password = etPassword.text.toString(),

                    )

                    viewModel.requestRegister(registerModel)
                }

            }
        }

    }


}