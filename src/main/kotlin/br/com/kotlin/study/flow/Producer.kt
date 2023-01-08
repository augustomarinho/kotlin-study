package br.com.kotlin.study.flow

import br.com.kotlin.study.flow.Producer.Companion.JOB_CUSTOM_POOL
import br.com.kotlin.study.flow.Producer.Companion.JOB_DISPATCHER
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Producer {

    fun produce(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(250)
            println("[${Thread.currentThread().name}] Emitting $i")
            emit(i)
        }
    }

    companion object {
        val JOB_DISPATCHER = Dispatchers.IO.limitedParallelism(10)
        val JOB_CUSTOM_POOL = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    }
}

fun main() {

    val producer = Producer()
    val scope = CoroutineScope(JOB_DISPATCHER)
    scope.launch {
        producer.produce().collect { value ->
            println("[${Thread.currentThread().name}] Consume $value")
        }
    }
    runBlocking(JOB_CUSTOM_POOL) {
        producer.produce().collect { value ->
            println("[${Thread.currentThread().name}] Consume $value")
        }
    }

    runBlocking {
        println("[${Thread.currentThread().name}] Scope Active ${scope.isActive}")
        println("[${Thread.currentThread().name}] Cancel")
        scope.cancel()
        println("[${Thread.currentThread().name}] Scope Active ${scope.isActive}")
        JOB_CUSTOM_POOL.close()
    }
}
