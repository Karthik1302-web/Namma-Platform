package com.namma.platform.model

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("station_id")      val stationId: String,
    @SerializedName("station_name_en") val stationNameEn: String,
    @SerializedName("station_name_kn") val stationNameKn: String,
    @SerializedName("trains")          val trains: List<Train>
)

data class TrainData(
    @SerializedName("stations") val stations: List<Station>
)
