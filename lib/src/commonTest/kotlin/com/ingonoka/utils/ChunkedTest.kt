package com.ingonoka.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
class ChunkedTest {

    @Test
    fun chunkedMaxElementsArriveBeforeTimeout() = runBlocking {

        val res = buildList {
            flow {
                repeat(5) {
                    emit(it)
                    delay(1.seconds)
                }
            }.chunked(6.seconds, 5).collect {
                add(it)
            }
        }

        assertEquals(1, res.size)
        assertEquals(5, res[0].size)

    }

    @Test
    fun testChunked() = runTest {

        var res = flow<Int> {
            emit(1)
            emit(2)
            emit(3)
            delay(5.seconds)
            emit(4)
        }.chunked(4.seconds, 4).toList()

        assertEquals(2, res.size)
        assertEquals(listOf(1, 2, 3), res[0])
        assertEquals(listOf(4), res[1])

        res = flow<Int> {
            emit(1)
            emit(2)
            emit(3)
            emit(4)
        }.chunked(4.seconds, 4).toList()

        assertEquals(1, res.size)
        assertEquals(listOf(1, 2, 3, 4), res[0])


        res = flow<Int> {
            delay(4.seconds)
            emit(1)
            emit(2)
            emit(3)
            emit(4)
        }.chunked(3.seconds, 4).toList()

        assertEquals(1, res.size)
        assertEquals(listOf(1, 2, 3, 4), res[0])

        res = flow<Int> {
            delay(4.seconds)
            emit(1)
            emit(2)
            delay(4.seconds)
            emit(3)
        }.chunked(3.seconds, 2).toList()

        assertEquals(2, res.size)
        assertEquals(listOf(1, 2), res[0])
        assertEquals(listOf(3), res[1])

        res = flow<Int> {
            delay(4.seconds)
        }.chunked(3.seconds, 2).toList()
        println(res)

        assertTrue { res.isEmpty() }

    }

    @Test
    fun testException() = runTest {
        val job = launch {
            flow {
                var i = 0
                repeat(5) {
                    println("emit: $i")
                    emit(i++)
                }
                while (true) {
                    delay(1.seconds)
                    println("emit: $i")
                    if (currentCoroutineContext().isActive) emit(i++)
                }
            }.chunked(2.seconds, 4).collect {
                println(it)
            }
        }

        delay(10.seconds)
        job.cancel()


    }



    @Test
    fun chunkedTimeoutBeforeMaxElementsReached() = runBlocking {

        val res = buildList {
            flow {
                repeat(5) {
                    emit(it)
                    delay(1.1.seconds)
                }
            }.chunked(3.seconds, 5).collect {
                add(it)
            }
        }

        assertEquals(2, res.size)
        assertEquals(3, res[0].size)
        assertEquals(2, res[1].size)

    }

    @Test
    fun chunkedNoElementsBeforeTimeoutSendNothing() = runBlocking {

        val res = buildList {
            flow {
                delay(2.1.seconds)
                emit(1)
            }.chunked(1.seconds, 5).collect {
                add(it)
            }
        }

        assertEquals(1, res.size)
        assertEquals(1, res[0].size)
    }

    @Test
    fun chunkedNoElementsBeforeTimeoutSendEmptyList() = runBlocking {

        val res = buildList {
            flow {
                delay(2.1.seconds)
                emit(1)
            }.chunked(1.seconds, 5, true).collect {
                add(it)
            }
        }

        assertEquals(listOf(emptyList(), emptyList(), listOf(1)), res)
    }

    @Test
    fun chunkedWithSahredFlow1() = runBlocking {

        val _sharedFlow = MutableSharedFlow<Int>(128)
        val sharedFlow = _sharedFlow.asSharedFlow()
        repeat(5) { i -> _sharedFlow.emit(i) }

        val res = buildList {
            sharedFlow.chunked(1.seconds, 1, true).take(5).collect {
                add(it)
            }
        }

        assertEquals(listOf(listOf(0), listOf(1), listOf(2), listOf(3), listOf(4)), res)
    }

    @Test
    fun chunkedWithSharedFlow1() = runBlocking {

        val _sharedFlow = MutableSharedFlow<Int>(128)
        val sharedFlow = _sharedFlow.asSharedFlow()
        _sharedFlow.emit(0)

        val res = buildList {
            sharedFlow.chunked(5.seconds, 1, true).take(1).collect {
                add(it)
            }
        }

        assertEquals(listOf(listOf(0)), res)
    }

    @Test
    fun testInterval() = runBlocking {
        val _sharedFlow = MutableSharedFlow<Int>(128)
        val sharedFlow = _sharedFlow.asSharedFlow()
        _sharedFlow.emit(0)

        val res = sharedFlow.withTimeout(5.seconds).take(1).first()

        assertEquals(listOf(0), res)
    }

    @Test
    fun testInterval1() = runBlocking {
        val _sharedFlow = MutableSharedFlow<Int>(128)
        val sharedFlow = _sharedFlow.asSharedFlow()

        val res = sharedFlow.withTimeout(5.seconds).take(1).first()

        assertEquals(emptyList(), res)
    }
}