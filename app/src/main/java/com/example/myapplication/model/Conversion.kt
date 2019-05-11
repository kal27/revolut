package com.example.myapplication.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Conversion(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: SortedMap<String, Double>
)