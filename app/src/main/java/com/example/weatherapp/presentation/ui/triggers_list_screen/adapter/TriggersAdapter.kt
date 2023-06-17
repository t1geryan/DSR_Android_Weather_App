package com.example.weatherapp.presentation.ui.triggers_list_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemTriggerBinding
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem.TriggerItem
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.triggeritem.TriggerItemClickListener

class TriggersAdapter(
    private val listener: TriggerItemClickListener
) : RecyclerView.Adapter<TriggersAdapter.TriggersViewHolder>() {

    var triggers: List<TriggerItem> = emptyList()
        set(value) {
            val diffCallback = TriggersDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriggersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTriggerBinding.inflate(inflater, parent, false)
        return TriggersViewHolder(binding)
    }

    override fun getItemCount(): Int = triggers.size

    override fun onBindViewHolder(holder: TriggersViewHolder, position: Int) {
        val triggerItem = triggers[position]
        holder.bind(triggerItem, position)
    }

    inner class TriggersViewHolder(
        private val binding: ItemTriggerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(triggerItem: TriggerItem, position: Int) {
            setOnTriggerItemClickListeners(triggerItem)
            with(binding) {
                val context = root.context
                triggerNameTV.text = context.getString(R.string.trigger_name, position)

                selectedConditionsTV.text = triggerItem.conditions.joinToString { condition ->
                    // TODO: implement mapping condition to localized name
                    "test"
                }

                selectedLocationsTV.text = triggerItem.locations.joinToString { location ->
                    "${location.name}\n"
                }
            }
        }

        private fun setOnTriggerItemClickListeners(triggerItem: TriggerItem) {
            with(binding) {
                root.setOnClickListener {
                    listener.onItemClickListener(triggerItem)
                }
                deleteTriggerIVLayout.setOnClickListener {
                    listener.onDeleteButtonClickListener(triggerItem)
                }
            }
        }
    }
}