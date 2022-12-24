package com.arysugiarto.attendence.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.databinding.FragmentMainBinding
import com.arysugiarto.attendence.util.BackButtonBehaviour
import com.arysugiarto.attendence.util.setupWithNavController
import com.arysugiarto.attendence.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val bottomNavSelectedItemIdKey = "BOTTOM_NAV_SELECTED_ITEM_ID_KEY"
    private var bottomNavSelectedItemId = R.id.home

    private val binding by viewBinding<FragmentMainBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            bottomNavSelectedItemId = it.getInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        }
        setUpBottomNavBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun setUpBottomNavBar() {
        binding.bottomNavigation.apply {
            background = null
            selectedItemId = bottomNavSelectedItemId
        }

        // Todo : Change graph when declared graph not used
        val navGraphIds = listOf(
            R.navigation.nav_home_graph
        )

        val controller = binding.bottomNavigation.setupWithNavController(
            fragmentManager = childFragmentManager,
            navGraphIds = navGraphIds,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            containerId = binding.navHostContainer.id,
            firstItemId = R.id.home,
            intent = requireActivity().intent
        )

    }

    companion object {

        val Fragment?.parentNavigation: BottomNavigationView?
            get() {
                val fragment = if (this?.parentFragment is NavHostFragment) {
                    (parentFragment as? NavHostFragment)?.parentFragment as? MainFragment
                } else null

                return fragment?.binding?.bottomNavigation
            }

    }

}