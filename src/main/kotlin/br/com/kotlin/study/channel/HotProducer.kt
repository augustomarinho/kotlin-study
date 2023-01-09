package br.com.kotlin.study.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HotProducer {

    fun produceNumbers(scope: CoroutineScope): ReceiveChannel<Int> = runBlocking {
        return@runBlocking scope.produce<Int> {
            var x = 1
            while (true) {
                send(x++)
                delay(1000)
            } // infinite stream of integers starting from 1
        }
    }
}

class HotConsumer {
    fun launchProcessor(id: String, channel: ReceiveChannel<Int>) = runBlocking {
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }
}

fun main() = runBlocking {
    val producer = HotProducer()
    val consumer = HotConsumer()
    val channel = producer.produceNumbers(this) // produces integers from 1 and on

    val job = launch {
        repeat(5) { consumer.launchProcessor("Job 1 $it", channel) }
    }

    delay(5000)
    job.cancel("Canceling Job 1")
    //numbers.cancel()
    println("Canceling consume")

    delay(5000)
    println("Start consuming again")
    val job2 = launch {
        repeat(5) { consumer.launchProcessor("Job 2 $it", channel) }
    }

    delay(5000)
    job2.cancel("Canceling Job 2")
    channel.cancel()
    println("Canceling consume")

    val job3 = launch {
        repeat(5) { consumer.launchProcessor("Job 3 $it", channel) }
    }
}