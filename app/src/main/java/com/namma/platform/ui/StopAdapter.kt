package com.namma.platform.ui

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.namma.platform.R
import com.namma.platform.model.Stop

class StopAdapter(private val stops: List<Stop>) : RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        holder.bind(stops[position])
    }

    override fun getItemCount() = stops.size

    inner class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stopDot: View = itemView.findViewById(R.id.stopDot)
        private val stopName: TextView = itemView.findViewById(R.id.stopName)
        private val stopTime: TextView = itemView.findViewById(R.id.stopTime)
        private val currentTag: TextView = itemView.findViewById(R.id.currentTag)

        fun bind(stop: Stop) {
            stopName.text = stop.stationName
            stopTime.text = stop.arrival
            
            when {
                stop.isCurrent -> {
                    stopName.setTextColor(Color.parseColor("#FFD700"))
                    stopName.textSize = 20f
                    stopName.typeface = Typeface.DEFAULT_BOLD
                    stopDot.setBackgroundResource(R.drawable.circle_dot)
                    stopDot.background.setTint(Color.parseColor("#FFD700"))
                    currentTag.visibility = View.VISIBLE
                }
                stop.departed -> {
                    stopName.setTextColor(Color.parseColor("#666666"))
                    stopName.textSize = 18f
                    stopName.typeface = Typeface.DEFAULT
                    stopDot.setBackgroundResource(R.drawable.circle_dot)
                    stopDot.background.setTint(Color.parseColor("#444444"))
                    currentTag.visibility = View.GONE
                }
                else -> {
                    stopName.setTextColor(Color.WHITE)
                    stopName.textSize = 18f
                    stopName.typeface = Typeface.DEFAULT
                    stopDot.setBackgroundResource(R.drawable.circle_dot)
                    stopDot.background.setTint(Color.parseColor("#1A6BA0"))
                    currentTag.visibility = View.GONE
                }
            }
        }
    }
}
