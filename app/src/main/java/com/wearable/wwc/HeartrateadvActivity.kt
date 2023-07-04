package com.wearable.wwc

import android.content.Intent

import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HeartrateadvActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heartrateadv)


    }

    // Measure button click handler
    fun onMeasureButtonClick(view: View) {
        // Start the homepage activity
        val intent = Intent(this, HeartActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Measure button clicked", Toast.LENGTH_SHORT).show()
    }

    // Advisory button click handler
    fun onAdvisoryButtonClick(view: View) {
        // Start the homepage activity
        val intent = Intent(this, HeartrateadvActivity::class.java)
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


}
