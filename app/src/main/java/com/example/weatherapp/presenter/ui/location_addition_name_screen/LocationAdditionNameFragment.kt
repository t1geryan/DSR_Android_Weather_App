package com.example.weatherapp.presenter.ui.location_addition_name_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionNameBinding

class LocationAdditionNameFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            toNextScreen()
        }

        binding.nameInputET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                toNextScreen()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun toNextScreen() {
        findNavController().navigate(R.id.action_locationAdditionNameFragment_to_locationAdditionDetailsFragment)
    }
}