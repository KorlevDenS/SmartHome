package com.example.smarthome.model

class Lighting (
    override var name: String,
    override var turnedOn: Boolean,
    override var room: Room,
    override var powerInWatts: Double,
    var luminance: Double
): Device(name, turnedOn, room, powerInWatts) {
}