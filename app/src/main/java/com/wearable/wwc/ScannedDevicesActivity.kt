package com.wearable.wwc

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScannedDevicesActivity : AppCompatActivity() {

    private lateinit var scannedDeviceRecyclerView: RecyclerView
    private lateinit var deviceList: List<BluetoothDevice>
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var gatt: BluetoothGatt? = null
    private val connectedDevices = mutableListOf<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_devices)

        deviceList = intent.getParcelableArrayListExtra<BluetoothDevice>(DEVICE_LIST_EXTRA) ?: emptyList()

        scannedDeviceRecyclerView = findViewById(R.id.scannedDeviceRecyclerView)
        scannedDeviceRecyclerView.layoutManager = LinearLayoutManager(this)
        val deviceListAdapter = DeviceListAdapter(deviceList) { device ->
            connectToDevice(device)
        }
        scannedDeviceRecyclerView.adapter = deviceListAdapter

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice) {
        if (connectedDevices.contains(device)) {
            showDisconnectConfirmation(device)
            return
        }

        Toast.makeText(this, "Connecting to ${device.name}...", Toast.LENGTH_SHORT).show()
        gatt = device.connectGatt(this, false, gattCallback)
    }

    @SuppressLint("MissingPermission")
    private fun showDisconnectConfirmation(device: BluetoothDevice) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Disconnect")
            .setMessage("Do you want to disconnect from ${device.name}?")
            .setPositiveButton("OK") { _, _ ->
                disconnectFromDevice(device)
            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.show()
    }

    @SuppressLint("MissingPermission")
    private fun disconnectFromDevice(device: BluetoothDevice) {
        connectedDevices.remove(device)
        gatt?.disconnect()
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                runOnUiThread {
                    showConnectedToast()
                    // Add the connected device to the list
                    gatt?.device?.let { connectedDevices.add(it) }
                }
                // Continue with further actions on successful connection
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                // Handle disconnection event
            }
        }

        // Implement other BluetoothGattCallback methods as needed
    }

    private fun showConnectedToast() {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        gatt?.close()
    }

    companion object {
        const val DEVICE_LIST_EXTRA = "device_list_extra"
    }

}