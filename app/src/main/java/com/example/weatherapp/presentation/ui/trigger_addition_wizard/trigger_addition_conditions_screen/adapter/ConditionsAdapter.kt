package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemConditionBinding
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.conditionitem.ConditionItem
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.conditionitem.ConditionItemClickListener

class ConditionsAdapter(
    private val listener: ConditionItemClickListener,
) : RecyclerView.Adapter<ConditionsAdapter.ConditionsViewHolder>() {

    var conditions: List<ConditionItem> = emptyList()
        set(value) {
            val diffCallback = ConditionsDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConditionBinding.inflate(inflater, parent, false)
        return ConditionsViewHolder(binding)
    }

    override fun getItemCount(): Int = conditions.size


    override fun onBindViewHolder(holder: ConditionsViewHolder, position: Int) {
        val itemCondition = conditions[position]
        holder.bind(itemCondition)
    }

    inner class ConditionsViewHolder(
        private val binding: ItemConditionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        private val conditionTypes = context.resources.getStringArray(R.array.condition_types)
        private val conditionExpressions =
            context.resources.getStringArray(R.array.condition_expression_types)

        init {
            val conditionTypesAdapter =
                ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, conditionTypes)
            binding.conditionTypeSelectTV.setAdapter(conditionTypesAdapter)

            val conditionExpressionsAdapter = ArrayAdapter(
                context, android.R.layout.simple_dropdown_item_1line, conditionExpressions
            )
            binding.expressionSelectTV.setAdapter(conditionExpressionsAdapter)
        }

        fun bind(conditionItem: ConditionItem) {
            with(binding) {
                deleteConditionLayout.setOnClickListener {
                    listener.onDeleteButtonClickListener(conditionItem)
                }

                conditionTypeSelectTV.setText(
                    conditionItem.conditionType?.let {
                        conditionTypes[it.ordinal]
                    } ?: ""
                )

                expressionSelectTV.setText(
                    conditionItem.expressionType?.let {
                        conditionExpressions[it.ordinal]
                    } ?: ""
                )

                amountInputET.setText(conditionItem.amount)
            }
        }
    }
}