package com.wearable.wwc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.wearable.wwc.database.DatabaseHelper

import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val userData = intent.getStringExtra("userData")
        // Use the fetched data as needed
        // Get references to the views
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val genderEditText: EditText = findViewById(R.id.genderEditText)
        val dobEditText: EditText = findViewById(R.id.dobEditText)
        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText: EditText = findViewById(R.id.weightEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val editButton: Button = findViewById(R.id.editButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // Set click listener for the Edit button
        editButton.setOnClickListener {
            // Perform actions when Edit button is clicked

        }

        // Set click listener for the Logout button
        logoutButton.setOnClickListener {
            // Perform logout actions
            logoutUser()
        }
    }

    private fun fetchUserData() {
        val databaseHelper = DatabaseHelper(this)
        val userData = databaseHelper.fetchUserData()

        if (userData != null) {
            val data = userData.split(",")

            val usernameEditText: EditText = findViewById(R.id.usernameEditText)
            val genderEditText: EditText = findViewById(R.id.genderEditText)
            val dobEditText: EditText = findViewById(R.id.dobEditText)
            val heightEditText: EditText = findViewById(R.id.heightEditText)
            val weightEditText: EditText = findViewById(R.id.weightEditText)
            val phoneEditText: EditText = findViewById(R.id.phoneEditText)
            val passwordEditText: EditText = findViewById(R.id.passwordEditText)

            usernameEditText.setText(data[0])
            genderEditText.setText(data[1])
            dobEditText.setText(data[2])
            heightEditText.setText(data[3])
            weightEditText.setText(data[4])
            phoneEditText.setText(data[5])
            passwordEditText.setText(data[6])
        }
    }
    private fun logoutUser() {
        // Clear the login status in shared preferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // Create an Intent to navigate back to the MainActivity (sign-in page)
        val intent = Intent(this, MainActivity::class.java)

        // Start the MainActivity and navigate back to the sign-in page
        startActivity(intent)

        // Finish the current activity (EditActivity) to remove it from the stack
        finish()
    }
}
