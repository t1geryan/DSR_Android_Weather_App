package com.example.weatherapp.presentation.ui.tabs_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class PagerFragmentAdapter(
    parentFragment: Fragment,
    private val fragmentsList: List<Fragment>,
) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int = fragmentsList.count()

    override fun createFragment(position: Int): Fragment = fragmentsList[position]
}