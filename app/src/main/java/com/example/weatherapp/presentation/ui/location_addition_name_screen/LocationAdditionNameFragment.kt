package com.example.weatherapp.presentation.ui.location_addition_name_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionNameBinding

class LocationAdditionNameFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionNameBinding

    private val args: LocationAdditionNameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionNameBinding.inflate(inflater, container, false)

        binding.nameInputET.setText(args.previouslyEnteredLocationName)

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
        if (checkEnteredName()) {
            val destination =
                LocationAdditionNameFragmentDirections.actionLocationAdditionNameFragmentToLocationAdditionDetailsFragment(
                    args.latitude,
                    args.longitude,
                    binding.nameInputET.text.toString()
                )
            findNavController().navigate(destination)
        } else {
            binding.nameInputET.error = getString(R.string.empty_field_error)
        }
    }

    private fun checkEnteredName(): Boolean {
        val enteredName = binding.nameInputET.text.toString()
        return enteredName.isNotBlank()
    }
}