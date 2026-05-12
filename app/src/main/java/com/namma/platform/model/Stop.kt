package com.namma.platform.model

import com.google.gson.annotations.SerializedName

data class Stop(
    @SerializedName("station_name") val stationName: String,
    @SerializedName("arrival")      val arrival: String,
    @SerializedName("departed")     val departed: Boolean,
    @SerializedName("is_current")   val isCurrent: Boolean
)
