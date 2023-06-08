package com.example.weatherapp.presentation.ui.triggers_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.presentation.ui.base_list_screen.BaseListFragment

class TriggersListFragment : BaseListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }
}