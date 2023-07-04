package com.wearable.wwc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wearable.wwc.database.DatabaseHelper

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Get references to the buttons
        val accountSettingsButton = findViewById<View>(R.id.accountSettingsButton)
        val deviceManagementButton = findViewById<View>(R.id.deviceManagementButton)
        val homeButton = findViewById<View>(R.id.homeButton)
        val reportButton = findViewById<View>(R.id.reportButton)
        val settingsButton = findViewById<View>(R.id.settingsButton)

        // Set click listeners for the buttons
        accountSettingsButton.setOnClickListener { v -> onAccountSettingsButtonClick(v) }
        deviceManagementButton.setOnClickListener { v -> onDeviceManagementButtonClick(v) }
        homeButton.setOnClickListener { v -> onHomeButtonClick(v) }
        reportButton.setOnClickListener { v -> onReportButtonClick(v) }
        settingsButton.setOnClickListener { v -> onSettingsButtonClick(v) }
    }

    // Account Settings button click handler
//    fun onAccountSettingsButtonClick(view: View?) {
//        val intent = Intent(this, EditActivity::class.java)
//        startActivity(intent)
//
//        Toast.makeText(this, "Account Settings button clicked", Toast.LENGTH_SHORT).show()
//    }
    fun onAccountSettingsButtonClick(view: View?) {
        val databaseHelper = DatabaseHelper(this)
        val userData = databaseHelper.fetchUserData()

        if (userData != null) {
            Toast.makeText(this, "Account Settings button clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("userData", userData)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Error retrieving account settings data", Toast.LENGTH_SHORT).show()
        }
    }

    // Device Management button click handler
    fun onDeviceManagementButtonClick(view: View?) {
        Toast.makeText(this, "Device Management button clicked", Toast.LENGTH_SHORT).show()
    }

    // Home button click handler
    fun onHomeButtonClick(view: View?) {
        // Start the homepage activity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    // Report button click handler
    fun onReportButtonClick(view: View?) {
        Toast.makeText(this, "Report button clicked", Toast.LENGTH_SHORT).show()
    }

    // Settings button click handler
    fun onSettingsButtonClick(view: View?) {
        // Start the settings activity
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show()
    }
}