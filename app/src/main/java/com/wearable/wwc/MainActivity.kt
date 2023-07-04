package com.wearable.wwc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wearable.wwc.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is already logged in
        if (isLoggedIn()) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish() // Finish this activity to prevent the user from coming back here by pressing the back button
            return // Exit the method to avoid setting the content view for the login page
        }

        // Continue setting up the login page
        setContentView(R.layout.activity_main)

        // Get references to the views
        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.loginButton)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        // Set click listener for the login button
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isUserValid(username, password)) {
                // Allow access to the home page
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)

                // Save the login status in shared preferences
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                finish() // Finish this activity to prevent the user from coming back here by pressing the back button
            } else {
                // Show an error message
                Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for the create account button
        createAccountButton.setOnClickListener {
            // Redirect to the sign-up page
            val intent = Intent(this@MainActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun isUserValid(username: String, password: String): Boolean {
        // Check if both the username and password exist in the SQLite database
        val dbHelper = DatabaseHelper(this)
        val userExists = dbHelper.checkUserCredentials(username, password)
        dbHelper.close()
        return userExists
    }
}
