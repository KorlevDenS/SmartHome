package com.example.smarthome.model

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _waterCounter = mutableDoubleStateOf(0.0)
    private val _energyCounter = mutableDoubleStateOf(0.0)
    private val _rooms = mutableStateListOf<Room>()
    private var _devices = mutableStateListOf<Device>()

    private var _selectedDevices = mutableStateOf<List<Device>>(_devices)
    private var _turnedOnDevices = mutableStateOf(_selectedDevices.value)

    var waterCounter: Double
        get() = _waterCounter.doubleValue
        set(waterAmount) {_waterCounter.doubleValue = waterAmount}

    var energyCounter: Double
        get() = _energyCounter.doubleValue
        set(energyAmount) {_energyCounter.doubleValue = energyAmount}

    val devices: List<Device>
        get() = _devices.filter { d -> d.room.isSelected}

    val selectedDevices: List<Device>
        get() = _selectedDevices.value

    fun selectArea() {
        _selectedDevices.value = _devices.filter { d -> d.room.isSelected}
    }

    val turnedOnDevices: List<Device>
        get() = _turnedOnDevices.value

    fun installTurnedOnDevices() {
        _turnedOnDevices.value = _turnedOnDevices.value.filter { d -> d.turnedOn }
    }

    val rooms: List<Room>
        get() = _rooms.toList()

    fun incWater(water: Double) {
        _waterCounter.doubleValue += water
    }

    fun incEnergy(energy: Double) {
        _energyCounter.doubleValue += energy
    }

    fun turnRoom(room: Room, isSelected: Boolean) {
        room.isSelected = isSelected
    }

    fun turnDevice(device: Device, isTurnedOn: Boolean) {
        device.turnedOn = isTurnedOn
    }

    fun addRoom(newRoom: Room): Room {
        _rooms.add(newRoom)
        return _rooms.last()
    }

    fun addDevice(newDevice: Device): Device {
        _devices.add(newDevice)
        selectArea()
        installTurnedOnDevices()
        return _devices.last()
    }


}