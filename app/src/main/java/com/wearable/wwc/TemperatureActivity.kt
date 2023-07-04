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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.formatter.ValueFormatter

import java.text.DecimalFormat
import java.util.*

class TemperatureActivity : AppCompatActivity() {

    private lateinit var graphContainer: FrameLayout
    private lateinit var temperatureDisplay: TextView
    private var currentTemperature: Float = 0.0f
    private var temperatureValues: List<Float> = emptyList()
    private var timestamps: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        graphContainer = findViewById(R.id.graphContainer)
        temperatureDisplay = findViewById(R.id.temperatureDisplay)
    }

    fun onMeasureButtonClick(view: View) {
        currentTemperature = generateRandomTemperature()
        updateTemperatureDisplay(currentTemperature)
        Toast.makeText(this, "Measure button clicked", Toast.LENGTH_SHORT).show()
    }

    fun onAdvisoryButtonClick(view: View) {
        val intent = Intent(this, TemperatureadvActivity::class.java)
        intent.putExtra("temperature", currentTemperature)
        startActivity(intent)
        Toast.makeText(this, "Advisory button clicked", Toast.LENGTH_SHORT).show()
    }

    fun onDayButtonClick(view: View) {
        generateAndDisplayGraph()
    }

    fun onMonthButtonClick(view: View) {
        Toast.makeText(this, "Month button clicked", Toast.LENGTH_SHORT).show()
    }

    fun onYearButtonClick(view: View) {
        Toast.makeText(this, "Year button clicked", Toast.LENGTH_SHORT).show()
    }

    fun onHomeButtonClick(view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun onReportButtonClick(view: View) {
        Toast.makeText(this, "Report button clicked", Toast.LENGTH_SHORT).show()
    }

    fun onSettingsButtonClick(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show()
    }

    private fun updateTemperatureDisplay(temperature: Float) {
        val decimalFormat = DecimalFormat("#.#")
        val formattedTemperature = decimalFormat.format(temperature)
        temperatureDisplay.text = "Temperature: $formattedTemperature \u00B0C"
    }

    // ... (Other parts of the code)

    private fun addGraphView(lineData: LineData) {
        // Create a LinearLayout to hold the LineChart
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT // Adjust height as needed
        )
        linearLayout.orientation = LinearLayout.VERTICAL

        val lineChart = LineChart(this)
        lineChart.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            (graphContainer.height * 0.75).toInt() // Set the graph height to 75% of graphContainer height
        )

        lineChart.data = lineData

        // Customize the appearance of the chart
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.description = Description().apply { text = "Temperature Data" }
        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.animateX(1500)

        // Customize the X-axis
        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.axisMinimum = 0f // Set minimum value for X-axis
        xAxis.axisMaximum = 24f // Set maximum value for X-axis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Format the X-axis labels as time in hours
                return "${value.toInt()}:00"
            }
        }
        xAxis.textSize = 12f

        // Customize the left Y-axis
        val leftYAxis: YAxis = lineChart.axisLeft
        leftYAxis.setDrawGridLines(true)
        leftYAxis.granularity = 1f
        leftYAxis.axisMinimum = 0f // Set minimum value for Y-axis
        leftYAxis.axisMaximum = 38f // Set maximum value for Y-axis
        leftYAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Format the Y-axis labels as temperature
                return "$value°C"
            }
        }
        leftYAxis.textSize = 12f

        // Customize the right Y-axis
        val rightYAxis: YAxis = lineChart.axisRight
        rightYAxis.isEnabled = false

        // Customize the legend
        val legend: Legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK

        // Add the LineChart to the LinearLayout
        linearLayout.addView(lineChart)

        // Remove all views from the graphContainer and add the LinearLayout
        graphContainer.removeAllViews()
        graphContainer.addView(linearLayout)
    }



    private fun generateAndDisplayGraph() {
        temperatureValues = listOf(35.6f, 36.0f, 37.0f, 35.7f, 36.4f, 37.0f)
        timestamps = listOf("19:00", "20:00", "18:00", "20:00", "24:00", "12:00")

        val entries: ArrayList<Entry> = ArrayList()
        for (i in temperatureValues.indices) {
            val temperature = temperatureValues[i]
            entries.add(Entry(i.toFloat(), temperature))
        }

        val dataSet = LineDataSet(entries, "Temperature Data")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)

        addGraphView(lineData)
    }

    private fun generateRandomTemperature(): Float {
        val random = Random()
        return (random.nextFloat() * (39.5f - 35.0f) + 35.0f)
    }
}
