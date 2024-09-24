package com.ingonoka.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class BitTwiddleTest {

    @Test
    fun testReverse() {

        assertEquals(0xF6.toByte(), 0x6F.toByte().reverse())
        assertEquals(0x00.toByte(), 0x00.toByte().reverse())
        assertEquals(0x80.toByte(), 0x01.toByte().reverse())
        assertEquals(0x01.toByte(), 0x80.toByte().reverse())
        assertEquals(0x10.toByte(), 0x08.toByte().reverse())
    }


    @Test
    fun testBitShift() {

        var input = byteArrayOf()
        assertArrayEquals(byteArrayOf().toTypedArray(), input.shiftLeft(0).toTypedArray())
        assertArrayEquals(byteArrayOf().toTypedArray(), input.shiftLeft(4).toTypedArray())
        assertArrayEquals(byteArrayOf().toTypedArray(), input.shiftRight(0).toTypedArray())
        assertArrayEquals(byteArrayOf().toTypedArray(), input.shiftRight(4).toTypedArray())

        input = byteArrayOf(0b1000_0001.toByte())
        assertArrayEquals(byteArrayOf(0b1000_0001.toByte()).toTypedArray(), input.shiftLeft(0).toTypedArray())
        input = byteArrayOf(0b1000_0001.toByte())
        assertArrayEquals(byteArrayOf(0b0001_0000.toByte()).toTypedArray(), input.shiftLeft(4).toTypedArray())
        assertArrayEquals(byteArrayOf(0b0000_0000.toByte()).toTypedArray(), input.shiftLeft(4).toTypedArray())

        input = byteArrayOf(0b1000_0001.toByte())
        assertArrayEquals(byteArrayOf(0b1000_0001.toByte()).toTypedArray(), input.shiftRight(0).toTypedArray())
        input = byteArrayOf(0b1000_0001.toByte())
        assertArrayEquals(byteArrayOf(0b0000_1000.toByte()).toTypedArray(), input.shiftRight(4).toTypedArray())
        assertArrayEquals(byteArrayOf(0b0000_0000.toByte()).toTypedArray(), input.shiftRight(4).toTypedArray())

        input = byteArrayOf(0b0000_1111.toByte())
        assertArrayEquals(byteArrayOf(0b1111_0000.toByte()).toTypedArray(), input.shiftLeft(4).toTypedArray())

        input = byteArrayOf(0b1111_0000.toByte())
        assertArrayEquals(byteArrayOf(0b0000_1111.toByte()).toTypedArray(), input.shiftRight(4).toTypedArray())

        input = byteArrayOf(0, 0b1111_0000.toByte())
        assertArrayEquals(byteArrayOf(0b0000_1111, 0).toTypedArray(), input.shiftLeft(4).toTypedArray())
        input = byteArrayOf(0, 0b1111_0000.toByte())
        assertArrayEquals(
            byteArrayOf(0b0000_0011, 0b1100_0000.toByte()).toTypedArray(),
            input.shiftLeft(2).toTypedArray()
        )

        input = byteArrayOf(0b0000_1111, 0)
        assertArrayEquals(byteArrayOf(0, 0b1111_0000.toByte()).toTypedArray(), input.shiftRight(4).toTypedArray())
        input = byteArrayOf(0b0000_1111, 0)
        assertArrayEquals(
            byteArrayOf(0b0000_0011, 0b1100_0000.toByte()).toTypedArray(),
            input.shiftRight(2).toTypedArray()
        )
    }

    @Test
    fun testBitShiftList() {

        var input = mutableListOf<Int>()
        assertEquals(listOf(), input.shiftLeft(0))
        assertEquals(listOf(), input.shiftLeft(4))
        assertEquals(listOf(), input.shiftRight(0))
        assertEquals(listOf(), input.shiftRight(4))

        input = mutableListOf(0b1000_0001)
        assertEquals(mutableListOf(0b1000_0001), input.shiftLeft(0))
        input = mutableListOf(0b1000_0001)
        assertEquals(mutableListOf(0b0001_0000), input.shiftLeft(4))
        assertEquals(mutableListOf(0b0000_0000), input.shiftLeft(4))

        input = mutableListOf(0b1000_0001)
        assertEquals(mutableListOf(0b1000_0001), input.shiftRight(0))
        input = mutableListOf(0b1000_0001)
        assertEquals(mutableListOf(0b0000_1000), input.shiftRight(4))
        assertEquals(mutableListOf(0b0000_0000), input.shiftRight(4))

        input = mutableListOf(0b0000_1111)
        assertEquals(mutableListOf(0b1111_0000), input.shiftLeft(4))

        input = mutableListOf(0b1111_0000)
        assertEquals(mutableListOf(0b0000_1111), input.shiftRight(4))

        input = mutableListOf(0, 0b1111_0000)
        assertEquals(mutableListOf(0b0000_1111, 0), input.shiftLeft(4))
        input = mutableListOf(0, 0b1111_0000)
        assertEquals(mutableListOf(0b0000_0011, 0b1100_0000), input.shiftLeft(2))

        input = mutableListOf(0b0000_1111, 0)
        assertEquals(mutableListOf(0, 0b1111_0000), input.shiftRight(4))
        input = mutableListOf(0b0000_1111, 0)
        assertEquals(mutableListOf(0b0000_0011, 0b1100_0000), input.shiftRight(2))
    }

    @Test
    fun testToList() {

        var input = byteArrayOf()
        assertEquals(listOf(), input.toListOfInt())

        input = byteArrayOf(1, -1, 0, -128, 127)
        assertEquals(listOf(1, -1, 0, -128, 127), input.toListOfInt())
    }

    @Test
    fun testToByteArray() {

        var input = listOf<Int>()
        assertArrayEquals(byteArrayOf().toTypedArray(), input.toByteArray().toTypedArray())

        input = listOf(1, -1, 0, -128, 127)
        assertArrayEquals(byteArrayOf(1, -1, 0, -128, 127).toTypedArray(), input.toByteArray().toTypedArray())
    }

    @Test
    fun testMapInPlace() {

        var input = byteArrayOf()
        assertArrayEquals(byteArrayOf().toTypedArray(), input.mapInPlace { it }.toTypedArray())

        input = byteArrayOf(1)
        input.mapInPlace { 2 }
        assertArrayEquals(byteArrayOf(2).toTypedArray(), input.toTypedArray())

    }
}