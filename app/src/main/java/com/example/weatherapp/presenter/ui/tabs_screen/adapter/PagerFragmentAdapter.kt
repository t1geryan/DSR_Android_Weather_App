package com.example.weatherapp.presenter.ui.tabs_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.presenter.ui.all_locations_screen.AllLocationsFragment
import com.example.weatherapp.presenter.ui.favorite_locations_screen.FavoriteLocationsFragment


class PagerFragmentAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    // Number of Fragments in parent's (TabsFragment) TabLayout = 2
    override fun getItemCount(): Int = 2

    /**
     * AllLocationsFragment and FavoriteLocationsFragments
     * is classes which should to be show from TabsFragment
     */
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> AllLocationsFragment()
        else -> FavoriteLocationsFragment()
    }
}