package com.example.weatherapp.presentation.ui.trigger_addition_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentTriggerAdditionBinding

class TriggerAdditionFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionBinding.inflate(inflater, container, false)

        return binding.root
    }
}