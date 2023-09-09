package com.programmazionemobile.formula1app.adapter

import androidx.recyclerview.widget.DiffUtil
import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStanding

class ConstructorStandingsRowItemDiffCallback : DiffUtil.ItemCallback<ConstructorStanding>() {
    override fun areItemsTheSame(oldItem: ConstructorStanding, newItem: ConstructorStanding): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: ConstructorStanding, newItem: ConstructorStanding): Boolean
    {
        return oldItem == newItem
    }
}
