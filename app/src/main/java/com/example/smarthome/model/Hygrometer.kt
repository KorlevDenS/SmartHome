package com.example.smarthome.model

class Hygrometer(
    override var name: String,
    override var turnedOn: Boolean,
    override var room: Room,
    override var powerInWatts: Double,
    var humidity: Double
): Device(name, turnedOn, room, powerInWatts) {
}