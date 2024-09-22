package com.ingonoka.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.*

@ExperimentalCoroutinesApi
class ObjectPoolTest {

    @Test
    fun testCreation() {
        val pool = MockObjectPool(100, 0L)
        assertEquals(100, pool.capacity)
    }

    @Test
    fun testCheckout() {

        val pool = MockObjectPool(100, 0L)
        pool.nextMockObjects.add(MockObject("test"))

        val o = pool.checkOut {
            it.meetsSpec
        }

        assertEquals("test", o?.name)
    }

    @Test
    fun testCheckIn() {

        val pool = MockObjectPool(100, 0L)
        pool.nextMockObjects.add(MockObject("test"))

        val o = pool.checkOut {
            it.meetsSpec
        }

        assertEquals("test", o?.name)

        o?.let {
            pool.checkIn(it)
        }
    }

    @Test
    fun testCapacity() {
        val pool = MockObjectPool(1, 0L)
        val o = pool.checkOut { true }
        assertNull(pool.checkOut { true })
        o?.let { pool.checkIn(it) }
        assertNotNull(pool.checkOut { true })
    }

    @Test
    fun testExpiry() = runTest {
        withContext(Dispatchers.Default) {
            val pool = MockObjectPool(1, 1_000L)
            val o = pool.checkOut { true }
            o?.let { pool.checkIn(it) }
            assertEquals(o, pool.checkOut { true })
            o?.let { pool.checkIn(it) }

            delay(1_100)
            assertNotEquals(o, pool.checkOut { true })
            assertTrue(o?.isExpired == true)
        }
    }

    @Test
    fun testValidity() {
        val pool = MockObjectPool(2, 1_000L)
        pool.nextMockObjects.add(MockObject("test1").also { it.isValid = false })
        pool.nextMockObjects.add(MockObject("test2").also { it.isValid = true })
        val o1 = pool.checkOut { true }
        val o2 = pool.checkOut { true }
        o1?.let { pool.checkIn(it) }
        o2?.let { pool.checkIn(it) }

        val o = pool.checkOut { true }
        assertEquals("test2", o?.name)
    }


}

class MockObjectPool(capacity: Int, expiryTime: Long) :
    ObjectPool<MockObject>(capacity = capacity, expirationTime = expiryTime) {

    override fun create(): MockObject = nextMockObjects.removeFirstOrNull() ?: MockObject("single")

    override fun validate(o: MockObject?): Boolean = o?.isValid ?: false

    override fun expire(o: MockObject?) {
        o?.isExpired = true
    }

    var nextMockObjects = ArrayDeque<MockObject>()
}

class MockObject(val name: String) {
    var isValid = true
    var isExpired = false
    var meetsSpec = true

    override fun toString(): String = "$name (valid:$isValid)"
}