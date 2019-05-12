package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.rate_item.view.*


class RateViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.rate_item, parent, false)) {

    fun bind(
        base: String,
        baseValue: Double,
        onItemFocusGained: (Int) -> Unit,
        afterTextChanged: (String, String) -> Unit
    ) {
        itemView.apply {
            updateUI(base, baseValue)

            addOnFocusChangedListener(onItemFocusGained)
            addAfterTextChangedListener(afterTextChanged, base)
        }
    }

    private fun View.updateUI(base: String, baseValue: Double) {
        rate_currency.text = base
        rate_input.hint = String.format(resources.getString(R.string.rate_hint), base)

        changeRateInputText(baseValue)
    }

    private fun View.changeRateInputText(baseValue: Double) {
        rate_input.tag = ""
        val baseValueFormatted = String.format("%.2f", baseValue)
        rate_input.setText(baseValueFormatted)
        rate_input.tag = null
    }

    private fun View.addAfterTextChangedListener(
        afterTextChanged: (String, String) -> Unit,
        base: String
    ) {
        rate_input.afterTextChanged {
            afterTextChanged(base, it)
        }
    }

    private fun View.addOnFocusChangedListener(onItemFocusGained: (Int) -> Unit) {
        rate_input.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                onItemFocusGained(adapterPosition)
            }
        }
    }
}
