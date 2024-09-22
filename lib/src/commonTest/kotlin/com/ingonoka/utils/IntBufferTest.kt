/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import com.ingonoka.hexutils.hexToBytes
import kotlin.test.*

class IntBufferTest {

    @Test
    fun testEquals() {
        val b1 = listOf(1, 2, 3, 4, 5, 6).listOfIntBuffer()
        val b2 = listOf(1, 2, 3, 4, 5, 6).listOfIntBuffer()
        assertEquals(b1, b2)

        val b3 = IntBuffer.empty(10).also { it.write(listOf(1, 2, 3, 4, 5, 6)) }
        val b4 = IntBuffer.empty(10).also { it.write(listOf(1, 2, 3, 4, 5, 6)) }
        assertEquals(b3, b4)

        val b5 = IntBuffer.empty(10).also { it.write(listOf(1, 2, 3, 4, 5, 6)) }
        val b6 = IntBuffer.empty(10).also { it.write(listOf(1, 2, 3, 4, 5)) }
        assertNotEquals(b5, b6)
    }

    @Test
    fun testGetList() {
        IntBuffer.wrap(1, 2, 3, 4, 5, 6).apply {
            assertEquals(listOf(1, 2, 3, 4, 5, 6), toList())
        }
        IntBuffer.empty(10).apply {
            write(listOf(1, 2, 3))
            assertEquals(listOf(1, 2, 3), toList())
        }
    }

    @Test
    fun testEmptyArray() {
        assertFails { IntBuffer.empty(-1) }

        val byteBuffer = IntBuffer.empty(4)
        assertEquals(4, byteBuffer.capacity)
        assertEquals(0, byteBuffer.position)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(4, byteBuffer.capacity)
            assertEquals(0, byteBuffer.position)
        }

