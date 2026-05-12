package com.namma.platform.repository

import android.content.Context
import com.google.gson.Gson
import com.namma.platform.model.Station
import com.namma.platform.model.Train
import com.namma.platform.model.TrainData
import java.io.InputStreamReader

class TrainRepository(private val context: Context) {
    private val trainData: TrainData by lazy {
        val inputStream = context.assets.open("trains.json")
        val reader = InputStreamReader(inputStream)
        Gson().fromJson(reader, TrainData::class.java)
    }

    fun getStations(): List<Station> {
        return trainData.stations
    }

    fun getTrainsForStation(stationId: String): List<Train> {
        return trainData.stations.find { it.stationId == stationId }?.trains ?: emptyList()
    }
}
