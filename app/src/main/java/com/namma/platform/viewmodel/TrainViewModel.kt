package com.namma.platform.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.namma.platform.model.Station
import com.namma.platform.model.Train

/**
 * ViewModel for managing train and station data across the UI.
 * Follows strict MVVM principles by keeping business logic separate from Activity code.
 */
class TrainViewModel : ViewModel() {
    private val _selectedStation = MutableLiveData<Station>()
    val selectedStation: LiveData<Station> = _selectedStation

    private val _trainList = MutableLiveData<List<Train>>()
    val trainList: LiveData<List<Train>> = _trainList

    /**
     * Updates the currently selected station and refreshes the train list.
     * Limits the train list to the next 3 scheduled trains as per requirements.
     */
    fun selectStation(station: Station) {
        _selectedStation.value = station
        _trainList.value = station.trains.take(3)
    }

    /**
     * Generates a natural-sounding Kannada announcement for the Text-To-Speech engine.
     * Uses pure Kannada script to ensure fluent pronunciation.
     */
    fun buildAnnouncement(train: Train): String {
        return "ಗಮನಿಸಿ, ${train.trainNameKn}, ರೈಲು ಸಂಖ್ಯೆ ${train.trainNumber}, ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ಸಂಖ್ಯೆ ${train.platform} ಗೆ ಶೀಘ್ರದಲ್ಲೇ ಬರುತ್ತಿದೆ. ಜನರಲ್ ಬೋಗಿಯು ಇಂಜಿನ್ ನಂತರ ಇರುತ್ತದೆ."
    }

    /**
     * Helper to find the first general coach position for UI indicators.
     */
    fun getCoachPosition(train: Train): Int {
        return train.coachSequence.indexOfFirst { it == "GEN" }
    }
}