        byteBuffer.writeByte(1)
        assertEquals(1, byteBuffer.position)

    }

    @Test
    fun testAllocate() {

        var byteBuffer = IntBuffer.empty(10)
        assertEquals(10, byteBuffer.capacity)
        assertEquals(0, byteBuffer.position)

        byteBuffer = IntBuffer.empty()
        assertEquals(MIN_EXTEND_SIZE, byteBuffer.capacity)
        assertEquals(0, byteBuffer.position)

        byteBuffer.write(List(MIN_EXTEND_SIZE) { 0 })
        assertEquals(MIN_EXTEND_SIZE, byteBuffer.capacity)
        assertEquals(MIN_EXTEND_SIZE, byteBuffer.position)

        byteBuffer.writeByte(0x00)
        assertEquals(MIN_EXTEND_SIZE + 1, byteBuffer.position)

        byteBuffer = IntBuffer.empty(4)
        byteBuffer.write(0)
        assertEquals(4, byteBuffer.position)
        byteBuffer.write(0)
        assertEquals(8, byteBuffer.position)

        byteBuffer = IntBuffer.empty()
        byteBuffer.write(List(MIN_EXTEND_SIZE) { 0 })
        assertEquals(MIN_EXTEND_SIZE, byteBuffer.position)

        byteBuffer.write(List(MIN_EXTEND_SIZE) { 0 })
        assertEquals(2 * MIN_EXTEND_SIZE, byteBuffer.position)

    }

    @Test
    fun testReadByte() {

        val buf1 = IntBuffer.wrap(listOf(0, 1, 2, 3, 4, 5))

        assertEquals(0, buf1.position)
        assertEquals(6, buf1.watermark)
        (0..5).forEach {
            assertEquals(it, buf1.readByte().getOrThrow())
        }
        assertFails { buf1.readByte().getOrThrow() }
        assertEquals(6, buf1.position)

        val buf2 = IntBuffer.wrap(listOf(0, 1, 2, 3, 4, 5), 3)
        assertEquals(0, buf2.position)
        assertEquals(3, buf2.watermark)
        (0..2).forEach {
            assertEquals(it, buf2.readByte().getOrThrow())
        }
        assertFails { buf2.readByte().getOrThrow() }
        assertEquals(3, buf2.position)
    }

    @Test
    fun testReadByteOrNull() {

        val buf1 = IntBuffer.wrap(listOf(0, 1, 2, 3, 4, 5))

        assertEquals(0, buf1.position)
        assertEquals(6, buf1.watermark)
        (0..5).forEach {
            assertEquals(it, buf1.readByteOrNull())
        }

        assertNull(buf1.readByteOrNull())
        assertEquals(6, buf1.position)

        val buf2 = IntBuffer.wrap(listOf(0, 1, 2, 3, 4, 5), 3)
        assertEquals(0, buf2.position)
        assertEquals(3, buf2.watermark)
        (0..2).forEach {
            assertEquals(it, buf2.readByteOrNull())
        }
        assertNull(buf2.readByteOrNull())
        assertEquals(3, buf2.position)
    }

    @Test
    fun testReadWriteByte() {
        val byteBuffer = IntBuffer.empty(2)
        byteBuffer.writeByte(1)
        assertEquals(1, byteBuffer.position)
        byteBuffer.writeByte(2)
        assertEquals(2, byteBuffer.position)
        byteBuffer.writeByte(3)
        assertEquals(3, byteBuffer.position)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readByte().getOrDefault(0))
            assertEquals(2, readByte().getOrDefault(0))
            assertEquals(3, readByte().getOrDefault(0))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readByteOrNull())
            assertEquals(2, readByteOrNull())
            assertEquals(3, readByteOrNull())
        }

        byteBuffer.reset()
        byteBuffer.writeByte(0)
        byteBuffer.writeByte((-1))
        byteBuffer.writeByte(Byte.MAX_VALUE.toInt())
        byteBuffer.writeByte(Byte.MIN_VALUE.toInt())

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readByte().getOrDefault(1))
            assertEquals((-1), readByte().getOrDefault(0))
            assertEquals(Byte.MAX_VALUE.toInt(), readByte().getOrDefault(0))
            assertEquals(Byte.MIN_VALUE.toInt(), readByte().getOrDefault(0))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readByteOrNull())
            assertEquals((-1), readByteOrNull())
            assertEquals(Byte.MAX_VALUE.toInt(), readByteOrNull())
            assertEquals(Byte.MIN_VALUE.toInt(), readByteOrNull())
        }

        byteBuffer.reset()
        byteBuffer.write("FF".hexToBytes())

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readByte().getOrThrow())
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readByteOrNull())
        }

        byteBuffer.reset()
        @Suppress("SpellCheckingInspection")
        byteBuffer.write("FFFF".hexToBytes())

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readByte().getOrThrow())
            assertEquals(-1, readByte().getOrThrow())
            assertFails { readByte().getOrThrow() }
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readByteOrNull())
            assertEquals(-1, readByteOrNull())
            assertNull(readByteOrNull())
        }

        assertFails { byteBuffer.writeByte(255) }
    }

    @Test
    fun testPeekByte() {
        val byteBuffer = IntBuffer.wrap(1, 2, 3)
        assertEquals(0, byteBuffer.position)
        assertEquals(1, byteBuffer.peekByteOrNull())
        assertEquals(1, byteBuffer.peekByte().getOrThrow())
        assertEquals(0, byteBuffer.position)

        byteBuffer.readByte()

        assertEquals(1, byteBuffer.position)
        assertEquals(2, byteBuffer.peekByteOrNull())
        assertEquals(2, byteBuffer.peekByte().getOrThrow())
        assertEquals(1, byteBuffer.position)
    }

    @Test
    fun testReadWriteInt() {
        val n = 8
        val byteBuffer = IntBuffer.empty(n)
        byteBuffer.write(1)
        assertEquals(4, byteBuffer.position)
        byteBuffer.write(2)
        assertEquals(8, byteBuffer.position)
        byteBuffer.write(3)
        assertEquals(12, byteBuffer.position)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readInt().getOrDefault(0))
            assertEquals(2, readInt().getOrDefault(0))
            assertEquals(3, readInt().getOrDefault(0))
            assertFails { readInt().getOrThrow() }
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readIntOrNull())
            assertEquals(2, readIntOrNull())
            assertEquals(3, readIntOrNull())
            assertNull(readIntOrNull())
        }

        byteBuffer.reset()
        byteBuffer.write(0)
        byteBuffer.write(-1)
        byteBuffer.write(Int.MAX_VALUE)
        byteBuffer.write(Int.MIN_VALUE)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readInt().getOrDefault(1))
            assertEquals(-1, readInt().getOrDefault(0))
            assertEquals(Int.MAX_VALUE, readInt().getOrDefault(0))
            assertEquals(Int.MIN_VALUE, readInt().getOrDefault(0))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readIntOrNull())
            assertEquals(-1, readIntOrNull())
            assertEquals(Int.MAX_VALUE, readIntOrNull())
            assertEquals(Int.MIN_VALUE, readIntOrNull())
        }

        byteBuffer.reset()
        @Suppress("SpellCheckingInspection")
        byteBuffer.write("FFFFFFFF".hexToBytes())
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readInt().getOrThrow())
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1, readIntOrNull())
        }

        byteBuffer.reset()
        byteBuffer.write(listOf(1, 0, 0, 0))
        byteBuffer.write(listOf(0, 0, 0, 1))
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readInt(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readInt(byteOrder = ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1, byteOrder = ByteOrder.LITTLE_ENDIAN)
        byteBuffer.write(1, byteOrder = ByteOrder.BIG_ENDIAN)
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readInt(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readInt(byteOrder = ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(0xFF, 1)
        assertEquals(1, byteBuffer.position)
        assertFails { byteBuffer.write(256, 1) }

        byteBuffer.write(0xFFFF, 2)
        assertEquals(3, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFF + 1, 2) }

        byteBuffer.write(0xFFFFFF, 3)
        assertEquals(6, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFF + 1, 3) }

        byteBuffer.write(-1, 4)
        assertEquals(10, byteBuffer.position)

        assertFails { byteBuffer.write(-1, 3) }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0xFF, readInt(1).getOrThrow())
            assertEquals(0xFFFF, readInt(2).getOrThrow())
            assertEquals(0xFFFFFF, readInt(3).getOrThrow())
            assertEquals(-1, readInt().getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1, 1, ByteOrder.LITTLE_ENDIAN)
        assertEquals(1, byteBuffer.position)

        byteBuffer.write(1, 2, ByteOrder.LITTLE_ENDIAN)
        assertEquals(3, byteBuffer.position)

        byteBuffer.write(1, 3, ByteOrder.LITTLE_ENDIAN)
        assertEquals(6, byteBuffer.position)

        byteBuffer.write(1, 4, ByteOrder.LITTLE_ENDIAN)
        assertEquals(10, byteBuffer.position)

        byteBuffer.write(Int.MIN_VALUE, 4, ByteOrder.LITTLE_ENDIAN)
        assertEquals(14, byteBuffer.position)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readInt(1, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readInt(2, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readInt(3, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readInt(4, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(Int.MIN_VALUE, readInt(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1, 4, ByteOrder.LITTLE_ENDIAN)
        byteBuffer.toReadListOfIntBuffer().apply {
            assertNotEquals(1, readInt(4, ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(200)
        assertEquals(listOf(0, 0, 0, -56), byteBuffer.toList())

    }

    @Test
    fun testPeekInt() {
        val byteBuffer = IntBuffer.wrap(0, 0, 0, 1, 0, 0, 0, 2)
        assertEquals(0, byteBuffer.position)
        assertEquals(1, byteBuffer.peekIntOrNull())
        assertEquals(1, byteBuffer.peekInt().getOrThrow())
        assertEquals(0, byteBuffer.position)

        byteBuffer.readInt()

        assertEquals(4, byteBuffer.position)
        assertEquals(2, byteBuffer.peekIntOrNull())
        assertEquals(2, byteBuffer.peekInt().getOrThrow())
        assertEquals(4, byteBuffer.position)
    }

    @Test
    fun testReadWriteLong() {
        val n = 16
        val byteBuffer = IntBuffer.empty(n)
        byteBuffer.write(1L)
        assertEquals(8, byteBuffer.position)
        byteBuffer.write(2L)
        assertEquals(n, byteBuffer.position)
        byteBuffer.write(3L)
        assertEquals(24, byteBuffer.position)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1L, readLong().getOrDefault(0))
            assertEquals(2L, readLong().getOrDefault(0))
            assertEquals(3L, readLong().getOrDefault(0))
            assertFails { readLong().getOrThrow() }
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1L, readLongOrNull())
            assertEquals(2L, readLongOrNull())
            assertEquals(3L, readLongOrNull())
            assertNull(readLongOrNull())
        }

        byteBuffer.reset()
        byteBuffer.write(0L)
        byteBuffer.write(-1L)
        byteBuffer.write(Long.MAX_VALUE)
        byteBuffer.write(Long.MIN_VALUE)

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readLong().getOrDefault(1))
            assertEquals(-1L, readLong().getOrDefault(0))
            assertEquals(Long.MAX_VALUE, readLong().getOrDefault(0))
            assertEquals(Long.MIN_VALUE, readLong().getOrDefault(0))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0, readLongOrNull())
            assertEquals(-1L, readLongOrNull())
            assertEquals(Long.MAX_VALUE, readLongOrNull())
            assertEquals(Long.MIN_VALUE, readLongOrNull())
        }

        byteBuffer.reset()
        @Suppress("SpellCheckingInspection")
        byteBuffer.write("FFFFFFFFFFFFFFFF".hexToBytes())
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1L, readLong().getOrThrow())
            assertFails { readLong().getOrThrow() }
        }
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(-1L, readLongOrNull())
            assertNull(readLongOrNull())
        }

        byteBuffer.reset()
        byteBuffer.write(listOf(1, 0, 0, 0, 0, 0, 0, 0))
        byteBuffer.write(listOf(0, 0, 0, 0, 0, 0, 0, 1))
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readLong(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readLong(byteOrder = ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1L, byteOrder = ByteOrder.LITTLE_ENDIAN)
        byteBuffer.write(1L, byteOrder = ByteOrder.BIG_ENDIAN)
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1, readLong(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1, readLong(byteOrder = ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(0xFFL, 1)
        assertEquals(1, byteBuffer.position)
        assertFails { byteBuffer.write(256L, 1) }

        byteBuffer.write(0xFFFFL, 2)
        assertEquals(3, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFF + 1, 2) }

        byteBuffer.write(0xFFFFFFL, 3)
        assertEquals(6, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFF + 1, 3) }

        byteBuffer.write(0xFFFFFFFFL, 4)
        assertEquals(10, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFF + 1, 4) }

        byteBuffer.write(0xFFFFFFFFFFL, 5)
        assertEquals(15, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFF + 1, 5) }

        byteBuffer.write(0xFFFFFFFFFFFFL, 6)
        assertEquals(21, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFFFF + 1, 6) }

        byteBuffer.write(0xFFFFFFFFFFFFFFL, 7)
        assertEquals(28, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFFFFFF + 1, 7) }

        byteBuffer.write(-1L, 8)
        assertEquals(36, byteBuffer.position)
        assertFails { byteBuffer.write(-1L, 7) }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(0xFFL, readLong(1).getOrThrow())
            assertEquals(0xFFFFL, readLong(2).getOrThrow())
            assertEquals(0xFFFFFFL, readLong(3).getOrThrow())
            assertEquals(0xFFFFFFFFL, readLong(4).getOrThrow())
            assertEquals(0xFFFFFFFFFFL, readLong(5).getOrThrow())
            assertEquals(0xFFFFFFFFFFFFL, readLong(6).getOrThrow())
            assertEquals(0xFFFFFFFFFFFFFFL, readLong(7).getOrThrow())
            assertEquals(-1L, readLong().getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1, 1, ByteOrder.LITTLE_ENDIAN)
        assertEquals(1, byteBuffer.position)
        assertFails { byteBuffer.write(256L, 1) }

        byteBuffer.write(1L, 2, ByteOrder.LITTLE_ENDIAN)
        assertEquals(3, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFF + 1, 2) }

        byteBuffer.write(1L, 3, ByteOrder.LITTLE_ENDIAN)
        assertEquals(6, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFF + 1, 3) }

        byteBuffer.write(1L, 4, ByteOrder.LITTLE_ENDIAN)
        assertEquals(10, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFF + 1, 4) }

        byteBuffer.write(1L, 5, ByteOrder.LITTLE_ENDIAN)
        assertEquals(15, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFF + 1, 5) }

        byteBuffer.write(1L, 6, ByteOrder.LITTLE_ENDIAN)
        assertEquals(21, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFFFF + 1, 6) }

        byteBuffer.write(1L, 7, ByteOrder.LITTLE_ENDIAN)
        assertEquals(28, byteBuffer.position)
        assertFails { byteBuffer.write(0xFFFFFFFFFFFFFF + 1, 7) }

        byteBuffer.write(Long.MIN_VALUE, 8, ByteOrder.LITTLE_ENDIAN)
        assertEquals(36, byteBuffer.position)
        assertFails { byteBuffer.write(-1L, 7) }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(1L, readLong(1, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(2, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(3, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(4, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(5, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(6, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(1L, readLong(7, ByteOrder.LITTLE_ENDIAN).getOrThrow())
            assertEquals(Long.MIN_VALUE, readLong(byteOrder = ByteOrder.LITTLE_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(1L, 4, ByteOrder.LITTLE_ENDIAN)
        byteBuffer.toReadListOfIntBuffer().apply {
            assertNotEquals(1L, readLong(4, ByteOrder.BIG_ENDIAN).getOrThrow())
        }

        byteBuffer.reset()
        byteBuffer.write(200L)
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, -56), byteBuffer.toList())

    }

    @Test
    fun testPeekLong() {
        val byteBuffer = IntBuffer.wrap(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2)
        assertEquals(0, byteBuffer.position)
        assertEquals(1, byteBuffer.peekLongOrNull())
        assertEquals(1, byteBuffer.peekLong().getOrThrow())
        assertEquals(0, byteBuffer.position)

        byteBuffer.readLong()

        assertEquals(8, byteBuffer.position)
        assertEquals(2, byteBuffer.peekLongOrNull())
        assertEquals(2, byteBuffer.peekLong().getOrThrow())
        assertEquals(8, byteBuffer.position)
    }

    @Test
    fun testReadWriteByteArray() {
        val byteBuffer = IntBuffer.empty(10)

        byteBuffer.write(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))

        byteBuffer.toReadListOfIntBuffer().apply {
            assertTrue(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).contentEquals(readByteArray(10).getOrThrow()))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertTrue(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).contentEquals(readByteArrayOrNull(10)))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertTrue(byteArrayOf(0, 1, 2, 3, 4).contentEquals(readByteArray(5).getOrThrow()))
            assertTrue(byteArrayOf(5, 6, 7, 8, 9).contentEquals(readByteArray(5).getOrThrow()))
        }
        byteBuffer.toReadListOfIntBuffer().apply {
            assertTrue(byteArrayOf(0, 1, 2, 3, 4).contentEquals(readByteArrayOrNull(5)))
            assertTrue(byteArrayOf(5, 6, 7, 8, 9).contentEquals(readByteArrayOrNull(5)))
        }
    }

    @Test
    fun testPeekByteArray() {
        val byteBuffer = IntBuffer.wrap(0, 0, 0, 1, 0, 0, 0, 2)
        assertEquals(0, byteBuffer.position)
        assertTrue { byteArrayOf(0, 0, 0, 1).contentEquals(byteBuffer.peekByteArrayOrNull(4)) }
        assertTrue { byteArrayOf(0, 0, 0, 1).contentEquals(byteBuffer.peekByteArray(4).getOrThrow()) }
        assertEquals(0, byteBuffer.position)

        byteBuffer.readByteArray(4)

        assertEquals(4, byteBuffer.position)
        assertTrue { byteArrayOf(0, 0, 0, 2).contentEquals(byteBuffer.peekByteArrayOrNull(4)) }
        assertTrue { byteArrayOf(0, 0, 0, 2).contentEquals(byteBuffer.peekByteArray(4).getOrThrow()) }
        assertEquals(4, byteBuffer.position)
    }

    @Test
    fun testReadWriteList() {
        val byteBuffer = IntBuffer.empty(10)

        byteBuffer.write(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), readList(10).getOrThrow())
        }
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), readListOrNull(10))
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(listOf(0, 1, 2, 3, 4), readList(5).getOrThrow())
            assertEquals(listOf(5, 6, 7, 8, 9), readList(5).getOrThrow())
        }
        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals(listOf(0, 1, 2, 3, 4), readListOrNull(5))
            assertEquals(listOf(5, 6, 7, 8, 9), readListOrNull(5))
        }
    }

    @Test
    fun testPeekList() {
        val byteBuffer = IntBuffer.wrap(0, 0, 0, 1, 0, 0, 0, 2)
        assertEquals(0, byteBuffer.position)
        assertEquals(listOf(0, 0, 0, 1), byteBuffer.peekList(4).getOrThrow())
        assertEquals(listOf(0, 0, 0, 1), byteBuffer.peekListOrNull(4))
        assertEquals(0, byteBuffer.position)

        byteBuffer.readList(4)

        assertEquals(4, byteBuffer.position)
        assertEquals(listOf(0, 0, 0, 2), byteBuffer.peekList(4).getOrThrow())
        assertEquals(listOf(0, 0, 0, 2), byteBuffer.peekListOrNull(4))
        assertEquals(4, byteBuffer.position)
    }

    @Test
    fun testReadWriteString() {
        val byteBuffer = IntBuffer.empty()
        byteBuffer.write("Test")

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals("Test", readString(4).getOrThrow())
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals("Test", readStringOrNull(4))
        }

        val posBefore = byteBuffer.position
        byteBuffer.write("")
        assertEquals(posBefore, byteBuffer.position)

        byteBuffer.reset()
        byteBuffer.write("Test1")
        byteBuffer.write("Test2")

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals("Test1", readString(5).getOrThrow())
            assertEquals("Test2", readString(5).getOrThrow())
        }

        byteBuffer.toReadListOfIntBuffer().apply {
            assertEquals("Test1", readStringOrNull(5))
            assertEquals("Test2", readStringOrNull(5))
        }
    }

    @Test
    fun testPeekString() {
        val byteBuffer = IntBuffer.wrap("FOOBAR".encodeToByteArray().toListOfInt())
        assertEquals(0, byteBuffer.position)
        assertEquals("FOO", byteBuffer.peekStringOrNull(3))
        assertEquals("FOO", byteBuffer.peekString(3).getOrThrow())
        assertEquals(0, byteBuffer.position)

        byteBuffer.readString(3)

        assertEquals(3, byteBuffer.position)
        assertEquals("BAR", byteBuffer.peekStringOrNull(3))
        assertEquals("BAR", byteBuffer.peekString(3).getOrThrow())
        assertEquals(3, byteBuffer.position)
    }

    @Test
    fun testHashCode() {

        // hashcode with zero-length backing array and with non-zero-length backing array the same if no data
        // written yet
        var hc = IntBuffer.wrap(listOf()).hashCode()
        var hc1 = IntBuffer.empty(10).hashCode()
        assertEquals(hc, hc1)
        assertEquals(1, hc)

        // Empty array and array with zero byte content have different hash codes
        hc = IntBuffer.wrap(listOf()).hashCode()
        hc1 = IntBuffer.wrap(listOf(0)).hashCode()

        assertTrue { hc != hc1 }

        // different size backing array, but same written data creates same hash code
        hc = IntBuffer.empty(20).also {
            it.write(0)
        }.hashCode()
        hc1 = IntBuffer.empty(10).also {
            it.write(0)
        }.hashCode()

        assertEquals(hc, hc1)

        // same size backing array, but different written data creates different hash code
        hc = IntBuffer.empty(10).also {
            it.write(1)
        }.hashCode()
        hc1 = IntBuffer.empty(10).also {
            it.write(0)
        }.hashCode()

        assertTrue { hc != hc1 }

    }

    @Test
    fun testReadRemaining() {
        val data = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val buf = IntBuffer.wrap(data)

        assertEquals(data, buf.readRemaining().getOrThrow())
        assertTrue { buf.readByteArray().getOrThrow().isEmpty() }

        buf.rewind()
        assertEquals(data, buf.readRemainingOrNull())
        assertTrue { buf.readByteArrayOrNull()?.isEmpty() == true }


        with(buf.rewind()) {
            readByteArray(10)
                .onSuccess { assertArrayEquals(data.toByteArray().toTypedArray(), it.toList().toTypedArray()) }
                .getOrThrow()

            assertTrue { readByteArray().getOrThrow().isEmpty() }

            assertFails { readByteArray(1).getOrThrow() }
        }

        with(buf.rewind()) {
            assertNotNull(
                readByteArrayOrNull(10)?.also {
                    assertArrayEquals(data.toByteArray().toTypedArray(), it.toList().toTypedArray())
                }
            )

            assertTrue { readByteArrayOrNull()?.isEmpty() == true }
            assertNull(readByteArrayOrNull(1))
        }

        with(buf.rewind()) {
            readByteArray(5)
                .onSuccess { assertArrayEquals(data.take(5).toByteArray().toTypedArray(), it.toList().toTypedArray()) }
                .getOrThrow()

            readByteArray(5)
                .onSuccess { assertArrayEquals(data.drop(5).toByteArray().toTypedArray(), it.toList().toTypedArray()) }
                .getOrThrow()

            readByteArray().onSuccess { assertTrue { it.isEmpty() } }.getOrThrow()

            assertFails { readByteArray(1).getOrThrow() }
        }

        with(buf.rewind()) {
            assertNotNull(
                readByteArrayOrNull(5)?.also {
                    assertArrayEquals(data.take(5).toByteArray().toTypedArray(), it.toList().toTypedArray())
                }
            )

            assertNotNull(
                readByteArrayOrNull(5)?.also {
                    assertArrayEquals(data.drop(5).toByteArray().toTypedArray(), it.toList().toTypedArray())
                }
            )

            assertTrue { readByteArrayOrNull()?.isEmpty() == true }
            assertNull(readByteArrayOrNull(1))

        }
    }

    @Test
    fun testPeekRemaining() {
        val buf = IntBuffer.wrap(1, 2, 3, 4, 5, 6, 7, 8)
        buf.readList(4)

        assertEquals(4, buf.position)
        assertEquals(listOf(5, 6, 7, 8), buf.peekRemaining().getOrThrow())
        assertEquals(listOf(5, 6, 7, 8), buf.peekRemainingOrNull())
        assertEquals(4, buf.position)

    }

    @Test
    fun testHasBytesLeftToRead() {

        val buf = IntBuffer.empty(10)
        buf.write(listOf(0, 1, 2, 3, 4))

        buf.toReadListOfIntBuffer().apply {
            assertTrue(hasBytesLeftToRead())
            assertTrue(hasBytesLeftToRead(5))
            assertFalse(hasBytesLeftToRead(6))
        }
    }

    @Test
    fun testBytesLeftToRead() {

        val buf = IntBuffer.empty(10)

        assertEquals(10, buf.capacity)

        buf.write(byteArrayOf(0, 1, 2, 3))
        assertEquals(4, buf.position)

        buf.toReadListOfIntBuffer().apply {
            assertEquals(4, bytesLeftToRead())
        }

        buf.reset()
        assertEquals(0, buf.position)
        assertEquals(10, buf.capacity)
        buf.toReadListOfIntBuffer().apply {
            assertEquals(0, bytesLeftToRead())
        }
    }
}