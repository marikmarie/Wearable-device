package com.wearable.wwc.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "UserData.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "user"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        private const val COLUMN_HEIGHT = "height"
        private const val COLUMN_BODY_WEIGHT = "body_weight"
        private const val COLUMN_PHONE_NUMBER = "phone_number"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_PARAMETER_READING = "ParameterReading"
        private const val TABLE_PARAMETER_THRESHOLD = "ParameterThreshold"
        private const val TABLE_PARAMETER_READING_ADVISORY = "ParameterReadingAdvisory"

        private const val COLUMN_ID = "id"

        private const val COLUMN_DEVICE_SERIAL_NO = "WWC_DeviceSerial_No"
        private const val COLUMN_BODY_TEMPERATURE = "body_temperature"
        private const val COLUMN_WALKING_STEADINESS = "walking_steadiness"
        private const val COLUMN_HEART_RATE = "heart_rate"
        private const val COLUMN_SLEEP_PATTERN = "sleep_pattern"
        private const val COLUMN_TIME_STAMP = "time_stamp"

        private const val COLUMN_MIN_TEMP = "min_temp"
        private const val COLUMN_MAX_TEMP = "max_temp"
        private const val COLUMN_MIN_HEART_RATE = "min_heart_rate"
        private const val COLUMN_MAX_HEART_RATE = "max_heart_rate"
        private const val COLUMN_MIN_WALK_STEADINESS = "min_walk_steadiness"
        private const val COLUMN_MAX_WALK_STEADINESS = "max_walk_steadiness"
        private const val COLUMN_MIN_SLEEP_PATTERN = "min_sleep_pattern"
        private const val COLUMN_MAX_SLEEP_PATTERN = "max_sleep_pattern"

        private const val COLUMN_HIGH_TEMP_ADVISORY = "high_temp_advisory"
        private const val COLUMN_LOW_TEMP_ADVISORY = "low_temp_advisory"
        private const val COLUMN_HIGH_HEART_RATE_ADVISORY = "high_heart_rate_advisory"
        private const val COLUMN_LOW_HEART_RATE_ADVISORY = "low_heart_rate_advisory"
        private const val COLUMN_UNSTEADY_WALK_ADVISORY = "unsteady_walk_advisory"
        private const val COLUMN_INSUFFICIENT_SLEEP_ADVISORY = "insufficient_sleep_advisory"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_USERNAME TEXT PRIMARY KEY, " +
                "$COLUMN_GENDER TEXT, " +
                "$COLUMN_DATE_OF_BIRTH TEXT, " +
                "$COLUMN_HEIGHT TEXT, " +
                "$COLUMN_BODY_WEIGHT TEXT, " +
                "$COLUMN_PHONE_NUMBER TEXT, " +
                "$COLUMN_PASSWORD TEXT)"

        val createParameterReadingTableQuery = "CREATE TABLE $TABLE_PARAMETER_READING (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DEVICE_SERIAL_NO TEXT, " +
                "$COLUMN_BODY_TEMPERATURE REAL, " +
                "$COLUMN_WALKING_STEADINESS TEXT, " +
                "$COLUMN_HEART_RATE REAL, " +
                "$COLUMN_SLEEP_PATTERN TEXT, " +
                "$COLUMN_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)"

        val createParameterThresholdTableQuery = "CREATE TABLE $TABLE_PARAMETER_THRESHOLD (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MIN_TEMP REAL, " +
                "$COLUMN_MAX_TEMP REAL, " +
                "$COLUMN_MIN_HEART_RATE REAL, " +
                "$COLUMN_MAX_HEART_RATE REAL, " +
                "$COLUMN_MIN_WALK_STEADINESS REAL, " +
                "$COLUMN_MAX_WALK_STEADINESS REAL, " +
                "$COLUMN_MIN_SLEEP_PATTERN REAL, " +
                "$COLUMN_MAX_SLEEP_PATTERN REAL)"

        val createParameterReadingAdvisoryTableQuery = "CREATE TABLE $TABLE_PARAMETER_READING_ADVISORY (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_HIGH_TEMP_ADVISORY TEXT, " +
                "$COLUMN_LOW_TEMP_ADVISORY TEXT, " +
                "$COLUMN_HIGH_HEART_RATE_ADVISORY TEXT, " +
                "$COLUMN_LOW_HEART_RATE_ADVISORY TEXT, " +
                "$COLUMN_UNSTEADY_WALK_ADVISORY TEXT, " +
                "$COLUMN_INSUFFICIENT_SLEEP_ADVISORY TEXT)"

        db.execSQL(createUserTableQuery)
        db.execSQL(createParameterReadingTableQuery)
        db.execSQL(createParameterThresholdTableQuery)
        db.execSQL(createParameterReadingAdvisoryTableQuery)

        // Insert sample data if needed

        insertSampleParameterThresholdData(db)
        insertSampleParameterReadingAdvisoryData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARAMETER_READING")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARAMETER_THRESHOLD")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARAMETER_READING_ADVISORY")
        onCreate(db)
    }




    private fun insertSampleParameterThresholdData(db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_MIN_TEMP, 36.0f)
        values.put(COLUMN_MAX_TEMP, 37.5f)
        values.put(COLUMN_MIN_HEART_RATE, 60.0f)
        values.put(COLUMN_MAX_HEART_RATE, 100.0f)
        values.put(COLUMN_MIN_WALK_STEADINESS, 0.0f)
        values.put(COLUMN_MAX_WALK_STEADINESS, 10.0f)
        values.put(COLUMN_MIN_SLEEP_PATTERN, 6.0f)
        values.put(COLUMN_MAX_SLEEP_PATTERN, 9.0f)
        db.insert(TABLE_PARAMETER_THRESHOLD, null, values)
    }

    private fun insertSampleParameterReadingAdvisoryData(db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_HIGH_TEMP_ADVISORY, "Your temperature is too high. Please visit a doctor if it persists. " +
                "Placing your feet in a cold foot bath cools your body and allows you to sit back and relax." +
                " Simply add cold water and ice cubes to a bucket of water." +
                " Immerse your feet and soak for up to 20 minutes. " +
                "Add a few drops of peppermint essential oil for an added cooling effect.")



        values.put(COLUMN_LOW_TEMP_ADVISORY, "Your temperature is too low. Please visit a doctor.")
        values.put(COLUMN_HIGH_HEART_RATE_ADVISORY, "Your heart rate is too high. Please consult a physician if it persists.")
        values.put(COLUMN_LOW_HEART_RATE_ADVISORY, "Your heart rate is too low. Please consult a physician.")
        values.put(COLUMN_UNSTEADY_WALK_ADVISORY, "Your walking pattern is unsteady. Please be careful while walking.")
        values.put(COLUMN_INSUFFICIENT_SLEEP_ADVISORY, "You have insufficient sleep. It's important to get enough rest.")
        db.insert(TABLE_PARAMETER_READING_ADVISORY, null, values)
    }

    // Rest of the code...
    // Function to fetch account settings data from the database
    @SuppressLint("Range")
    fun fetchUserData(): String? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)
        var userData: String? = null
        if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))
            val dob = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH))
            val height = cursor.getString(cursor.getColumnIndex(COLUMN_HEIGHT))
            val weight = cursor.getString(cursor.getColumnIndex(COLUMN_BODY_WEIGHT))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            userData = "$username,$gender,$dob,$height,$weight,$phone,$password"
        }
        cursor.close()
        db.close()
        return userData
    }

    fun insertUserData(
        username: String,
        gender: String,
        dateOfBirth: String,
        height: String,
        bodyWeight: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_GENDER, gender)
            put(COLUMN_DATE_OF_BIRTH, dateOfBirth)
            put(COLUMN_HEIGHT, height)
            put(COLUMN_BODY_WEIGHT, bodyWeight)
            put(COLUMN_PHONE_NUMBER, phoneNumber)
            put(COLUMN_PASSWORD, password)
        }
        val insertedRowId = db.insert(TABLE_NAME, null, values)
        return insertedRowId != -1L
    }

    fun checkUserCredentials(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    fun getParameterThreshold(): ParameterThreshold {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PARAMETER_THRESHOLD,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val threshold = ParameterThreshold()
        if (cursor.moveToFirst()) {
            threshold.minTemp = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MIN_TEMP))
            threshold.maxTemp = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MAX_TEMP))
        }

        cursor.close()
        db.close()
        return threshold
    }


    fun getLowTempAdvisory(): String {
        val db = readableDatabase
        val columns = arrayOf(COLUMN_ID, COLUMN_LOW_TEMP_ADVISORY)

        val cursor = db.query(
            TABLE_PARAMETER_READING_ADVISORY,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        var advisory = ""

        if (cursor.moveToFirst()) {
            val lowTempAdvisoryIndex = cursor.getColumnIndex(COLUMN_LOW_TEMP_ADVISORY)
            advisory = cursor.getString(lowTempAdvisoryIndex)
        }

        cursor.close()
        db.close()
        return advisory
    }


    fun getHighTempAdvisory(): String {
        val db = readableDatabase
        val columns = arrayOf(COLUMN_ID, COLUMN_HIGH_TEMP_ADVISORY)

        val cursor = db.query(
            TABLE_PARAMETER_READING_ADVISORY,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        var advisory = ""

        if (cursor.moveToFirst()) {
            val highTempAdvisoryIndex = cursor.getColumnIndex(COLUMN_HIGH_TEMP_ADVISORY)
            advisory = cursor.getString(highTempAdvisoryIndex)
        }

        cursor.close()
        db.close()
        return advisory
    }
    //updateUserData
    fun updateUserData(
        username: String,
        gender: String,
        dateOfBirth: String,
        height: String,
        bodyWeight: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GENDER, gender)
            put(COLUMN_DATE_OF_BIRTH, dateOfBirth)
            put(COLUMN_HEIGHT, height)
            put(COLUMN_BODY_WEIGHT, bodyWeight)
            put(COLUMN_PHONE_NUMBER, phoneNumber)
            put(COLUMN_PASSWORD, password)
        }
        val whereClause = "$COLUMN_USERNAME = ?"
        val whereArgs = arrayOf(username)
        val rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs)
        return rowsUpdated > 0
    }
}





