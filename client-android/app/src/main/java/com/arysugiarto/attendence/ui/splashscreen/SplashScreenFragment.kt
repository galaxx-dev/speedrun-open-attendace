package com.arysugiarto.attendence.ui.splashscreen

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment: Fragment(R.layout.fragment_splashscreen) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forceFullscreenStatusBar()

    }

    private fun forceFullscreenStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(
                WindowInsets.Type.statusBars()
            )
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun hideFullscreenStatusBar() {
        if (Build.VERSION.SDK_INT >= 30) {
            requireActivity().window.insetsController?.show(
                WindowInsets.Type.statusBars()
            )
        } else {
            requireActivity().window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

//    private suspend fun initSessionId(
//        hadToInsertSessionId: Boolean
//    ) {
//        if (hadToInsertSessionId) viewModel.setSessionId()
//    }

    override fun onDestroy() {
        super.onDestroy()

        hideFullscreenStatusBar()
    }

}