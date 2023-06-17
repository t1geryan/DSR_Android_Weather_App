package com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem

interface TriggerItemClickListener {

    fun onDeleteButtonClickListener(triggerItem: TriggerItem)

    fun onItemClickListener(triggerItem: TriggerItem)
}