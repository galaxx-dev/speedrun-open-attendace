package com.arysugiarto.attendence.util

import android.app.Activity
import android.content.Intent
import android.util.SparseArray
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.arysugiarto.attendence.R
import timber.log.Timber

fun BottomNavigationView.setupWithNavController(
    fragmentManager: FragmentManager,
    navGraphIds: List<Int>,
    backButtonBehaviour: BackButtonBehaviour,
    containerId: Int,
    firstItemId: Int,
    intent: Intent
): LiveData<NavController> {
    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
    // Result. [MutableLiveData] with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        // Save to the map
        graphIdToTagMap.put(graphId, fragmentTag)

        // Attach or detach nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update [LiveData] with the selected graph
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, graphId == firstItemId)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstItemId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                if (backButtonBehaviour == BackButtonBehaviour.SHOW_STARTING_FRAGMENT) { // Back button goes to first
                    // Exclude the first fragment tag because it's always in the back stack.
                    if (firstFragmentTag != newlySelectedItemTag) {
                        // Commit a transaction that cleans the back stack and adds the first fragment
                        // to it, creating the fixed started destination.
                        commitTransactionWithFixedStart(
                            fragmentManager,
                            selectedFragment,
                            firstFragmentTag
                        )
                    }
                } else { // Back button goes to previous 'main' graph destination
                    commitTransaction(fragmentManager, selectedFragment, selectedItemTag)
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    setupItemReselected(graphIdToTagMap, fragmentManager)

    // Optional: handle deep links
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstItemId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun commitTransaction(
    fragmentManager: FragmentManager,
    selectedFragment: NavHostFragment,
    selectedItemTag: String
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )
        .attach(selectedFragment)
        .setPrimaryNavigationFragment(selectedFragment)
        .detach(fragmentManager.findFragmentByTag(selectedItemTag)!!)
        .setReorderingAllowed(true)
        .commit()
}

private fun commitTransactionWithFixedStart(
    fragmentManager: FragmentManager,
    selectedFragment: NavHostFragment,
    firstFragmentTag: String
) {
    fragmentManager.commit {
        setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )

        attach(selectedFragment)
        setPrimaryNavigationFragment(selectedFragment)
        detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
        addToBackStack(firstFragmentTag)
        setReorderingAllowed(true)
    }
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)
            && selectedItemId != navHostFragment.navController.graph.id
        ) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

fun NavController.popToStartDestination(inclusive: Boolean = false) =
    popBackStack(graph.startDestination, inclusive)

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.commitNow {
        detach(navHostFragment)
    }
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.commitNow {
        attach(navHostFragment)
        if (isPrimaryNavFragment) {
            setPrimaryNavigationFragment(navHostFragment)
        }
    }
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.commitNow {
        add(containerId, navHostFragment, fragmentTag)
    }

    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"

enum class BackButtonBehaviour {
    SHOW_STARTING_FRAGMENT,
    POP_HOST_FRAGMENT
}


/**
 * Return [NavController] from Fragment Reference
 */
val Fragment.navController get() = findNavController()

/**
 * Return [NavController] from View Reference
 */
val View.navController get() = findNavController()

/**
 * Navigate To [NavDirections] with null safety or crash issue, it also give Default Animation on
 * It's Navigation
 * @param direction is Direction to other page
 * @param clearTask will clear previous backStack when navigated, same functionality as [Activity.finish]
 */
fun NavController.navigateOrNull(direction: NavDirections?, clearTask: Boolean = false) {
    try {
        val options = NavOptions.Builder().apply {
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)

            if (clearTask) {
                setLaunchSingleTop(true)
                currentDestination?.id?.let { currentDest ->
                    setPopUpTo(currentDest, clearTask)
                }
            }
        }.build()

        direction?.let { navigate(it, options) }
    } catch (e: Exception) {
        Timber.DebugTree().e(e)
    }
}

fun NavController.navigateWhen(
    clearStack: Boolean = false,
    navDirections: NavDirections,
    condition: (navDirection: NavDirections?) -> Boolean
) {
    try {
        val options = NavOptions.Builder().apply {
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)

            if (clearStack) {
                currentDestination?.id?.let { currentDest ->
                    setPopUpTo(currentDest, clearStack)
                }
            }

            if (condition.invoke(navDirections)) {
                navigate(navDirections)
            }
        }.build()
    } catch (e: Exception) {
        Timber.e(e)
    }
}

/**
 * Navigate To [NavDirections] with null safety or crash issue, it also give Default Animation on
 * It's Navigation or Custom Animation and other attributes depend on [options]
 * @param direction is Direction to other page
 * @param options is the navigation attributes that will be executed on Navigation
 */
fun NavController.navigateOrNull(direction: NavDirections?, options: NavOptions.Builder.() -> Unit) {
    try {
        val navOptions = NavOptions.Builder()
            .apply {
                setEnterAnim(R.anim.nav_default_enter_anim)
                setExitAnim(R.anim.nav_default_exit_anim)
                setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            }
            .apply(options)
            .build()

        direction?.let { navigate(direction, navOptions) }
    } catch (e: Exception) {
        Timber.DebugTree().e(e)
    }
}