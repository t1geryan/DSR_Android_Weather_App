package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.conditionitem.ConditionItem

class ConditionsDiffCallback(
    private val oldList: List<ConditionItem>,
    private val newList: List<ConditionItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // todo: not yet done
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCondition = oldList[oldItemPosition]
        val newCondition = newList[newItemPosition]

        return oldCondition == newCondition
    }
}