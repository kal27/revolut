package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.rate_item.view.*


class RateViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.rate_item, parent, false)) {

    fun bind(
        rate: Pair<String, Double>,
        onItemFocusChanged: (Boolean, Int) -> Unit
    ) {
        itemView.apply {
            rate_currency.text = rate.first
            rate_input.hint = String.format(resources.getString(R.string.rate_hint), rate.first)
            rate_input.setText(rate.second.toString())

            rate_input.setOnFocusChangeListener { _, hasFocus -> onItemFocusChanged(hasFocus, adapterPosition) }

            setOnClickListener {
                rate_input.clearFocus()
            }
        }
    }
}
