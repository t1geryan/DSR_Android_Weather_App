package com.example.weatherapp.presentation.ui.tabs_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.presentation.ui.all_locations_list_screen.AllLocationsListFragment
import com.example.weatherapp.presentation.ui.favorite_locations_list_screen.FavoriteLocationsListFragment


class PagerFragmentAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    // Number of Fragments in parent's (TabsFragment) TabLayout = 2
    override fun getItemCount(): Int = 2

    /**
     * AllLocationsFragment and FavoriteLocationsFragments
     * is classes which should to be show from TabsFragment
     */
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> AllLocationsListFragment()
        else -> FavoriteLocationsListFragment()
    }
}