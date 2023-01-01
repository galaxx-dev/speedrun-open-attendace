package com.arysugiarto.attendence.ui.edit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.UpdateModel
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
    private val viewModel by hiltNavGraphViewModels<HomeViewModel>(R.id.home)
    private val args by navArgs<UpdateFragmentArgs>()

    var gender = emptyString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initOnClick()
        initViewModelCallback()
        initData()

    }


    private fun initViewModelCallback() {
        initUpdateCallback()
    }

    private fun initUpdateCallback() {
        viewModel.update.observe(viewLifecycleOwner) { result ->

            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    Timber.e("Berhasil")
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(context?.getString(R.string.success))
                        .setContentText(context?.getString(R.string.edit_employee))
                        .setConfirmClickListener {
                            it.dismissWithAnimation()
                            viewModel.requestEmployee()
                            navController.navigateUp()
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

    private fun initData(){
        binding.apply {
            etEmployeeId.textOrNull = args.id
            etNameEmployee.textOrNull  = args.name
            etEmail.textOrNull = args.email
            etPhone.textOrNull = args.phone
            etAddress.textOrNull = args.address

        }

    }


    private fun initOnClick() {
        binding.apply {
            btnEdit.setOnClickListener(onClickCallback)
        }
    }


    private val onClickCallback = View.OnClickListener { view ->
        when (view) {
            binding.btnEdit -> {
                binding.apply {
                    val updateModel = UpdateModel(
                        employeeId = etEmployeeId.text.toString(),
                        fullname = etNameEmployee.text.toString(),
                        email = etEmail.text.toString(),
                        phone = etPhone.text.toString(),
                        address = etAddress.text.toString(),
                        gender = gender
                    )
                    viewModel.requestUpdate(updateModel, args.id.toString())
                }

//                navController.navigateUp()

            }
        }

    }


}