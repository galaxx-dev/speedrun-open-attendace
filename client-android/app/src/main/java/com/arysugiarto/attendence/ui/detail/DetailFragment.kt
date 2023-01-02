package com.arysugiarto.attendence.ui.detail

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
import com.arysugiarto.attendence.databinding.FragmentDetailBinding
import com.arysugiarto.attendence.databinding.FragmentEditBinding
import com.arysugiarto.attendence.databinding.FragmentHomeBinding
import com.arysugiarto.attendence.databinding.FragmentLoginBinding
import com.arysugiarto.attendence.databinding.FragmentRegisterBinding
import com.arysugiarto.attendence.ui.edit.UpdateFragmentArgs
import com.arysugiarto.attendence.ui.login.LoginFragment
import com.arysugiarto.attendence.util.*
import com.arysugiarto.attendence.util.animatedtext.attachTextChangeAnimator
import com.arysugiarto.attendence.util.animatedtext.bindProgressButton
import com.arysugiarto.attendence.viewmodel.AuthViewModel
import com.arysugiarto.attendence.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding<FragmentDetailBinding>()
    private val viewModel by hiltNavGraphViewModels<HomeViewModel>(R.id.home)

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       initData()

    }

    private fun initData(){
        binding.apply {
            tvName.textOrNull = args.name
            tvEmployeeId.textOrNull = args.id
            tvEmail.textOrNull = "Email :" + args.email
            tvPhone.textOrNull = "Telepon :" + args.phone
            tvAddress.textOrNull = "Alamat : " + args.address
            tvGender.textOrNull = "Jenis kelamin : " + args.gender
        }
    }

}