package com.example.weatherapp.presentation.ui.tabs_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.presentation.ui.all_locations_list_screen.AllLocationsListFragment
import com.example.weatherapp.presentation.ui.favorite_locations_list_screen.FavoriteLocationsListFragment


class PagerFragmentAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    /**
     * AllLocationsFragment and FavoriteLocationsFragments
     * is classes which should to be show from TabsFragment
     */
    private val fragments = listOf(AllLocationsListFragment(), FavoriteLocationsListFragment())

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment = fragments[position]
}