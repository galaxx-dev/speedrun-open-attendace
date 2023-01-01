package com.arysugiarto.attendence.ui.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.databinding.FragmentHomeBinding
import com.arysugiarto.attendence.ui.home.adapter.HomeAdapter
import com.arysugiarto.attendence.util.*
import com.arysugiarto.attendence.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModels by hiltNavGraphViewModels<HomeViewModel>(R.id.home)
    private val employeeAdapter = HomeAdapter.employeeAdapter

    private var typeAbsensi = emptyString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initCallback()
        initTypeAbsen()
        initViewModelCallback()

    }

    private fun initViewModel() {
        viewModels.requestEmployee()
    }

    private fun initViewModelCallback() {
        initEmployeeCallback()
    }

    private fun initCallback() {
        initClickAdapter()
    }

    private fun initEmployeeCallback() =
        viewModels.employee.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    employeeAdapter.items = result.data?.payload.orEmpty()
                }
                is Result.Error<*> -> {

                }
                else -> {
                }
            }
            binding.rvNews.adapter = employeeAdapter

        }


    private fun initTypeAbsen() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sType.adapter = adapter

        binding.sType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                typeAbsensi = binding.sType.selectedItem.toString()
                Timber.e(typeAbsensi)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun initClickAdapter() {
        HomeAdapter.SetOnClickItem.setOnClickItemListener { item ->
            navController.navigateOrNull(
                HomeFragmentDirections.actionHomeFragmentToEditFragment(
                    item.id,
                    item.fullname,
                    item.email,
                    item.phone,
                    item.gender
                )
            )
        }
    }

}