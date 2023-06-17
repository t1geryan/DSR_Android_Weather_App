package com.example.weatherapp.presentation.ui.triggers_list_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.repositories.TriggersRepository
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem.TriggerItem
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem.TriggerItemClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TriggersListViewModel @Inject constructor(
    private val triggersRepository: TriggersRepository,
) : ViewModel(), TriggerItemClickListener {

    override fun onDeleteButtonClickListener(triggerItem: TriggerItem) {
        TODO("Not yet implemented")
    }

    override fun onItemClickListener(triggerItem: TriggerItem) {
        TODO("Not yet implemented")
    }
}