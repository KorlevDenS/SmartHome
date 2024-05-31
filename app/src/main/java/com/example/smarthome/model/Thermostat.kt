package com.example.smarthome.model

class Thermostat(
    override var name: String,
    override var turnedOn: Boolean,
    override var room: Room,
    override var powerInWatts: Double,
    var temperature: Double
): Device(name, turnedOn, room, powerInWatts) {
}