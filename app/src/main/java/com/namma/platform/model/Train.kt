package com.namma.platform.model

import com.google.gson.annotations.SerializedName

data class Train(
    @SerializedName("train_id")       val trainId: String,
    @SerializedName("train_number")   val trainNumber: String,
    @SerializedName("train_name_en")  val trainNameEn: String,
    @SerializedName("train_name_kn")  val trainNameKn: String,
    @SerializedName("platform")       val platform: String,
    @SerializedName("scheduled_time") val scheduledTime: String,
    @SerializedName("destination_en") val destinationEn: String,
    @SerializedName("coach_sequence") val coachSequence: List<String>,
    @SerializedName("stops")          val stops: List<Stop> = emptyList()
)
