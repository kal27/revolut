package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Rate

class RatesAdapter(private val onRateChanged: (String, String) -> Unit) : RecyclerView.Adapter<RateViewHolder>() {

    private var recyclerView: RecyclerView? = null

    private var currentBase = ""
    private var currentBaseValue = 1.0

    private var rates: MutableList<Rate> = mutableListOf()

    fun setData(list: List<Rate>) {
        if (recyclerView?.isComputingLayout == true) return

        if (rates.isEmpty()) {
            rates = list.toMutableList()
        } else {
            updateRates(list)
        }

        notifyDataSetChanged()
    }

    private fun updateRates(list: List<Rate>) {
        rates.map { original ->
            list.map {
                if ((original.base == it.base) and (original.base != currentBase)) {
                    original.value = it.value * currentBaseValue
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RateViewHolder(inflater, parent)
    }

    fun updateBaseValue(baseValue: Double, base: String) {
        if (recyclerView?.isComputingLayout == true) return

        currentBase = base
        currentBaseValue = baseValue

        updateRatesValues(base, baseValue)

        notifyDataSetChanged()
    }

    private fun updateRatesValues(base: String, baseValue: Double) {
        rates.map {
            if (it.base == base) {
                it.value = baseValue
            } else {
                it.value = it.value * baseValue
            }
        }
    }

    private fun moveItem(position: Int) {
        if ((rates.size < position) and (position == -1)) return
        val item = rates.removeAt(position)
        rates.add(0, item)
        notifyItemMoved(position, 0)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = rates[position]

        holder.bind(rate.base, rate.value, this::moveItem, onRateChanged)
    }

    override fun getItemCount(): Int = rates.size
}
