package com.example.weatherapp.presentation.ui.triggers_list_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem.TriggerItem

class TriggersDiffCallback(
    private val oldList: List<TriggerItem>,
    private val newList: List<TriggerItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrigger = oldList[oldItemPosition]
        val newTrigger = newList[newItemPosition]

        return oldTrigger.id == newTrigger.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrigger = oldList[oldItemPosition]
        val newTrigger = newList[newItemPosition]

        return oldTrigger == newTrigger
    }
}
