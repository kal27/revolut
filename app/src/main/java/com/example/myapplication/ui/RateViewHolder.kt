package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.rate_item.view.*


class RateViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.rate_item, parent, false)) {

    fun bind(rate: Pair<String, Double>) {
        itemView.rate_currency.text = rate.first
        itemView.rate_inputLayout.hint = String.format(itemView.resources.getString(R.string.rate_hint), rate.first)
        itemView.rate_input.setText(rate.second.toString())
    }
}
