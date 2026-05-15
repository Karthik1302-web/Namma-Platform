package com.namma.platform.ui

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.namma.platform.R
import com.namma.platform.model.Station
import com.namma.platform.repository.TrainRepository
import com.namma.platform.viewmodel.TrainViewModel
import java.util.Locale

/**
 * Main entry point of the application.
 * Displays a list of next 3 trains for a selected station.
 * Handles Kannada Text-To-Speech announcements and navigation to route details.
 */
class HomeActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val viewModel: TrainViewModel by viewModels()
    private lateinit var repository: TrainRepository
    private lateinit var tts: TextToSpeech
    private lateinit var trainAdapter: TrainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Data Repository
        repository = TrainRepository(this)
        
        // Initialize Text-To-Speech engine
        tts = TextToSpeech(this, this)

        setupSpinner()
        setupRecyclerView()
        observeViewModel()
    }

    /**
     * Configures the station selection dropdown with data from repository.
     */
    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.stationSpinner)
        val stations = repository.getStations()
        val stationNames = stations.map { it.stationNameEn }

        val adapter = ArrayAdapter(this, R.layout.spinner_item, stationNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Update ViewModel when a new station is picked
                viewModel.selectStation(stations[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    /**
     * Prepares the RecyclerView with TrainAdapter and click listeners.
     */
    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.trainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        trainAdapter = TrainAdapter(emptyList(), { train ->
            // Navigate to Route Screen on card click
            val intent = Intent(this, RouteActivity::class.java).apply {
                putExtra("TRAIN_JSON", Gson().toJson(train))
            }
            startActivity(intent)
        }, { train ->
            // Trigger TTS announcement on mic button click
            speakAnnouncement(train)
        })
        recyclerView.adapter = trainAdapter
    }

    /**
     * Binds UI components to LiveData observers in the ViewModel.
     */
    private fun observeViewModel() {
        val stationNameKn: TextView = findViewById(R.id.stationNameKn)
        val emptyMessage: TextView = findViewById(R.id.emptyMessage)
        
        viewModel.selectedStation.observe(this) { station ->
            stationNameKn.text = "ನಿಲ್ದಾಣ: ${station.stationNameKn}"
        }

        viewModel.trainList.observe(this) { trains ->
            trainAdapter.updateData(trains)
            // Show empty state if no trains found
            emptyMessage.visibility = if (trains.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    /**
     * Triggers the Kannada announcement with polished speech rate and pitch.
     */
    private fun speakAnnouncement(train: com.namma.platform.model.Train) {
        if (!::tts.isInitialized) return
        
        val text = viewModel.buildAnnouncement(train)
        tts.setSpeechRate(0.85f) // Set to a clear, professional speed
        tts.setPitch(1.0f)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "AnnouncementID")
    }

    /**
     * TTS Engine Callback: Configures Kannada locale and quality voices.
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.forLanguageTag("kn-IN"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts.language = Locale.US
            } else {
                try {
                    // Attempt to set a high-quality offline Kannada voice
                    val voices = tts.voices
                    if (!voices.isNullOrEmpty()) {
                        val bestVoice = voices.filter { 
                            it.locale?.language == "kn" && !it.isNetworkConnectionRequired 
                        }.maxByOrNull { it.quality }
                        
                        if (bestVoice != null) {
                            tts.voice = bestVoice
                        }
                    }
                } catch (e: Exception) {
                    // Fallback to default engine voice if voice selection fails
                }
            }
        }
    }

    override fun onDestroy() {
        // Clean up TTS resources to prevent memory leaks
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
