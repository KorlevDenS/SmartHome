package com.example.smarthome.model

open class Device(
    open var name: String,
    open var turnedOn: Boolean,
    open var room: Room,
    open var powerInWatts: Double
) {

}