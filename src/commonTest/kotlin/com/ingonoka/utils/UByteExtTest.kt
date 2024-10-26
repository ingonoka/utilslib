package com.ingonoka.utils

import com.ingonoka.utils.ByteOrder.BIG_ENDIAN
import com.ingonoka.utils.ByteOrder.LITTLE_ENDIAN
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UByteExtTest {

    @Test
    fun testInline() {

        assertEquals((-1).toByte(), 0xff.b)
        assertEquals((-128).toByte(), (-128).b)
        assertEquals(127.toByte(), (-129).b)
        assertEquals(127.toByte(), 127.b)
        assertEquals((-128).toByte(), 128.b)

        assertEquals(-1, 0xff.bi)
        assertEquals(-128, (-128).bi)
        assertEquals(127, (-129).bi)
        assertEquals(127, 127.bi)
        assertEquals(-128, 128.bi)

        assertEquals(128, (-128).toByte().ui)
        assertEquals(128, (-128).toByte().ui)
        assertEquals(127, (-129).toByte().ui)
        assertEquals(127, 127.toByte().ui)
        assertEquals(128, 128.toByte().ui)


    }

    @Test
    fun testBufferToInt() {

        assertEquals(-1, BufferImpl.wrap( 0xFF, 0xFF, 0xFF, 0xFF).toInt())
        assertEquals(255, BufferImpl.wrap(0, 0, 0, 0xFF).toInt())
        assertEquals(255, BufferImpl.wrap(0xff, 0, 0, 0).toInt(byteOrder = LITTLE_ENDIAN))
        BufferImpl.wrap(0, 0, 0, 0, 0xff, 0).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255,
                buf.toInt(2, byteOrder = LITTLE_ENDIAN)
            )
        }
        BufferImpl.wrap(0, 0, 0, 0, 0, 0xFF).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255,
                buf.toInt(2, byteOrder = BIG_ENDIAN)
            )
        }
    }

    @Test
    fun testBufferToUInt() {

        assertEquals(0xFFFFFFFFu, BufferImpl.wrap( 0xFF, 0xFF, 0xFF, 0xFF).toUInt())
        assertEquals(255u, BufferImpl.wrap(0, 0, 0, 0xFF).toUInt())
        assertEquals(255u, BufferImpl.wrap(0xff, 0, 0, 0).toUInt(byteOrder = LITTLE_ENDIAN))
        BufferImpl.wrap(0, 0, 0, 0, 0xff, 0).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255u,
                buf.toUInt(2, byteOrder = LITTLE_ENDIAN)
            )
        }
        BufferImpl.wrap(0, 0, 0, 0, 0, 0xFF).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255u,
                buf.toUInt(2, byteOrder = BIG_ENDIAN)
            )
        }
    }

    @Test
    fun testBufferToLong() {

        assertEquals(-1, BufferImpl.wrap(0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF).toLong())
        assertEquals(255, BufferImpl.wrap(0, 0, 0, 0, 0, 0, 0, 0xFF).toLong())
        assertEquals(255, BufferImpl.wrap(0xff, 0, 0, 0, 0, 0, 0, 0).toLong(byteOrder = LITTLE_ENDIAN))
        BufferImpl.wrap(0, 0, 0, 0, 0xff, 0, 0, 0).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255,
                buf.toLong(4, byteOrder = LITTLE_ENDIAN)
            )
        }
        BufferImpl.wrap(0, 0, 0, 0, 0, 0, 0, 0xFF).also { buf ->
            buf.seekBy(4)
            assertEquals(
                255,
                buf.toLong(4, byteOrder = BIG_ENDIAN)
            )
        }
    }

    @Test
    fun testBufferToULong() {

        assertEquals(
            0xFFFFFFFFFFFFFFFFu,
            BufferImpl.wrap(0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF).toULong()
        )
        assertEquals(
            255u,
            BufferImpl.wrap(0, 0, 0, 0, 0, 0, 0, 0xFF).toULong()
        )
        assertEquals(
            255u,
            BufferImpl.wrap(0xff, 0, 0, 0, 0, 0, 0, 0).toULong(byteOrder = LITTLE_ENDIAN)
        )
        BufferImpl.wrap(0, 0, 0, 0, 0xff, 0, 0, 0).also { buf ->
            buf.seekBy(4)
            assertEquals(255, buf.toLong(4, byteOrder = LITTLE_ENDIAN))
        }
        BufferImpl.wrap(0, 0, 0, 0, 0, 0, 0, 0xFF).also { buf ->
            buf.seekBy(4)
            assertEquals(255, buf.toLong(4, byteOrder = BIG_ENDIAN))
        }
    }

    @Test
    fun testUIntToUBytes() {

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u), 0u.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u), 0u.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0u), 0u.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0u), 0u.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0u), 0u.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xffu.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu), 0xffu.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0xffu), 0xffu.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffu.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffu.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0x0u), 0xffu.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u), 0xffu.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u), 0xffu.toUBytes(2, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffu.toUBytes(1, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffu.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffffu.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0xffu, 0xffu), 0xffffu.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffu.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffu.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffu.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 1u), 0x0201u.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 0x1u), 0x0201u.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0x02u, 0x01u), 0x0201u.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201u.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201u.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0u), 0x0201u.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0x0u), 0x0201u.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u), 0x0201u.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201u.toUBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201u.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xffu.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0x7Fu), 127u.toUBytes())
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u), 0xff000000u.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffffu.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xffu.toUBytes())
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffu.toUBytes())
    }

    @Test
    fun testUIntToIntBytes() {

        assertContentEquals(listOf(0, 0, 0, 0), 0u.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 0), 0u.toIntBytes(3))
        assertContentEquals(listOf(0, 0), 0u.toIntBytes(2))
        assertContentEquals(listOf(0), 0u.toIntBytes(1))
        assertContentEquals(listOf(0), 0u.toIntBytes(0))


        assertContentEquals(listOf(0, 0, 0, 0xff), 0xffu.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 0xff), 0xffu.toIntBytes(3))
        assertContentEquals(listOf(0, 0xff), 0xffu.toIntBytes(2))
        assertContentEquals(listOf(0xff), 0xffu.toIntBytes(1))
        assertContentEquals(listOf(0xff), 0xffu.toIntBytes(0))


        assertContentEquals(listOf(0xff, 0, 0, 0x0), 0xffu.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff, 0, 0), 0xffu.toIntBytes(3, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff, 0), 0xffu.toIntBytes(2, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff), 0xffu.toIntBytes(1, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff), 0xffu.toIntBytes(0, LITTLE_ENDIAN))

        assertContentEquals(listOf(0, 0, 0xff, 0xff), 0xffffu.toIntBytes(4))
        assertContentEquals(listOf(0, 0xff, 0xff), 0xffffu.toIntBytes(3))
        assertContentEquals(listOf(0xff, 0xff), 0xffffu.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffu.toIntBytes(1) }
        assertContentEquals(listOf(0xff, 0xff), 0xffffu.toIntBytes(0))


        assertContentEquals(listOf(0, 0, 2, 1), 0x0201u.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 2, 0x1), 0x0201u.toIntBytes(4))
        assertContentEquals(listOf(0, 0x02, 0x01), 0x0201u.toIntBytes(3))
        assertContentEquals(listOf(0x02, 0x01), 0x0201u.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toIntBytes(1) }
        assertContentEquals(listOf(0x02, 0x01), 0x0201u.toIntBytes(0))

        assertContentEquals(listOf(0x01, 0x02, 0, 0), 0x0201u.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02, 0, 0x0), 0x0201u.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02, 0), 0x0201u.toIntBytes(3, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02), 0x0201u.toIntBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toIntBytes(1) }
        assertContentEquals(listOf(0x01, 0x02), 0x0201u.toIntBytes(0, LITTLE_ENDIAN))

        assertContentEquals(listOf(0, 0, 0, 0xff), 0xffu.toIntBytes())
        assertContentEquals(listOf(0, 0, 0, 0x7F), 127u.toIntBytes())
        assertContentEquals(listOf(0xff, 0, 0, 0), 0xff000000u.toIntBytes())
        assertContentEquals(listOf(0, 0, 0xff, 0xff), 0xffffu.toIntBytes())
        assertContentEquals(listOf(0, 0, 0, 0xff), 0xffu.toIntBytes())
        assertContentEquals(listOf(0xff, 0xff, 0xff, 0xff), 0xffffffffu.toIntBytes())
    }

    @Test
    fun testUIntToBytes() {

        assertContentEquals(byteArrayOf(0, 0, 0, 0), 0u.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0), 0u.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0), 0u.toBytes(2))
        assertContentEquals(byteArrayOf(0), 0u.toBytes(1))
        assertContentEquals(byteArrayOf(0), 0u.toBytes(0))


        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xffu.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b), 0xffu.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0xff.b), 0xffu.toBytes(2))
        assertContentEquals(byteArrayOf(0xff.b), 0xffu.toBytes(1))
        assertContentEquals(byteArrayOf(0xff.b), 0xffu.toBytes(0))


        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0x0), 0xffu.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0), 0xffu.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0), 0xffu.toBytes(2, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffu.toBytes(1, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffu.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffffu.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0xff.b, 0xff.b), 0xffffu.toBytes(3))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffu.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffu.toBytes(1) }
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffu.toBytes(0))


        assertContentEquals(byteArrayOf(0, 0, 2, 1), 0x0201u.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 2, 0x1), 0x0201u.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0x02, 0x01), 0x0201u.toBytes(3))
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201u.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toBytes(1) }
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201u.toBytes(0))

        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0), 0x0201u.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0x0), 0x0201u.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0), 0x0201u.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201u.toBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201u.toBytes(1) }
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201u.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xffu.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0x7F), 127u.toBytes())
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0), 0xff000000u.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffffu.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xffu.toBytes())
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffu.toBytes())
    }

    @Test
    fun testIntToUBytes() {

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u), 0.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u), 0.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0u), 0.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0u), 0.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0u), 0.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xff.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu), 0xff.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0xffu), 0xff.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0xffu), 0xff.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0xffu), 0xff.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0x0u), 0xff.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u), 0xff.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u), 0xff.toUBytes(2, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xff.toUBytes(1, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xff.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffff.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0xffu, 0xffu), 0xffff.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffff.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffff.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffff.toUBytes(0))


        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 1u), 0x0201.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 0x1u), 0x0201.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0x02u, 0x01u), 0x0201.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0u), 0x0201.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0x0u), 0x0201.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u), 0x0201.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201.toUBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xff.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0x7Fu), 127.toUBytes())
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u), 0xff000000.toInt().toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffff.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xff.toUBytes())
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu), 0xffffffff.toInt().toUBytes())
    }

    @Test
    fun testIntToBytes() {

        assertContentEquals(byteArrayOf(0, 0, 0, 0), 0.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0), 0.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0), 0.toBytes(2))
        assertContentEquals(byteArrayOf(0), 0.toBytes(1))
        assertContentEquals(byteArrayOf(0), 0.toBytes(0))


        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xff.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b), 0xff.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0xff.b), 0xff.toBytes(2))
        assertContentEquals(byteArrayOf(0xff.b), 0xff.toBytes(1))
        assertContentEquals(byteArrayOf(0xff.b), 0xff.toBytes(0))


        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0x0), 0xff.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0), 0xff.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0), 0xff.toBytes(2, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xff.toBytes(1, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xff.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffff.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0xff.b, 0xff.b), 0xffff.toBytes(3))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffff.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffff.toBytes(1) }
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffff.toBytes(0))


        assertContentEquals(byteArrayOf(0, 0, 2, 1), 0x0201.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 2, 0x1), 0x0201.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0x02, 0x01), 0x0201.toBytes(3))
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201.toBytes(1) }
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201.toBytes(0))

        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0), 0x0201.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0x0), 0x0201.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0), 0x0201.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201.toBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201.toBytes(1) }
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xff.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0x7F), 127.toBytes())
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0), 0xff000000.toInt().toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffff.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xff.toBytes())
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffff.toInt().toBytes())
    }

    @Test
    fun testIntToIntBytes() {

        assertContentEquals(listOf(0, 0, 0, 0), 0.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 0), 0.toIntBytes(3))
        assertContentEquals(listOf(0, 0), 0.toIntBytes(2))
        assertContentEquals(listOf(0), 0.toIntBytes(1))
        assertContentEquals(listOf(0), 0.toIntBytes(0))


        assertContentEquals(listOf(0, 0, 0, 0xff), 0xff.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 0xff), 0xff.toIntBytes(3))
        assertContentEquals(listOf(0, 0xff), 0xff.toIntBytes(2))
        assertContentEquals(listOf(0xff), 0xff.toIntBytes(1))
        assertContentEquals(listOf(0xff), 0xff.toIntBytes(0))


        assertContentEquals(listOf(0xff, 0, 0, 0x0), 0xff.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff, 0, 0), 0xff.toIntBytes(3, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff, 0), 0xff.toIntBytes(2, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff), 0xff.toIntBytes(1, LITTLE_ENDIAN))
        assertContentEquals(listOf(0xff), 0xff.toIntBytes(0, LITTLE_ENDIAN))

        assertContentEquals(listOf(0, 0, 0xff, 0xff), 0xffff.toIntBytes(4))
        assertContentEquals(listOf(0, 0xff, 0xff), 0xffff.toIntBytes(3))
        assertContentEquals(listOf(0xff, 0xff), 0xffff.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffff.toIntBytes(1) }
        assertContentEquals(listOf(0xff, 0xff), 0xffff.toIntBytes(0))


        assertContentEquals(listOf(0, 0, 2, 1), 0x0201.toIntBytes(4))
        assertContentEquals(listOf(0, 0, 2, 0x1), 0x0201.toIntBytes(4))
        assertContentEquals(listOf(0, 0x02, 0x01), 0x0201.toIntBytes(3))
        assertContentEquals(listOf(0x02, 0x01), 0x0201.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201.toIntBytes(1) }
        assertContentEquals(listOf(0x02, 0x01), 0x0201.toIntBytes(0))

        assertContentEquals(listOf(0x01, 0x02, 0, 0), 0x0201.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02, 0, 0x0), 0x0201.toIntBytes(4, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02, 0), 0x0201.toIntBytes(3, LITTLE_ENDIAN))
        assertContentEquals(listOf(0x01, 0x02), 0x0201.toIntBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201.toIntBytes(1) }
        assertContentEquals(listOf(0x01, 0x02), 0x0201.toIntBytes(0, LITTLE_ENDIAN))

        assertContentEquals(listOf(0, 0, 0, 0xff), 0xff.toIntBytes())
        assertContentEquals(listOf(0, 0, 0, 0x7F), 127.toIntBytes())
        assertContentEquals(listOf(0xff, 0, 0, 0), 0xff000000.toInt().toIntBytes())
        assertContentEquals(listOf(0, 0, 0xff, 0xff), 0xffff.toIntBytes())
        assertContentEquals(listOf(0, 0, 0, 0xff), 0xff.toIntBytes())
        assertContentEquals(listOf(0xff, 0xff, 0xff, 0xff), 0xffffffff.toInt().toIntBytes())
    }

    @Test
    fun testLongToIntBytes() {

        assertEquals(listOf(0, 0, 0, 0), 0L.toIntBytes(4))
        assertEquals(listOf(0, 0, 0), 0L.toIntBytes(3))
        assertEquals(listOf(0, 0), 0L.toIntBytes(2))
        assertEquals(listOf(0), 0L.toIntBytes(1))
        assertEquals(listOf(0), 0L.toIntBytes(0))


        assertEquals(listOf(0, 0, 0, 0xff), 0xffL.toIntBytes(4))
        assertEquals(listOf(0, 0, 0xff), 0xffL.toIntBytes(3))
        assertEquals(listOf(0, 0xff), 0xffL.toIntBytes(2))
        assertEquals(listOf(0xff), 0xffL.toIntBytes(1))
        assertEquals(listOf(0xff), 0xffL.toIntBytes(0))


        assertEquals(listOf(0xff, 0, 0, 0x0), 0xffL.toIntBytes(4, LITTLE_ENDIAN))
        assertEquals(listOf(0xff, 0, 0), 0xffL.toIntBytes(3, LITTLE_ENDIAN))
        assertEquals(listOf(0xff, 0), 0xffL.toIntBytes(2, LITTLE_ENDIAN))
        assertEquals(listOf(0xff), 0xffL.toIntBytes(1, LITTLE_ENDIAN))
        assertEquals(listOf(0xff), 0xffL.toIntBytes(0, LITTLE_ENDIAN))

        assertEquals(listOf(0, 0, 0xff, 0xff), 0xffffL.toIntBytes(4))
        assertEquals(listOf(0, 0xff, 0xff), 0xffffL.toIntBytes(3))
        assertEquals(listOf(0xff, 0xff), 0xffffL.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffL.toIntBytes(1) }
        assertEquals(listOf(0xff, 0xff), 0xffffL.toIntBytes(0))


        assertEquals(listOf(0, 0, 2, 1), 0x0201L.toIntBytes(4))
        assertEquals(listOf(0, 0, 2, 0x1), 0x0201L.toIntBytes(4))
        assertEquals(listOf(0, 0x02, 0x01), 0x0201L.toIntBytes(3))
        assertEquals(listOf(0x02, 0x01), 0x0201L.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toIntBytes(1) }
        assertEquals(listOf(0x02, 0x01), 0x0201L.toIntBytes(0))

        assertFailsWith<IllegalArgumentException> { 0x0201L.toIntBytes(9, LITTLE_ENDIAN) }
        assertEquals(listOf(0x01, 0x02, 0, 0, 0, 0, 0, 0), 0x0201L.toIntBytes(8, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02, 0, 0x0), 0x0201L.toIntBytes(4, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02, 0), 0x0201L.toIntBytes(3, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02), 0x0201L.toIntBytes(2, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02), 0x0201L.toIntBytes(0, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toIntBytes(1) }
        assertEquals(listOf(0x01, 0x02), 0x0201L.toIntBytes(0, LITTLE_ENDIAN))

        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0xff), 0xffL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0x7F), 127L.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0xff, 0, 0, 0), 0xff000000.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0xff, 0xff), 0xffffL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0xff), 0xffL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0xff, 0xff, 0xff, 0xff), 0xffffffff.toIntBytes())
        assertEquals(listOf(0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff), (-1L).toIntBytes())
    }

    @Test
    fun testULongToIntBytes() {

        assertEquals(listOf(0, 0, 0, 0), 0uL.toIntBytes(4))
        assertEquals(listOf(0, 0, 0), 0uL.toIntBytes(3))
        assertEquals(listOf(0, 0), 0uL.toIntBytes(2))
        assertEquals(listOf(0), 0uL.toIntBytes(1))
        assertEquals(listOf(0), 0uL.toIntBytes(0))


        assertEquals(listOf(0, 0, 0, 0xff), 0xffuL.toIntBytes(4))
        assertEquals(listOf(0, 0, 0xff), 0xffuL.toIntBytes(3))
        assertEquals(listOf(0, 0xff), 0xffuL.toIntBytes(2))
        assertEquals(listOf(0xff), 0xffuL.toIntBytes(1))
        assertEquals(listOf(0xff), 0xffuL.toIntBytes(0))


        assertEquals(listOf(0xff, 0, 0, 0x0), 0xffuL.toIntBytes(4, LITTLE_ENDIAN))
        assertEquals(listOf(0xff, 0, 0), 0xffuL.toIntBytes(3, LITTLE_ENDIAN))
        assertEquals(listOf(0xff, 0), 0xffuL.toIntBytes(2, LITTLE_ENDIAN))
        assertEquals(listOf(0xff), 0xffuL.toIntBytes(1, LITTLE_ENDIAN))
        assertEquals(listOf(0xff), 0xffuL.toIntBytes(0, LITTLE_ENDIAN))

        assertEquals(listOf(0, 0, 0xff, 0xff), 0xffffuL.toIntBytes(4))
        assertEquals(listOf(0, 0xff, 0xff), 0xffffuL.toIntBytes(3))
        assertEquals(listOf(0xff, 0xff), 0xffffuL.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffuL.toIntBytes(1) }
        assertEquals(listOf(0xff, 0xff), 0xffffuL.toIntBytes(0))


        assertEquals(listOf(0, 0, 2, 1), 0x0201uL.toIntBytes(4))
        assertEquals(listOf(0, 0, 2, 0x1), 0x0201uL.toIntBytes(4))
        assertEquals(listOf(0, 0x02, 0x01), 0x0201uL.toIntBytes(3))
        assertEquals(listOf(0x02, 0x01), 0x0201uL.toIntBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toIntBytes(1) }
        assertEquals(listOf(0x02, 0x01), 0x0201uL.toIntBytes(0))

        assertFailsWith<IllegalArgumentException> { 0x0201uL.toIntBytes(9, LITTLE_ENDIAN) }
        assertEquals(listOf(0x01, 0x02, 0, 0, 0, 0, 0, 0), 0x0201uL.toIntBytes(8, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02, 0, 0x0), 0x0201uL.toIntBytes(4, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02, 0), 0x0201uL.toIntBytes(3, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02), 0x0201uL.toIntBytes(2, LITTLE_ENDIAN))
        assertEquals(listOf(0x01, 0x02), 0x0201uL.toIntBytes(0, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toIntBytes(1) }
        assertEquals(listOf(0x01, 0x02), 0x0201uL.toIntBytes(0, LITTLE_ENDIAN))

        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0xff), 0xffuL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0x7F), 127uL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0xff, 0, 0, 0), 0xff000000uL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0xff, 0xff), 0xffffuL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0xff), 0xffuL.toIntBytes())
        assertEquals(listOf(0, 0, 0, 0, 0xff, 0xff, 0xff, 0xff), 0xffffffffuL.toIntBytes())
        assertEquals(listOf(0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff), 0xFFFFFFFFFFFFFFFFuL.toIntBytes())
    }


    @Test
    fun testULongToUBytes() {

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0uL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u), 0uL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u), 0uL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0u), 0uL.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0u), 0uL.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0u), 0uL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffuL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xffuL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu), 0xffuL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0xffu), 0xffuL.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffuL.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffuL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0xffuL.toUBytes(8, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0x0u), 0xffuL.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u), 0xffuL.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u), 0xffuL.toUBytes(2, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffuL.toUBytes(1, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffuL.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0xffu, 0xffu), 0xffffuL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffffuL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0xffu, 0xffu), 0xffffuL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffuL.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffuL.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffuL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 2u, 1u), 0x0201uL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 0x1u), 0x0201uL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0x02u, 0x01u), 0x0201uL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201uL.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201uL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u), 0x0201uL.toUBytes(8, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0x0u), 0x0201uL.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u), 0x0201uL.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201uL.toUBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201uL.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffuL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0x7Fu), 127uL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0xffu, 0u, 0u, 0u), 0xff000000uL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0xffu, 0xffu), 0xffffuL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffuL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffuL.toUBytes())

        assertContentEquals(ubyteArrayOf(0xffu), 0xffuL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0x7Fu), 127uL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u), 0xff000000uL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffuL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffuL.toUBytes(0))
        assertContentEquals(
            ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffffffffffuL.toUBytes(0)
        )

        assertFailsWith<IllegalArgumentException> { 0xff000000uL.toUBytes(3) }

    }

    @Test
    fun testULongToBytes() {

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0), 0uL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0, 0), 0uL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0), 0uL.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0), 0uL.toBytes(2))
        assertContentEquals(byteArrayOf(0), 0uL.toBytes(1))
        assertContentEquals(byteArrayOf(0), 0uL.toBytes(0))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffuL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xffuL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b), 0xffuL.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0xff.b), 0xffuL.toBytes(2))
        assertContentEquals(byteArrayOf(0xff.b), 0xffuL.toBytes(1))
        assertContentEquals(byteArrayOf(0xff.b), 0xffuL.toBytes(0))

        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0, 0, 0, 0, 0), 0xffuL.toBytes(8, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0x0), 0xffuL.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0), 0xffuL.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0), 0xffuL.toBytes(2, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffuL.toBytes(1, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffuL.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0xff.b, 0xff.b), 0xffffuL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffffuL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0xff.b, 0xff.b), 0xffffuL.toBytes(3))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffuL.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffuL.toBytes(1) }
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffuL.toBytes(0))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 2, 1), 0x0201uL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 2, 0x1), 0x0201uL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0x02, 0x01), 0x0201uL.toBytes(3))
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201uL.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toBytes(1) }
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201uL.toBytes(0))

        assertContentEquals(byteArrayOf(1, 2, 0, 0, 0, 0, 0, 0), 0x0201uL.toBytes(8, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0x0), 0x0201uL.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0), 0x0201uL.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201uL.toBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201uL.toBytes(1) }
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201uL.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffuL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0x7F), 127uL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0xff.b, 0, 0, 0), 0xff000000uL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0xff.b, 0xff.b), 0xffffuL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffuL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffuL.toBytes())

        assertContentEquals(byteArrayOf(0xff.b), 0xffuL.toBytes(0))
        assertContentEquals(byteArrayOf(0x7F), 127uL.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0), 0xff000000uL.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffuL.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffuL.toBytes(0))
        assertContentEquals(
            byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffffffffffuL.toBytes(0)
        )

        assertFailsWith<IllegalArgumentException> { 0xff000000uL.toBytes(3) }

    }

    @Test
    fun testLongToUBytes() {

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0L.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u), 0L.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u), 0L.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0u), 0L.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0u), 0L.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0u), 0L.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0xffu), 0xffL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu), 0xffL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0u, 0xffu), 0xffL.toUBytes(2))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffL.toUBytes(1))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0xffL.toUBytes(8, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0x0u), 0xffL.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u), 0xffL.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu, 0u), 0xffL.toUBytes(2, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffL.toUBytes(1, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0xffu), 0xffL.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0xffu, 0xffu), 0xffffL.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 0xffu, 0xffu), 0xffffL.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0xffu, 0xffu), 0xffffL.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffL.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffL.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffL.toUBytes(0))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 2u, 1u), 0x0201L.toUBytes(8))
        assertContentEquals(ubyteArrayOf(0u, 0u, 2u, 0x1u), 0x0201L.toUBytes(4))
        assertContentEquals(ubyteArrayOf(0u, 0x02u, 0x01u), 0x0201L.toUBytes(3))
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201L.toUBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x02u, 0x01u), 0x0201L.toUBytes(0))

        assertContentEquals(ubyteArrayOf(1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u), 0x0201L.toUBytes(8, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u, 0x0u), 0x0201L.toUBytes(4, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u, 0u), 0x0201L.toUBytes(3, LITTLE_ENDIAN))
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201L.toUBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toUBytes(1) }
        assertContentEquals(ubyteArrayOf(0x01u, 0x02u), 0x0201L.toUBytes(0, LITTLE_ENDIAN))

        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0x7Fu), 127L.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0xffu, 0u, 0u, 0u), 0xff000000L.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0xffu, 0xffu), 0xffffL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 0xffL.toUBytes())
        assertContentEquals(ubyteArrayOf(0u, 0u, 0u, 0u, 0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffL.toUBytes())

        assertContentEquals(ubyteArrayOf(0xffu), 0xffL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0x7Fu), 127L.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0u, 0u, 0u), 0xff000000L.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu), 0xffffL.toUBytes(0))
        assertContentEquals(ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu), 0xffffffffL.toUBytes(0))
        assertContentEquals(
            ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu), (-1L).toUBytes(0)
        )

        assertFailsWith<IllegalArgumentException> { 0xff000000L.toUBytes(3) }

    }

    @Test
    fun testLongToBytes() {

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0), 0L.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0, 0), 0L.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0), 0L.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0), 0L.toBytes(2))
        assertContentEquals(byteArrayOf(0), 0L.toBytes(1))
        assertContentEquals(byteArrayOf(0), 0L.toBytes(0))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0, 0xff.b), 0xffL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b), 0xffL.toBytes(3))
        assertContentEquals(byteArrayOf(0, 0xff.b), 0xffL.toBytes(2))
        assertContentEquals(byteArrayOf(0xff.b), 0xffL.toBytes(1))
        assertContentEquals(byteArrayOf(0xff.b), 0xffL.toBytes(0))

        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0, 0, 0, 0, 0), 0xffL.toBytes(8, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0x0), 0xffL.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0), 0xffL.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b, 0), 0xffL.toBytes(2, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffL.toBytes(1, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0xff.b), 0xffL.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0xff.b, 0xff.b), 0xffffL.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 0xff.b, 0xff.b), 0xffffL.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0xff.b, 0xff.b), 0xffffL.toBytes(3))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffL.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0xffffL.toBytes(1) }
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffL.toBytes(0))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 2, 1), 0x0201L.toBytes(8))
        assertContentEquals(byteArrayOf(0, 0, 2, 0x1), 0x0201L.toBytes(4))
        assertContentEquals(byteArrayOf(0, 0x02, 0x01), 0x0201L.toBytes(3))
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201L.toBytes(2))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toBytes(1) }
        assertContentEquals(byteArrayOf(0x02, 0x01), 0x0201L.toBytes(0))

        assertContentEquals(byteArrayOf(1, 2, 0, 0, 0, 0, 0, 0), 0x0201L.toBytes(8, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0, 0x0), 0x0201L.toBytes(4, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02, 0), 0x0201L.toBytes(3, LITTLE_ENDIAN))
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201L.toBytes(2, LITTLE_ENDIAN))
        assertFailsWith<IllegalArgumentException> { 0x0201L.toBytes(1) }
        assertContentEquals(byteArrayOf(0x01, 0x02), 0x0201L.toBytes(0, LITTLE_ENDIAN))

        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0x7F), 127L.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0xff.b, 0, 0, 0), 0xff000000L.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0xff.b, 0xff.b), 0xffffL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0xff.b), 0xffL.toBytes())
        assertContentEquals(byteArrayOf(0, 0, 0, 0, 0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffL.toBytes())

        assertContentEquals(byteArrayOf(0xff.b), 0xffL.toBytes(0))
        assertContentEquals(byteArrayOf(0x7F), 127L.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0, 0, 0), 0xff000000L.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b), 0xffffL.toBytes(0))
        assertContentEquals(byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b), 0xffffffffL.toBytes(0))
        assertContentEquals(
            byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b), (-1L).toBytes(0)
        )

        assertFailsWith<IllegalArgumentException> { 0xff000000L.toBytes(3) }

    }

    @Test
    fun testBytesToUInt() {
        assertEquals(0u, byteArrayOf(0, 0, 0, 0).toUInt())
        assertEquals(0u, byteArrayOf(0).toUInt(0, 1))
        assertEquals(0xFFFFFFFFu, byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b).toUInt())
        assertEquals(
            16909060u, byteArrayOf(0x01, 0x02, 0x03, 0x04).toUInt()
        )
        assertEquals(
            16909060u, byteArrayOf(0x04, 0x03, 0x02, 0x01).toUInt(byteOrder = LITTLE_ENDIAN)
        )
        assertEquals(0xFFFFFFFFu, ubyteArrayOf(0u, 0xffu, 0xffu, 0xffu, 0xffu).toUInt(1))
        assertEquals(255u, ubyteArrayOf(0u, 0u, 0u, 0xffu, 0u, 0u, 0u, 0u).toUInt(3, 1))
    }

    @Test
    fun testBytesToLong() {
        assertEquals(0, byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0).toLong())
        assertEquals(0, byteArrayOf(0).toLong(0, 1))
        assertEquals(-1, byteArrayOf(0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b).toLong())
        assertEquals(
            72623859790382856, byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08).toLong()
        )
        assertEquals(
            72623859790382856,
            byteArrayOf(0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01).toLong(byteOrder = LITTLE_ENDIAN)
        )
        assertEquals(-1, byteArrayOf(0, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b, 0xff.b).toLong(1))
        assertEquals(255, byteArrayOf(0, 0, 0, 0xff.b, 0, 0, 0, 0).toLong(3, 1))
    }

    @Test
    fun testUBytesToLong() {
        assertEquals(0, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u).toLong())
        assertEquals(0, ubyteArrayOf(0u).toLong(0, 1))
        assertEquals(-1, ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu).toLong())
        assertEquals(
            72623859790382856, ubyteArrayOf(0x01u, 0x02u, 0x03u, 0x04u, 0x05u, 0x06u, 0x07u, 0x08u).toLong()
        )
        assertEquals(
            72623859790382856,
            ubyteArrayOf(0x08u, 0x07u, 0x06u, 0x05u, 0x04u, 0x03u, 0x02u, 0x01u).toLong(byteOrder = LITTLE_ENDIAN)
        )
        assertEquals(-1, ubyteArrayOf(0u, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu).toLong(1))
        assertEquals(255, ubyteArrayOf(0u, 0u, 0u, 0xffu, 0u, 0u, 0u, 0u).toLong(3, 1))
    }

    @Test
    fun testUBytesToULong() {
        assertEquals(0u, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u).toULong())
        assertEquals(0u, ubyteArrayOf(0u).toULong(0, 1))
        assertEquals(
            0xFFFFFFFFFFFFFFFFu, ubyteArrayOf(0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu).toULong()
        )
        assertEquals(
            72623859790382856u, ubyteArrayOf(0x01u, 0x02u, 0x03u, 0x04u, 0x05u, 0x06u, 0x07u, 0x08u).toULong()
        )
        assertEquals(
            72623859790382856u,
            ubyteArrayOf(0x08u, 0x07u, 0x06u, 0x05u, 0x04u, 0x03u, 0x02u, 0x01u).toULong(byteOrder = LITTLE_ENDIAN)
        )
        assertEquals(
            0xFFFFFFFFFFFFFFFFu, ubyteArrayOf(0u, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu, 0xffu).toULong(1)
        )
        assertEquals(255u, ubyteArrayOf(0u, 0u, 0u, 0xffu, 0u, 0u, 0u, 0u).toULong(3, 1))
    }

    @Test
    fun testUBytesToUInt() {

        assertEquals(0x01020304u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt().getOrNull())
        assertEquals(0x020304u, ubyteArrayOf(0u, 2u, 3u, 4u).readUInt().getOrNull())
        assertEquals(0x0304u, ubyteArrayOf(0u, 0u, 3u, 4u).readUInt().getOrNull())
        assertEquals(0x0u, ubyteArrayOf(0u, 0u, 0u, 0u).readUInt().getOrNull())

        assertEquals(0x01020304u, ubyteArrayOf(0u, 0u, 1u, 2u, 3u, 4u).readUInt(2).getOrNull())
        assertEquals(0x020304u, ubyteArrayOf(0u, 0u, 0u, 2u, 3u, 4u).readUInt(2).getOrNull())
        assertEquals(0x0304u, ubyteArrayOf(0u, 0u, 0u, 0u, 3u, 4u).readUInt(2).getOrNull())
        assertEquals(0x0u, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u).readUInt(2).getOrNull())

        assertEquals(0x010203u, ubyteArrayOf(0u, 0u, 1u, 2u, 3u, 4u).readUInt(2, 3).getOrNull())
        assertEquals(0x0203u, ubyteArrayOf(0u, 0u, 0u, 2u, 3u, 4u).readUInt(2, 3).getOrNull())
        assertEquals(0x03u, ubyteArrayOf(0u, 0u, 0u, 0u, 3u, 4u).readUInt(2, 3).getOrNull())
        assertEquals(0x0u, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u).readUInt(2, 3).getOrNull())

        assertEquals(0x04030201u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(byteOrder = LITTLE_ENDIAN).getOrNull())
        assertEquals(0x04030200u, ubyteArrayOf(0u, 2u, 3u, 4u).readUInt(byteOrder = LITTLE_ENDIAN).getOrNull())
        assertEquals(0x04030000u, ubyteArrayOf(0u, 0u, 3u, 4u).readUInt(byteOrder = LITTLE_ENDIAN).getOrNull())
        assertEquals(0x04000000u, ubyteArrayOf(0u, 0u, 0u, 4u).readUInt(byteOrder = LITTLE_ENDIAN).getOrNull())
        assertEquals(0x00000000u, ubyteArrayOf(0u, 0u, 0u, 0u).readUInt(byteOrder = LITTLE_ENDIAN).getOrNull())

        assertEquals(0x01020304u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(length = 4).getOrNull())
        assertEquals(0x010203u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(length = 3).getOrNull())
        assertEquals(0x0102u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(length = 2).getOrNull())
        assertEquals(0x01u, ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(length = 1).getOrNull())
        assertFailsWith<IllegalArgumentException> {
            ubyteArrayOf(1u, 2u, 3u, 4u).readUInt(length = 0).getOrThrow()
        }

        assertEquals(
            0x01020304u, ubyteArrayOf(4u, 3u, 2u, 1u).readUInt(length = 4, byteOrder = LITTLE_ENDIAN).getOrNull()
        )
        assertEquals(
            0x020304u, ubyteArrayOf(4u, 3u, 2u, 1u).readUInt(length = 3, byteOrder = LITTLE_ENDIAN).getOrNull()
        )
        assertEquals(0x0304u, ubyteArrayOf(4u, 3u, 2u, 1u).readUInt(length = 2, byteOrder = LITTLE_ENDIAN).getOrNull())
        assertEquals(0x04u, ubyteArrayOf(4u, 3u, 2u, 1u).readUInt(length = 1, byteOrder = LITTLE_ENDIAN).getOrNull())
        assertFailsWith<IllegalArgumentException> {
            ubyteArrayOf(4u, 3u, 2u, 1u).readUInt(length = 0, byteOrder = LITTLE_ENDIAN).getOrThrow()
        }


    }

    @Test
    fun testUIntCopyInto() {
        data class TestCase(
            val unsignedInt: UInt,
            val expected: UByteArray,
            val offset: Int,
            val newIndex: Int,
            val encodingLength: Int,
            val byteOrder: ByteOrder
        )

        val testCases = listOf(
            TestCase(
                0xffu, ubyteArrayOf(0u, 0u, 0u, 0xffu, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffu, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 4, 8, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffu, ubyteArrayOf(0xffu, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0xffu + 1u, ubyteArrayOf(0u, 0u, 0x01u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffu + 1u, ubyteArrayOf(0u, 0x01u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x0102u, ubyteArrayOf(0u, 0u, 0x01u, 0x02u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x0102u, ubyteArrayOf(0x02u, 0x01u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x010203u, ubyteArrayOf(0u, 0x01u, 0x02u, 0x03u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x010203u, ubyteArrayOf(0x03u, 0x02u, 0x01u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x01020304u, ubyteArrayOf(0x01u, 0x02u, 0x03u, 0x04u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x01020304u, ubyteArrayOf(0x04u, 0x03u, 0x02u, 0x01u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x01020304u, ubyteArrayOf(0u, 0u, 0u, 0u, 0x01u, 0x02u, 0x03u, 0x04u), 4, 8, 4, BIG_ENDIAN
            ),
            TestCase(
                0x01020304u, ubyteArrayOf(0u, 0u, 0u, 0u, 0x04u, 0x03u, 0x02u, 0x01u), 4, 8, 4, LITTLE_ENDIAN
            ),

            TestCase(
                0x0102u, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 1u, 2u), 5, 8, 3, BIG_ENDIAN
            ),
        )

        for (c in testCases) {
            println(c)
            val buf = UByteArray(c.expected.size) { 0u }
            assertEquals(c.newIndex, c.unsignedInt.copyInto(buf, c.offset, c.encodingLength, c.byteOrder))
            assertContentEquals(c.expected, buf)

            assertEquals(c.unsignedInt, c.expected.toUInt(c.offset, c.encodingLength, c.byteOrder))
        }

        assertFailsWith<IllegalArgumentException> {
            0x01020304u.copyInto(UByteArray((8)), 5, 4, BIG_ENDIAN)
        }
    }

    @Test
    fun testULongCopyInto() {
        data class TestCase(
            val unsignedLong: ULong,
            val expected: UByteArray,
            val offset: Int,
            val newIndex: Int,
            val encodingLength: Int,
            val byteOrder: ByteOrder,
        )

        val testCases = listOf(
            TestCase(
                0xffu, ubyteArrayOf(0u, 0u, 0u, 0xffu, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffu, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xffu), 4, 8, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffu, ubyteArrayOf(0xffu, 0u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0xffuL + 1u, ubyteArrayOf(0u, 0u, 0x01u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0xffuL + 1u, ubyteArrayOf(0u, 0x01u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x0102uL, ubyteArrayOf(0u, 0u, 0x01u, 0x02u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x0102uL, ubyteArrayOf(0x02u, 0x01u, 0u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x010203uL, ubyteArrayOf(0u, 0x01u, 0x02u, 0x03u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x010203uL, ubyteArrayOf(0x03u, 0x02u, 0x01u, 0u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x01020304uL, ubyteArrayOf(0x01u, 0x02u, 0x03u, 0x04u, 0u, 0u, 0u, 0u), 0, 4, 4, BIG_ENDIAN
            ),
            TestCase(
                0x01020304uL, ubyteArrayOf(0x04u, 0x03u, 0x02u, 0x01u, 0u, 0u, 0u, 0u), 0, 4, 4, LITTLE_ENDIAN
            ),
            TestCase(
                0x01020304uL, ubyteArrayOf(0u, 0u, 0u, 0u, 0x01u, 0x02u, 0x03u, 0x04u), 4, 8, 4, BIG_ENDIAN
            ),
            TestCase(
                0x01020304uL, ubyteArrayOf(0u, 0u, 0u, 0u, 0x04u, 0x03u, 0x02u, 0x01u), 4, 8, 4, LITTLE_ENDIAN
            ),

            TestCase(
                0x0102uL, ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 1u, 2u), 5, 8, 3, BIG_ENDIAN
            ),
        )

        testCases.forEachIndexed { index, c ->
            println("$index: $c")
            val buf = UByteArray(c.expected.size) { 0u }
            assertEquals(c.newIndex, c.unsignedLong.copyInto(buf, c.offset, c.encodingLength, c.byteOrder))
            assertContentEquals(c.expected, buf)

            assertEquals(c.unsignedLong, c.expected.toULong(c.offset, c.encodingLength, c.byteOrder))
        }

        assertFailsWith<IllegalArgumentException> {
            0x01020304u.copyInto(UByteArray((8)), 5, 4, BIG_ENDIAN)
        }
    }

    @Test
    fun testToUInt() {


    }
}