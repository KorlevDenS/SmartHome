package com.example.smarthome.model

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.smarthome.navigation.MeterStatItem
import kotlinx.coroutines.channels.Channel

class HomeViewModel : ViewModel() {
    val deviceTurnChannel = Channel<Device>()

    private val _meterStats = mutableStateListOf<MeterStatItem>()

    private val _waterCounter = mutableDoubleStateOf(0.0)
    private val _energyCounter = mutableDoubleStateOf(0.0)

    private val _rooms = mutableStateListOf<Room>()
    private var _devices = mutableStateListOf<Device>()
    private var _selectedDevices = mutableStateOf<List<Device>>(_devices)
    private var _turnedOnDevices = mutableStateOf<List<Device>>(_devices)
    private var _wholeSpaceMode = mutableStateOf(false)

    val meterStats: List<MeterStatItem>
        get() = _meterStats

    var wholeSpaceMode: Boolean
        get() = _wholeSpaceMode.value
        set(isTurnedOn) {
            _wholeSpaceMode.value = isTurnedOn
        }

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
        _selectedDevices.value = _devices.filter { d -> d.room.isSelected || wholeSpaceMode}
    }

    val turnedOnDevices: List<Device>
        get() = _turnedOnDevices.value

    fun installTurnedOnDevices() {
        _turnedOnDevices.value = _devices.filter { d -> d.turnedOn }
    }

    val rooms: List<Room>
        get() = _rooms.toList()

    fun installTemperature(thermostat: Thermostat, value: Double) {
        val index = _devices.indexOf(thermostat)
        _devices[index] = Thermostat(thermostat.name, thermostat.turnedOn,
            thermostat.room, thermostat.powerInWatts, value)
        selectArea()
        installTurnedOnDevices()
    }

    fun installHumidity(hygrometer: Hygrometer, value: Double) {
        val index = _devices.indexOf(hygrometer)
        _devices[index] = Hygrometer(hygrometer.name, hygrometer.turnedOn,
            hygrometer.room, hygrometer.powerInWatts, value)
        selectArea()
        installTurnedOnDevices()
    }

    fun turnRoom(room: Room, isSelected: Boolean) {
        room.isSelected = isSelected
    }

    fun turnDevice(device: Device, isTurnedOn: Boolean) {
        device.turnedOn = isTurnedOn
        installTurnedOnDevices()
        deviceTurnChannel.trySend(device)
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

    fun deleteDevice(device: Device) {
        _devices.remove(device)
        selectArea()
        installTurnedOnDevices()
    }

    fun addMeterStats(stat: MeterStatItem) {
        _meterStats.add(stat)
    }


}