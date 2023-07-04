package com.wearable.wwc

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WalkingActivity : AppCompatActivity() {

    private lateinit var graphContainer: FrameLayout
    private lateinit var walkingSteadinessDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walkingsteadiness)

        graphContainer = findViewById(R.id.graphContainer)
        walkingSteadinessDisplay = findViewById(R.id.walkingSteadinessDisplay)
    }

    // Measure button click handler
    fun onMeasureButtonClick(view: View) {
        val walkingSteadiness = generateRandomWalkingSteadiness() // Replace with your walking steadiness calculation or value
        updateWalkingSteadinessDisplay(walkingSteadiness)
        Toast.makeText(this, "Measure button clicked", Toast.LENGTH_SHORT).show()
    }

    // Advisory button click handler
    fun onAdvisoryButtonClick(view: View) {
        // Start the homepage activity
        val intent = Intent(this, WalkingadvActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Advisory button clicked", Toast.LENGTH_SHORT).show()
    }

    // Day button click handler
    fun onDayButtonClick(view: View) {
        Toast.makeText(this, "Day button clicked", Toast.LENGTH_SHORT).show()
    }

    // Month button click handler
    fun onMonthButtonClick(view: View) {
        Toast.makeText(this, "Month button clicked", Toast.LENGTH_SHORT).show()
    }

    // Year button click handler
    fun onYearButtonClick(view: View) {
        Toast.makeText(this, "Year button clicked", Toast.LENGTH_SHORT).show()
    }

    // Home button click handler
    fun onHomeButtonClick(view: View) {
        // Start the homepage activity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    // Report button click handler
    fun onReportButtonClick(view: View) {
        Toast.makeText(this, "Report button clicked", Toast.LENGTH_SHORT).show()
    }

    // Settings button click handler
    fun onSettingsButtonClick(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show()
    }

    private fun updateWalkingSteadinessDisplay(walkingSteadiness: String) {
        walkingSteadinessDisplay.text = "Walking Steadiness: $walkingSteadiness"
    }

    private fun addGraphView() {
        val graphView = View(this)
        graphView.setBackgroundColor(Color.BLUE)

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        graphContainer.addView(graphView, layoutParams)
    }

    private fun generateRandomWalkingSteadiness(): String {
        // Replace with your walking steadiness calculation or value
        return "--"
    }

}
