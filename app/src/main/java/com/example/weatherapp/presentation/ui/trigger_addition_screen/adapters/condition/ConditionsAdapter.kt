package com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemConditionBinding
import com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition.conditionitem.ConditionItem
import com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition.conditionitem.ConditionItemClickListener

class ConditionsAdapter(
    private val listener: ConditionItemClickListener,
) : RecyclerView.Adapter<ConditionsAdapter.ConditionViewHolder>() {

    var conditions: List<ConditionItem> = emptyList()
        set(value) {
            val diffCallback = ConditionsDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConditionBinding.inflate(inflater, parent, false)
        return ConditionViewHolder(binding)
    }

    override fun getItemCount(): Int = conditions.size


    override fun onBindViewHolder(holder: ConditionViewHolder, position: Int) {
        val itemCondition = conditions[position]
        holder.bind(itemCondition)
    }

    inner class ConditionViewHolder(
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
                    listener.deleteConditionItem(conditionItem)
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