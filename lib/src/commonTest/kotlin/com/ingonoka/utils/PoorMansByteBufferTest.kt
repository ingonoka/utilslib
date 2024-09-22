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

class PoorMansByteBufferTest {

    @Test
    fun testEmptyArray() {
        assertFails { PoorMansByteBuffer(0) }
        assertFails { PoorMansByteBuffer(-1) }

        val byteBuffer = PoorMansByteBuffer(4)
        assertEquals(4, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer.rewind()
        assertEquals(0, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer.writeByte(0x01)
        assertEquals(4, byteBuffer.size)
        assertEquals(1, byteBuffer.pos)

    }

    @ExperimentalStdlibApi
    @Test
    fun testAllocate() {

        var byteBuffer = PoorMansByteBuffer(10)
        assertEquals(10, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer = PoorMansByteBuffer()
        assertEquals(128, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer = PoorMansByteBuffer(byteArrayOf())
        assertEquals(0, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer.writeByteArray(ByteArray(129))
        assertEquals(2 * 128, byteBuffer.size)

        byteBuffer = PoorMansByteBuffer()
        assertEquals(128, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer.writeByteArray(ByteArray(128))
        assertEquals(128, byteBuffer.size)
        assertEquals(128, byteBuffer.pos)

        byteBuffer.writeByte(0x00)
        assertEquals(2 * 128, byteBuffer.size)
        assertEquals(129, byteBuffer.pos)

        byteBuffer = PoorMansByteBuffer(4)
        assertEquals(4, byteBuffer.size)
        assertEquals(0, byteBuffer.pos)

        byteBuffer.writeInt(0)
        assertEquals(4, byteBuffer.size)
        assertEquals(4, byteBuffer.pos)

        byteBuffer.writeInt(0)
        assertEquals(12, byteBuffer.size)
        assertEquals(8, byteBuffer.pos)

        byteBuffer = PoorMansByteBuffer()
        byteBuffer.writeByteArray(ByteArray(128))
        assertEquals(128, byteBuffer.size)
        assertEquals(128, byteBuffer.pos)

        byteBuffer.writeByteArray(ByteArray(128))
        assertEquals(3 * 128, byteBuffer.size)
        assertEquals(2 * 128, byteBuffer.pos)

    }

    @Test
    fun testReadWriteByte() {
        val byteBuffer = PoorMansByteBuffer(2)
        byteBuffer.writeByte(1)
        assertEquals(1, byteBuffer.pos)
        byteBuffer.writeByte(2)
        assertEquals(2, byteBuffer.pos)
        byteBuffer.writeByte(3)
        assertEquals(3, byteBuffer.pos)

        byteBuffer.rewind()
        assertEquals(1, byteBuffer.readByte().materialize { 0 })
        assertEquals(2, byteBuffer.readByte().materialize { 0 })
        assertEquals(3, byteBuffer.readByte().materialize { 0 })

        byteBuffer.rewind()
        byteBuffer.writeByte(0)
        byteBuffer.writeByte(-1)
        byteBuffer.writeByte(Byte.MAX_VALUE)
        byteBuffer.writeByte(Byte.MIN_VALUE)
        byteBuffer.rewind()
        assertEquals(0, byteBuffer.readByte().materialize { 1 })
        assertEquals(-1, byteBuffer.readByte().materialize { 0 })
        assertEquals(Byte.MAX_VALUE, byteBuffer.readByte().materialize { 0 })
        assertEquals(Byte.MIN_VALUE, byteBuffer.readByte().materialize { 0 })

        byteBuffer.rewind()
        byteBuffer.writeByteArray("FF".hexToBytes())
        byteBuffer.rewind()
        assertEquals(-1, byteBuffer.readByte().materialize { throw it })

        byteBuffer.rewind()
        byteBuffer.writeByteArray("FFFF".hexToBytes())
        byteBuffer.rewind()
        assertEquals(-1, byteBuffer.readByte().materialize { throw it })
        assertEquals(-1, byteBuffer.readByte().materialize { throw it })
        assertEquals(0,
            try {
                byteBuffer.readByte().materialize {
                    println(it.message)
                    throw it
                }
                1
            } catch (e: Exception) {
                0
            }
        )


    }

    @Test
    fun testReadWriteInt() {
        val n = 8
        val byteBuffer = PoorMansByteBuffer(n)
        byteBuffer.writeInt(1)
        assertEquals(4, byteBuffer.pos)
        byteBuffer.writeInt(1)
        assertEquals(8, byteBuffer.pos)
        assertEquals(n, byteBuffer.size)
        byteBuffer.writeInt(1)
        assertEquals(12, byteBuffer.pos)
        assertEquals(2 * n, byteBuffer.size)
        byteBuffer.rewind()
        assertEquals(12, byteBuffer.size)
        assertEquals(1, byteBuffer.readInt().getOrDefault(0))
        assertEquals(1, byteBuffer.readInt().getOrDefault(0))
        assertEquals(1, byteBuffer.readInt().getOrDefault(0))
        assertEquals(0, byteBuffer.readInt().getOrDefault(0))

        byteBuffer.rewind()

        byteBuffer.writeInt(0)
        byteBuffer.writeInt(-1)
        byteBuffer.writeInt(Int.MAX_VALUE)
        byteBuffer.writeInt(Int.MIN_VALUE)
        byteBuffer.rewind()
        assertEquals(0, byteBuffer.readInt().getOrDefault(0))
        assertEquals(-1, byteBuffer.readInt().getOrDefault(0))
        assertEquals(Int.MAX_VALUE, byteBuffer.readInt().getOrDefault(0))
        assertEquals(Int.MIN_VALUE, byteBuffer.readInt().getOrDefault(0))

        byteBuffer.rewind()
        byteBuffer.writeByteArray("FFFFFFFF".hexToBytes())
        byteBuffer.rewind()
        assertEquals(-1, byteBuffer.readInt().getOrThrow())

    }

    @Test
    fun testReadWriteLong() {
        val n = 16
        val byteBuffer = PoorMansByteBuffer(n)
        byteBuffer.writeLong(1)
        assertEquals(8, byteBuffer.pos)
        byteBuffer.writeLong(1)
        assertEquals(n, byteBuffer.pos)
        assertEquals(16, byteBuffer.size)
        byteBuffer.writeLong(1)
        assertEquals(24, byteBuffer.pos)
        assertEquals(2 * n, byteBuffer.size)
        byteBuffer.rewind()
        assertEquals(24, byteBuffer.size)
        assertEquals(1, byteBuffer.readLong().getOrDefault(0))
        assertEquals(1, byteBuffer.readLong().getOrDefault(0))
        assertEquals(1, byteBuffer.readLong().getOrDefault(0))
        assertEquals(0, byteBuffer.readLong().getOrDefault(0))

        byteBuffer.rewind()

        byteBuffer.writeLong(0)
        byteBuffer.writeLong(-1)
        byteBuffer.writeLong(Long.MAX_VALUE)
        byteBuffer.writeLong(Long.MIN_VALUE)
        byteBuffer.rewind()
        assertEquals(0, byteBuffer.readLong().getOrDefault(0))
        assertEquals(-1, byteBuffer.readLong().getOrDefault(0))
        assertEquals(Long.MAX_VALUE, byteBuffer.readLong().getOrDefault(0))
        assertEquals(Long.MIN_VALUE, byteBuffer.readLong().getOrDefault(0))

        byteBuffer.rewind()
        byteBuffer.writeByteArray("FFFFFFFFFFFFFFFF".hexToBytes())
        byteBuffer.rewind()
        assertEquals(-1, byteBuffer.readLong().getOrThrow())

    }

    @Test
    fun testReadWriteByteArray() {
        val byteBuffer = PoorMansByteBuffer(10)
        byteBuffer.writeByteArray(ByteArray(10) { i -> i.toByte() })
        byteBuffer.rewind()
        assertTrue(
            byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .contentEquals(byteBuffer.readByteArray(10).getOrThrow())
        )
        byteBuffer.rewind()
        assertTrue(
            byteArrayOf(0, 1, 2, 3, 4)
                .contentEquals(byteBuffer.readByteArray(5).getOrThrow())
        )
        assertTrue(
            byteArrayOf(5, 6, 7, 8, 9)
                .contentEquals(byteBuffer.readByteArray(5).getOrThrow())
        )
    }

    @Test
    fun testHashCode() {

        // hashcode with zero-length backing array and with non-zero-length backing array the same if no data
        // written yet
        var hc = PoorMansByteBuffer(byteArrayOf()).hashCode()
        var hc1 = PoorMansByteBuffer(10).hashCode()
        assertEquals(hc, hc1)
        assertEquals(1, hc)

        // Empty array and array with zero byte content have different hash codes
        hc = PoorMansByteBuffer(byteArrayOf()).hashCode()
        hc1 = PoorMansByteBuffer(10).also {
            it.writeInt(0)
        }.hashCode()

        assertTrue { hc != hc1 }

        // different size backing array, but same written data creates same hash code
        hc = PoorMansByteBuffer(20).also {
            it.writeInt(0)
        }.hashCode()
        hc1 = PoorMansByteBuffer(10).also {
            it.writeInt(0)
        }.hashCode()

        assertEquals(hc, hc1)

        // same size backing array, but different written data creates different hash code
        hc = PoorMansByteBuffer(10).also {
            it.writeInt(1)
        }.hashCode()
        hc1 = PoorMansByteBuffer(10).also {
            it.writeInt(0)
        }.hashCode()

        assertTrue { hc != hc1 }

    }

    @Test
    fun testReadByteArray() {
        val buf = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val pbuf = PoorMansByteBuffer(buf)

        var res = pbuf.readByteArray().getOrThrow()

        assertArrayEquals(buf.toTypedArray(), res.toTypedArray())

        res = pbuf.readByteArray().getOrThrow()
        assertArrayEquals(byteArrayOf().toTypedArray(), res.toTypedArray())

        pbuf.rewind()
        res = pbuf.readByteArray(10).getOrThrow()
        assertArrayEquals(buf.toTypedArray(), res.toTypedArray())

        res = pbuf.readByteArray().getOrThrow()
        assertArrayEquals(byteArrayOf().toTypedArray(), res.toTypedArray())

        pbuf.rewind()
        res = pbuf.readByteArray(5).getOrThrow()
        assertArrayEquals(buf.take(5).toTypedArray(), res.toTypedArray())

        res = pbuf.readByteArray().getOrThrow()
        assertArrayEquals(buf.drop(5).toTypedArray(), res.toTypedArray())

        res = pbuf.readByteArray().getOrThrow()
        assertArrayEquals(byteArrayOf().toTypedArray(), res.toTypedArray())

        res = pbuf.readByteArray().getOrThrow()
        assertArrayEquals(byteArrayOf().toTypedArray(), res.toTypedArray())

        assertFails {
            pbuf.readByteArray(1).getOrThrow()
        }
    }

    @Test
    fun testHasRemaining() {

        val pbuf = PoorMansByteBuffer(10)

        assertTrue(pbuf.hasRemaining())

        pbuf.writeByteArray(byteArrayOf(0, 1, 2, 3, 4))
        assertTrue(pbuf.hasRemaining())
        pbuf.rewind()
        assertTrue(pbuf.hasRemaining())

    }

    @Test
    fun testRemaining() {

        val pbuf = PoorMansByteBuffer(10)

        assertTrue(pbuf.hasRemaining())
        assertEquals(10, pbuf.remaining())

        pbuf.writeByteArray(byteArrayOf(0, 1, 2, 3))
        assertTrue(pbuf.hasRemaining())
        assertEquals(6, pbuf.remaining())

        pbuf.rewind()
        assertTrue(pbuf.hasRemaining())
        assertEquals(4, pbuf.remaining())

    }
}