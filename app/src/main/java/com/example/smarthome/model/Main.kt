package com.example.smarthome.model

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    testChannel()
}

fun testChannel() = runBlocking {
    val channel = Channel<Pair<SharedObject, (SharedObject) -> Unit>>()
    val obj = SharedObject("Name", 10)

    launch {
        delay(5000)
        channel.send (Pair(obj){ o -> o.setCount(3)})
        println("sent " + obj.getCount())
    }

    launch {
        while (true) {
            val pair = channel.receive()
            pair.second(pair.first)
            println("got " + obj.getCount())
        }
    }
}

fun testDetect() = runBlocking {
    val obj = SharedObject("Name", 10)

    launch {
        val li: List<Int> = listOf(10, 6, 7, 8, 7, 32, 321, 234, 432)

        li.forEach {
            obj.setCount(it)
            delay(2000)
        }
    }

    launch {
        var currentCount: Int = obj.getCount()
        while (isActive) {
            if (obj.getCount() != currentCount) {
                println("Changed state: " + currentCount + " -> " + obj.getCount())
                currentCount = obj.getCount()
            }
            delay(500)
        }
    }


}

class SharedObject(
    private var name: String,
    private var count: Int
) {
    fun setName(name: String) {
        this.name = name
    }
    fun getName(): String {
        return this.name
    }

    fun getCount(): Int {
        return this.count
    }

    fun setCount(count: Int) {
        this.count = count
    }

}