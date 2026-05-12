package com.namma.platform.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.namma.platform.R
import com.namma.platform.model.Train

class TrainAdapter(
    private var trains: List<Train>,
    private val onTrainClick: (Train) -> Unit,
    private val onSpeakClick: (Train) -> Unit
) : RecyclerView.Adapter<TrainAdapter.TrainViewHolder>() {

    fun updateData(newTrains: List<Train>) {
        trains = newTrains
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_train_card, parent, false)
        return TrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int) {
        val train = trains[position]
        holder.bind(train)
    }

    override fun getItemCount() = trains.size

    inner class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trainNameKn: TextView = itemView.findViewById(R.id.trainNameKn)
        private val trainNumberEn: TextView = itemView.findViewById(R.id.trainNumberEn)
        private val destination: TextView = itemView.findViewById(R.id.destination)
        private val arrivalTime: TextView = itemView.findViewById(R.id.arrivalTime)
        private val platformNumber: TextView = itemView.findViewById(R.id.platformNumber)
        private val coachContainer: LinearLayout = itemView.findViewById(R.id.coachContainer)
        private val speakButton: View = itemView.findViewById(R.id.speakButton)

        fun bind(train: Train) {
            trainNameKn.text = train.trainNameKn
            trainNumberEn.text = "${train.trainNumber} - ${train.trainNameEn}"
            destination.text = "→ ${train.destinationEn}"
            arrivalTime.text = "(Arrival): ${train.scheduledTime}"
            platformNumber.text = train.platform

            setupCoachStrip(train.coachSequence)

            itemView.setOnClickListener { onTrainClick(train) }
            speakButton.setOnClickListener { onSpeakClick(train) }
        }

        private fun setupCoachStrip(sequence: List<String>) {
            coachContainer.removeAllViews()
            sequence.forEach { coach ->
                val textView = TextView(itemView.context).apply {
                    text = coach
                    setPadding(16, 8, 16, 8)
                    setTextColor(Color.WHITE)
                    textSize = 12f
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    lp.setMargins(0, 0, 8, 0)
                    layoutParams = lp
                    
                    val bgColor = when {
                        coach == "Engine" -> "#444444"
                        coach == "GEN" -> "#4CAF50"
                        coach == "L" -> "#E91E8C"
                        coach.startsWith("S") -> "#1565C0"
                        coach.contains("CC") || coach.contains("EC") || coach.startsWith("A") || coach.startsWith("B") -> "#FFA000"
                        else -> "#444444"
                    }
                    setBackgroundColor(Color.parseColor(bgColor))
                }
                coachContainer.addView(textView)
            }
        }
    }
}
