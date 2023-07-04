package com.wearable.wwc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wearable.wwc.database.DatabaseHelper
import java.text.DecimalFormat

class TemperatureadvActivity : AppCompatActivity() {

    private lateinit var temperatureDisplay: TextView
    private lateinit var graphContainer: ScrollView
    private var currentTemperature: Float = 0.0f
    private lateinit var db: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperatureadv)

        temperatureDisplay = findViewById(R.id.temperatureDisplay)
        graphContainer = findViewById(R.id.graphContainer)

        db = DatabaseHelper(this)

        // Get the temperature value passed from TemperatureActivity
        currentTemperature = intent.getFloatExtra("temperature", 0.0f)

        // Display the temperature value
        updateTemperatureDisplay(currentTemperature)
        checkTemperatureThreshold()
    }

    private fun updateTemperatureDisplay(temperature: Float) {
        val decimalFormat = DecimalFormat("#.#")
        val formattedTemperature = decimalFormat.format(temperature)
        temperatureDisplay.text = "Temperature: $formattedTemperature \u00B0C"
    }

    private fun checkTemperatureThreshold() {
        val threshold = db.getParameterThreshold()
        if (currentTemperature < threshold.minTemp) {
            val advisory = db.getLowTempAdvisory()
            displayAdvisory(advisory)
        } else if (currentTemperature > threshold.maxTemp) {
            val advisory = db.getHighTempAdvisory()
            displayAdvisory(advisory)
        }
    }

    private fun displayAdvisory(advisory: String) {
        graphContainer.removeAllViews()
        val messageView = TextView(this)
        messageView.text = advisory
        graphContainer.addView(messageView)
    }



    // Measure button click handler
    fun onMeasureButtonClick(view: View) {
        updateTemperatureDisplay(currentTemperature)
        val intent = Intent(this, TemperatureActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Measure button clicked", Toast.LENGTH_SHORT).show()
    }

    // Advisory button click handler
    fun onAdvisoryButtonClick(view: View) {
        updateTemperatureDisplay(currentTemperature)

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
}
