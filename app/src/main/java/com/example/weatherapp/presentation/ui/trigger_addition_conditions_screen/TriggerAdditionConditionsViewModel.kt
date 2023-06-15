package com.example.weatherapp.presentation.ui.trigger_addition_conditions_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.presentation.ui.trigger_addition_conditions_screen.adapters.condition.conditionitem.ConditionItem
import com.example.weatherapp.presentation.ui.trigger_addition_conditions_screen.adapters.condition.conditionitem.ConditionItemClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TriggerAdditionConditionsViewModel @Inject constructor(

) : ViewModel(), ConditionItemClickListener {

    private val _conditionItems = MutableStateFlow(listOf(ConditionItem()))
    val conditionItems: StateFlow<List<ConditionItem>>
        get() = _conditionItems.asStateFlow()

    override fun deleteConditionItem(conditionItem: ConditionItem) {
        TODO("not yet implemented")
    }
}