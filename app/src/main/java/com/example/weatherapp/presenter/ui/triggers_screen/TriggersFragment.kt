package com.example.weatherapp.presenter.ui.triggers_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentTriggersBinding

class TriggersFragment : Fragment() {

    private lateinit var binding: FragmentTriggersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggersBinding.inflate(inflater, container, false)
        return binding.root
    }
}