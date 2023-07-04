package com.wearable.wwc

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class HomeActivity : AppCompatActivity() {
    private lateinit var heartRateButton: Button
    private lateinit var temperatureButton: Button
    private lateinit var sleepingPatternButton: Button
    private lateinit var walkingSteadinessButton: Button

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothLeScanner: BluetoothLeScanner? = null

    private var PERMISSION_ALL = 1
    @RequiresApi(Build.VERSION_CODES.S)
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_CONNECT
    )

    private var scanning = false
    private val SCAN_PERIOD: Long = 10000
    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_BLUETOOTH_SCAN_PERMISSION = 23

    private val deviceList = mutableListOf<BluetoothDevice>()
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            if (!deviceList.contains(device)) {
                deviceList.add(device)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (!hasPermission(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }

        bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothAdapter = bluetoothManager?.adapter
        bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
        heartRateButton = findViewById(R.id.heartRateButton)
        temperatureButton = findViewById(R.id.temperatureButton)
        sleepingPatternButton = findViewById(R.id.sleepingPatternButton)
        walkingSteadinessButton = findViewById(R.id.walkingSteadinessButton)


        setupClickListeners()
    }

    private fun setupClickListeners() {

        heartRateButton.setOnClickListener {
            val intent = Intent(this, HeartActivity::class.java)
            startActivity(intent)
        }

        temperatureButton.setOnClickListener {
            val intent = Intent(this, TemperatureActivity::class.java)
            startActivity(intent)
        }

        sleepingPatternButton.setOnClickListener {
            val intent = Intent(this, SleepingActivity::class.java)
            startActivity(intent)
        }

        walkingSteadinessButton.setOnClickListener {
            val intent = Intent(this, WalkingActivity::class.java)
            startActivity(intent)
        }


        val bluetoothButton: ImageButton = findViewById(R.id.bluetoothButton)
        bluetoothButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_SCAN),
                    REQUEST_BLUETOOTH_SCAN_PERMISSION
                )
            }
            handleBluetoothButtonClick()
        }
    }

    @SuppressLint("MissingPermission")
    private fun handleBluetoothButtonClick() {
        bluetoothAdapter?.let {
            if (!it.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            scanLeDevice()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_BLUETOOTH_SCAN_PERMISSION -> {
                scanLeDevice()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanLeDevice() {
        if (!scanning) {
            Handler(mainLooper).postDelayed({
                scanning = false
                bluetoothLeScanner?.stopScan(leScanCallback)
                showScannedDevices()
            }, SCAN_PERIOD)

            scanning = true
            bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }
    private fun showScannedDevices() {
        val scannedDevicesIntent = Intent(this, ScannedDevicesActivity::class.java)
        scannedDevicesIntent.putParcelableArrayListExtra(
            ScannedDevicesActivity.DEVICE_LIST_EXTRA,
            ArrayList(deviceList)
        )
        startActivity(scannedDevicesIntent)
    }

    private fun hasPermission(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
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
