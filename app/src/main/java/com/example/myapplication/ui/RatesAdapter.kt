package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RatesAdapter(private val onFocusChangedListener: (Boolean) -> Unit) : RecyclerView.Adapter<RateViewHolder>() {

    private var rates: MutableList<Pair<String, Double>> = mutableListOf()

    fun setData(list: List<Pair<String, Double>>) {
        rates = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RateViewHolder(inflater, parent)
    }

    private fun onItemFocusChanged(hasFocus: Boolean, position: Int) {
        onFocusChangedListener(hasFocus)
        if (hasFocus) {
            moveItem(position)
        }
    }

    private fun moveItem(position: Int) {
        if ((rates.size < position) and (position == -1)) return
        val item = rates.removeAt(position)
        rates.add(0, item)
        notifyItemMoved(position, 0)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rates[position], this::onItemFocusChanged)
    }

    override fun getItemCount(): Int = rates.size

}