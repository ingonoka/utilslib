package com.ingonoka.utils

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration

/**
 * Collect from flow and submit received elements when received in less than [timeout]. If no
 * element has been received within [timeout], submit an empty list.
 *
 * Intended to be used for situation in which new arriving elements should be submitted immediately.
 * For example, a system in which heartbeats may be submitted based on events in the system (i.e.
 * unpredictable) and in which a heartbeat must be submitted every [timeout] intervals
 * regardless whether any event in the system triggered a heartbeat.
 */
suspend fun <E> Flow<E>.withTimeout(timeout: Duration): Flow<List<E>> =
    flow {

        coroutineScope {
            var nextElement: E? = null
            val emitSemaphore = Channel<Unit>()
            val collectSemaphore = Channel<Unit>()

            launch {
                this@withTimeout.collect { value ->
                    nextElement = value
                    emitSemaphore.send(Unit)
                    collectSemaphore.receive()
                }
                emitSemaphore.close()
            }

            var shouldCollectNext = true
            while (shouldCollectNext) {

                try {
                    kotlinx.coroutines.withTimeout(timeout) {

                        val valueOrClosed = emitSemaphore.receiveCatching()
                        emit(listOf(nextElement ?: throw IllegalStateException()))
                        shouldCollectNext = !valueOrClosed.isClosed
                        if (shouldCollectNext) {
                            collectSemaphore.send(Unit)
                        } else {
                            collectSemaphore.close()
                        }

                    }
                } catch (e: TimeoutCancellationException) {
                    emit(listOf())
                }
                shouldCollectNext = true
            }
        }
    }


/**
 * Collect from flow and submit received elements in a list when [interval] is over or [maxElements] has
 * been reached, whatever comes first. If [interval] is over, but no elements have been received, then
 * submit the empty list if [submitEmpty] is true, otherwise do nothing and keep collecting.
 */
suspend fun <E> Flow<E>.chunked(interval: Duration, maxElements: Int, submitEmpty: Boolean = false): Flow<List<E>> =
    flow {

        coroutineScope {
            val buffer = Channel<E>(maxElements)
            val emitSemaphore = Channel<Unit>()
            val collectSemaphore = Channel<Unit>()

            launch {
                this@chunked.collect { value ->
                    val hasCapacity = buffer.trySend(value).isSuccess
                    if (!hasCapacity) {
                        emitSemaphore.send(Unit)
                        collectSemaphore.receive()
                        buffer.send(value)
                    }
                }
                emitSemaphore.close()
                buffer.close()
            }

            var shouldCollectNextChunk = true
            while (shouldCollectNextChunk) {

                try {
                    kotlinx.coroutines.withTimeout(interval) {

                        val valueOrClosed = emitSemaphore.receiveCatching()
                        buffer.drain().takeIf { it.isNotEmpty() || submitEmpty }?.let { emit(it) }
                        shouldCollectNextChunk = !valueOrClosed.isClosed
                        if (shouldCollectNextChunk) {
                            collectSemaphore.send(Unit)
                        } else {
                            collectSemaphore.close()
                        }

                    }
                } catch (e: TimeoutCancellationException) {
                    if (submitEmpty) {
                        emit(buffer.drain())
                    } else {
                        val listOfE = buffer.awaitFirstAndDrain()
                        if (listOfE.isNotEmpty()) emit(listOfE)
                    }
                    shouldCollectNextChunk = true
                }
            }
        }
    }

private suspend fun <T> ReceiveChannel<T>.awaitFirstAndDrain(): List<T> {
    val first = receiveCatching().takeIf { it.isClosed.not() }?.getOrNull() ?: return emptyList()
    return drain(mutableListOf(first))
}

private tailrec fun <T> ReceiveChannel<T>.drain(acc: MutableList<T> = mutableListOf()): List<T> {
    val item = tryReceive().getOrNull()
    return if (item == null) acc
    else {
        acc.add(item)
        drain(acc)
    }
}