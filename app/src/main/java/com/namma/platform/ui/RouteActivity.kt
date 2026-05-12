package com.namma.platform.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.namma.platform.R
import com.namma.platform.model.Train

class RouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        findViewById<android.view.View>(R.id.backButton).setOnClickListener {
            finish()
        }

        val trainJson = intent.getStringExtra("TRAIN_JSON")
        val train = Gson().fromJson(trainJson, Train::class.java)

        setupHeader(train)
        setupRecyclerView(train)
    }

    private fun setupHeader(train: Train) {
        val routeTrainName: TextView = findViewById(R.id.routeTrainName)
        routeTrainName.text = "${train.trainNameKn} (${train.trainNumber})"
    }

    private fun setupRecyclerView(train: Train) {
        val recyclerView: RecyclerView = findViewById(R.id.stopRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StopAdapter(train.stops)
    }
}
