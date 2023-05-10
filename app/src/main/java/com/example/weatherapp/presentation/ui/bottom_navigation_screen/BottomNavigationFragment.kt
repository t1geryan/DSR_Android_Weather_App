package com.example.weatherapp.presentation.ui.bottom_navigation_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentBottomNavigationBinding
import com.example.weatherapp.presentation.contract.toolbar.ScreenContainer

class BottomNavigationFragment : Fragment(), ScreenContainer {

    private lateinit var binding: FragmentBottomNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost =
            childFragmentManager.findFragmentById(R.id.bottomNavFragmentContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}