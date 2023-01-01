package com.arysugiarto.attendence.ui.home.adapter

import com.arysugiarto.attendence.base.BaseAdapter
import com.arysugiarto.attendence.data.remote.model.EmployeResponse
import com.arysugiarto.attendence.databinding.ItemEmployeBinding
import com.arysugiarto.attendence.util.textOrNull


object HomeAdapter {

    val employeeAdapter =
        BaseAdapter.adapterOf<EmployeResponse.Payload, ItemEmployeBinding>(
            register = BaseAdapter.Register(
                onBindHolder = { pos, item, view ->
                    view.run {

                        tvNameEmploye.textOrNull = item.fullname

                        clItem.setOnClickListener {
                            SetOnClickItem.onClickListener.invoke(item)
                        }

                    }
                }
            ),
            diff = BaseAdapter.Diff(
                areItemsTheSame = { old, new -> old.id == new.id },
                areContentsTheSame = { old, new -> old == new }
            )
        )

    object SetOnClickItem {
        var onClickListener: (EmployeResponse.Payload) -> Unit = { _ -> }

        fun setOnClickItemListener(listener: (EmployeResponse.Payload) -> Unit) {
            onClickListener = listener
        }

    }

}