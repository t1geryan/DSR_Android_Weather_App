package com.example.weatherapp.presenter.ui.tabs_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTabsBinding
import com.example.weatherapp.presenter.contract.ScreenContainer
import com.example.weatherapp.presenter.ui.tabs_screen.adapter.PagerFragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator

/**
 * container for all classes from TabsLayout
 */
class TabsFragment : Fragment(), ScreenContainer {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PagerFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = getString(R.string.all)
                1 -> tab.text = getString(R.string.favorites)
            }
        }.attach()
    }
}