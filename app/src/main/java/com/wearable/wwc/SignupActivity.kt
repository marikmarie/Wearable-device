package com.wearable.wwc

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.wearable.wwc.database.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : AppCompatActivity() {
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var datePickerDialog: DatePickerDialog
    private var isDoubleClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Get references to the views
        val loginButton: Button = findViewById(R.id.loginButton)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)
        val usernameEditText: EditText = findViewById(R.id.username)
        val genderSpinner: Spinner = findViewById(R.id.genderSpinner)
        dateOfBirthEditText = findViewById(R.id.dateOfBirth)
        val heightEditText: EditText = findViewById(R.id.height)
        val bodyWeightEditText: EditText = findViewById(R.id.bodyWeight)
        val phoneNumberEditText: EditText = findViewById(R.id.phoneNumber)
        val passwordEditText: EditText = findViewById(R.id.password)

        // Set click listener for the login button
        loginButton.setOnClickListener {
            // Redirect to sign-in page
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up the gender spinner
        val genderOptions = arrayOf("Male", "Female")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        genderSpinner.adapter = genderAdapter

        // Set click listener for the create account button
        createAccountButton.setOnClickListener {
            // Get the values from the EditText fields
            val username = usernameEditText.text.toString().trim()
            val gender = genderSpinner.selectedItem.toString()
            val dateOfBirth = dateOfBirthEditText.text.toString().trim()
            val height = heightEditText.text.toString().trim()
            val bodyWeight = bodyWeightEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate the input fields
            if (username.isEmpty() || username.length > 25) {
                Toast.makeText(this@SignupActivity, "Invalid username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8 || password.length > 15) {
                Toast.makeText(this@SignupActivity, "Password should be between 8 and 15 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dateOfBirth.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Date of birth is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (bodyWeight.isEmpty() || !bodyWeight.isDigitsOnly()) {
                Toast.makeText(this@SignupActivity, "Invalid body weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an instance of the DatabaseHelper
            val dbHelper = DatabaseHelper(this)

            // Insert the user data into the database
            val success = dbHelper.insertUserData(username, gender, dateOfBirth, height, bodyWeight, phoneNumber, password)

            if (success) {
                // Data inserted successfully
                Toast.makeText(this@SignupActivity, "Account created successfully", Toast.LENGTH_SHORT).show()
                // Perform further actions or show a success message
            } else {
                // Failed to insert data
                Toast.makeText(this@SignupActivity, "Failed to create account", Toast.LENGTH_SHORT).show()
                // Perform error handling or show an error message
            }

            // Close the database connection
            dbHelper.close()
        }

        // Set up the DatePickerDialog for the date of birth field
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(calendar.time)
                dateOfBirthEditText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        // Set the date of birth field to accept manual input
        dateOfBirthEditText.inputType = InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE

        // Set click listener for the date of birth field
        dateOfBirthEditText.setOnClickListener {
            if (isDoubleClick) {
                showDatePickerDialog()
            } else {
                isDoubleClick = true
                dateOfBirthEditText.postDelayed({ isDoubleClick = false }, 500)
            }
        }
    }

    private fun showDatePickerDialog() {
        // Reset the calendar to the current date
        val calendar = Calendar.getInstance()
        datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

}
