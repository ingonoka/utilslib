/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

@file:Suppress("SpellCheckingInspection")

package com.ingonoka.utils

import com.ingonoka.hexutils.hexToBytes
import com.ingonoka.hexutils.hexToListOfInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


class LongIntToFromBytesKtTest {

    @ExperimentalUnsignedTypes
    @Test
    fun testToLongNoLeadingZeros() {

        assertEquals(
            128, byteArrayOf(0x80.toByte()).toLongNoLeadingZeros()
                .getOrThrow()
        )
        assertEquals(128, byteArrayOf(0x80.toByte(), 1).toLongNoLeadingZeros(1).getOrThrow())
        assertEquals(128, byteArrayOf(1, 0x80.toByte()).toLongNoLeadingZeros(1, 1).getOrThrow())

        assertEquals(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(8).getOrThrow())
        assertEquals(
            Long.MAX_VALUE,
            "887FFFFFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(8, 1).getOrThrow()
        )
        assertEquals(
            Long.MAX_VALUE,
            "887FFFFFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(index = 1).getOrThrow()
        )

        assertEquals(72057594037927935, "FFFFFFFFFFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(
            72057594037927935,
            "FFFFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(7).getOrThrow()
        )
        assertEquals(
            72057594037927935,
            "88FFFFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(7, 1).getOrThrow()
        )

        assertEquals(281474976710655, "FFFFFFFFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(281474976710655, "FFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(6).getOrThrow())
        assertEquals(
            281474976710655,
            "88FFFFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(6, 1).getOrThrow()
        )

        assertEquals(1099511627775, "FFFFFFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(1099511627775, "FFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(5).getOrThrow())
        assertEquals(1099511627775, "88FFFFFFFFFF88".hexToBytes().toLongNoLeadingZeros(5, 1).getOrThrow())

        assertEquals(4294967295, "FFFFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(4294967295, "FFFFFFFF88".hexToBytes().toLongNoLeadingZeros(4).getOrThrow())
        assertEquals(4294967295, "88FFFFFFFF88".hexToBytes().toLongNoLeadingZeros(4, 1).getOrThrow())

        assertEquals(16777215, "FFFFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(16777215, "FFFFFF88".hexToBytes().toLongNoLeadingZeros(3).getOrThrow())
        assertEquals(16777215, "88FFFFFF88".hexToBytes().toLongNoLeadingZeros(3, 1).getOrThrow())

        assertEquals(65535, "FFFF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(65535, "FFFF88".hexToBytes().toLongNoLeadingZeros(2).getOrThrow())
        assertEquals(65535, "88FFFF88".hexToBytes().toLongNoLeadingZeros(2, 1).getOrThrow())

        assertEquals(255, "FF".hexToBytes().toLongNoLeadingZeros().getOrThrow())
        assertEquals(255, "FF88".hexToBytes().toLongNoLeadingZeros(1).getOrThrow())
        assertEquals(255, "88FF88".hexToBytes().toLongNoLeadingZeros(1, 1).getOrThrow())

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testListToLongNoLeadingZeros() {

        assertEquals(
            1000000,
            "40420F00".hexToListOfInt().toIntNoLeadingZeros(4, 0, ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

        assertEquals(
            128L, listOf(0x80).toLongNoLeadingZeros()
                .getOrThrow()
        )
        assertEquals(128L, listOf(0x80, 1).toLongNoLeadingZeros(1).getOrThrow())
        assertEquals(128L, listOf(1, 0x80).toLongNoLeadingZeros(1, 1).getOrThrow())

        assertEquals(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(8).getOrThrow())
        assertEquals(
            Long.MAX_VALUE,
            "887FFFFFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(8, 1).getOrThrow()
        )
        assertEquals(
            Long.MAX_VALUE,
            "887FFFFFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(index = 1).getOrThrow()
        )

        assertEquals(72057594037927935L, "FFFFFFFFFFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(
            72057594037927935L,
            "FFFFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(7).getOrThrow()
        )
        assertEquals(
            72057594037927935L,
            "88FFFFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(7, 1).getOrThrow()
        )

        assertEquals(281474976710655L, "FFFFFFFFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(281474976710655L, "FFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(6).getOrThrow())
        assertEquals(
            281474976710655L,
            "88FFFFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(6, 1).getOrThrow()
        )

        assertEquals(1099511627775L, "FFFFFFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(1099511627775L, "FFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(5).getOrThrow())
        assertEquals(1099511627775L, "88FFFFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(5, 1).getOrThrow())

        assertEquals(4294967295L, "FFFFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(4294967295L, "FFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(4).getOrThrow())
        assertEquals(4294967295L, "88FFFFFFFF88".hexToListOfInt().toLongNoLeadingZeros(4, 1).getOrThrow())

        assertEquals(16777215L, "FFFFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(16777215L, "FFFFFF88".hexToListOfInt().toLongNoLeadingZeros(3).getOrThrow())
        assertEquals(16777215L, "88FFFFFF88".hexToListOfInt().toLongNoLeadingZeros(3, 1).getOrThrow())

        assertEquals(65535L, "FFFF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(65535L, "FFFF88".hexToListOfInt().toLongNoLeadingZeros(2).getOrThrow())
        assertEquals(65535L, "88FFFF88".hexToListOfInt().toLongNoLeadingZeros(2, 1).getOrThrow())

        assertEquals(255L, "FF".hexToListOfInt().toLongNoLeadingZeros().getOrThrow())
        assertEquals(255L, "FF88".hexToListOfInt().toLongNoLeadingZeros(1).getOrThrow())
        assertEquals(255L, "88FF88".hexToListOfInt().toLongNoLeadingZeros(1, 1).getOrThrow())

    }

    @Test
    fun testToInNoLeadingZeros() {

        assertEquals(
            128,
            listOf(-128, 0, 0, 0).toIntNoLeadingZeros(4, 0, ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testReadLongNoLeadingZeros() {

        IntBuffer.wrap(byteArrayOf()).readLongNoLeadingZeros(1).onFailure {
            assertTrue { it.message?.contains("ByteArray Empty") ?: false }
        }
        assertEquals(
            128L,
            IntBuffer.wrap(byteArrayOf(0x80.toByte())).readLongNoLeadingZeros(1).getOrThrow()
        )
        assertEquals(
            128L,
            IntBuffer.wrap(byteArrayOf(0x80.toByte())).readLongNoLeadingZeros(2).getOrThrow()
        )

        assertEquals(
            -9223372036854775807L - 1L,
            IntBuffer.wrap("8000000000000000".hexToBytes()).readLongNoLeadingZeros(8).getOrThrow()
        )
        assertEquals(
            -1L,
            IntBuffer.wrap("FFFFFFFFFFFFFFFF".hexToBytes()).readLongNoLeadingZeros(8).getOrThrow()
        )
        assertEquals(
            Long.MAX_VALUE,
            IntBuffer.wrap("7FFFFFFFFFFFFFFF".hexToBytes()).readLongNoLeadingZeros(8).getOrThrow()
        )
        assertEquals(
            72057594037927935,
            IntBuffer.wrap("FFFFFFFFFFFFFF".hexToBytes()).readLongNoLeadingZeros(7).getOrThrow()
        )
        assertEquals(
            281474976710655,
            IntBuffer.wrap("FFFFFFFFFFFF".hexToBytes()).readLongNoLeadingZeros(6).getOrThrow()
        )
        assertEquals(
            1099511627775,
            IntBuffer.wrap("FFFFFFFFFF".hexToBytes()).readLongNoLeadingZeros(5).getOrThrow()
        )
        assertEquals(
            4294967295,
            IntBuffer.wrap("FFFFFFFF".hexToBytes()).readLongNoLeadingZeros(4).getOrThrow()
        )
        assertEquals(
            16777215,
            IntBuffer.wrap("FFFFFF".hexToBytes()).readLongNoLeadingZeros(3).getOrThrow()
        )
        assertEquals(65535, IntBuffer.wrap("FFFF".hexToBytes()).readLongNoLeadingZeros(2).getOrThrow())
        assertEquals(255, IntBuffer.wrap("FF".hexToBytes()).readLongNoLeadingZeros(1).getOrThrow())


    }

    @Test
    fun testToInt() {
        assertEquals(0, "00000000".hexToBytes().toInt(0).getOrThrow())
        assertEquals(0, "00000000".hexToBytes().toInt().getOrThrow())
        assertEquals(1, "00000001".hexToBytes().toInt(0).getOrThrow())
        assertEquals(-1, "FFFFFFFF".hexToBytes().toInt(0).getOrThrow())
        assertEquals(-1, "00FFFFFFFF".hexToBytes().toInt(1).getOrThrow())
        assertEquals(-1, "FFFFFFFF00".hexToBytes().toInt(0).getOrThrow())
        assertEquals(128, "00000080".hexToBytes().toInt(0).getOrThrow())
        assertEquals(Int.MAX_VALUE, "7FFFFFFF".hexToBytes().toInt(0).getOrThrow())
        assertEquals(Int.MIN_VALUE, "80000000".hexToBytes().toInt(0).getOrThrow())
        assertFails { "000000".hexToBytes().toInt(0).getOrThrow() }
        assertFails { "00000000".hexToBytes().toInt(1).getOrThrow() }

    }

    @Test
    fun testToLong() {
        assertEquals(0, "0000000000000000".hexToBytes().toLong(0).getOrThrow())
        assertEquals(0, "0000000000000000".hexToBytes().toLong().getOrThrow())
        assertEquals(1, "0000000000000001".hexToBytes().toLong(0).getOrThrow())
        assertEquals(-1, "FFFFFFFFFFFFFFFF".hexToBytes().toLong(0).getOrThrow())
        assertEquals(-1, "00FFFFFFFFFFFFFFFF".hexToBytes().toLong(1).getOrThrow())
        assertEquals(-1, "FFFFFFFFFFFFFFFF00".hexToBytes().toLong(0).getOrThrow())
        assertEquals(128, "0000000000000080".hexToBytes().toLong(0).getOrThrow())
        assertEquals(Long.MAX_VALUE, "7FFFFFFFFFFFFFFF".hexToBytes().toLong(0).getOrThrow())
        assertEquals(Long.MIN_VALUE, "8000000000000000".hexToBytes().toLong(0).getOrThrow())
        assertFails { "00000000000000".hexToBytes().toLong(0).getOrThrow() }
        assertFails { "0000000000000000".hexToBytes().toLong(1).getOrThrow() }

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testToIntNoLeadingZeros() {

        assertEquals(128, "0080".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(128, "80".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(128, "8000".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())
        assertEquals(128, "80".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertEquals(-1, "FFFFFFFF".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(-1, "FFFFFFFF".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(-1, "FFFFFFFF".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertEquals(-1, "FFFFFFFF88".hexToBytes().toIntNoLeadingZeros(4).getOrThrow())
        assertEquals(-1, "FFFFFFFF88".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(
            -1,
            "FFFFFFFF88".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

        assertEquals(-1, "88FFFFFFFF".hexToBytes().toIntNoLeadingZeros(4, 1).getOrThrow())
        assertEquals(
            -1,
            "88FFFFFFFF".hexToBytes().toIntNoLeadingZeros(4, 1, endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )
        assertEquals(
            -1,
            "88FFFFFFFF".hexToBytes().toIntNoLeadingZeros(4, 1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

        assertEquals(-2147483648, "80000000".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(
            -2147483648,
            "80000000".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )
        assertEquals(
            -2147483648,
            "00000080".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )
        assertEquals(-2147483648, "8000000088".hexToBytes().toIntNoLeadingZeros(4).getOrThrow())
        assertEquals(
            -2147483648,
            "8000000088".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )
        assertEquals(
            -2147483648,
            "0000008088".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

        assertEquals(128, byteArrayOf(0x80.toByte()).toIntNoLeadingZeros().getOrThrow())
        assertEquals(128, byteArrayOf(0x80.toByte()).toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(128, byteArrayOf(0x80.toByte()).toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())
        assertEquals(
            128,
            byteArrayOf(0x80.toByte(), 1).toIntNoLeadingZeros(1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )
        assertEquals(
            128,
            byteArrayOf(0x80.toByte(), 1).toIntNoLeadingZeros(1, endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )

        assertEquals(Int.MAX_VALUE, "7FFFFFFF".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(
            Int.MAX_VALUE,
            "7FFFFFFF".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )
        assertEquals(
            Int.MAX_VALUE,
            "FFFFFF7F".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )
        assertEquals(Int.MAX_VALUE, "7FFFFFFF88".hexToBytes().toIntNoLeadingZeros(4).getOrThrow())
        assertEquals(
            Int.MAX_VALUE,
            "7FFFFFFF88".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.BIG_ENDIAN).getOrThrow()
        )
        assertEquals(
            Int.MAX_VALUE,
            "FFFFFF7F88".hexToBytes().toIntNoLeadingZeros(4, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )

        assertEquals(2147483647, "7FFFFFFF".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(2147483647, "7FFFFFFF88".hexToBytes().toIntNoLeadingZeros(4).getOrThrow())

        assertEquals(16777215, "FFFFFF".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(16777215, "FFFFFF88".hexToBytes().toIntNoLeadingZeros(3).getOrThrow())

        assertEquals(66051, "010203".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(66051, "010203".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(66051, "030201".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())
        assertEquals(66051, "01020388".hexToBytes().toIntNoLeadingZeros(3).getOrThrow())
        assertEquals(66051, "01020388".hexToBytes().toIntNoLeadingZeros(3, endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(
            66051,
            "03020188".hexToBytes().toIntNoLeadingZeros(3, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow()
        )


        assertEquals(65535, "FFFF".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(65535, "FFFF88".hexToBytes().toIntNoLeadingZeros(2).getOrThrow())

        assertEquals(258, "0102".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(258, "0102".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(258, "0201".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertEquals(258, "010288".hexToBytes().toIntNoLeadingZeros(2).getOrThrow())
        assertEquals(258, "010288".hexToBytes().toIntNoLeadingZeros(2, endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(258, "020188".hexToBytes().toIntNoLeadingZeros(2, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertEquals(18, "12".hexToBytes().toIntNoLeadingZeros().getOrThrow())
        assertEquals(18, "12".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(18, "12".hexToBytes().toIntNoLeadingZeros(endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertEquals(18, "1288".hexToBytes().toIntNoLeadingZeros(1).getOrThrow())
        assertEquals(18, "1288".hexToBytes().toIntNoLeadingZeros(1, endian = ByteOrder.BIG_ENDIAN).getOrThrow())
        assertEquals(18, "1288".hexToBytes().toIntNoLeadingZeros(1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow())

        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(2).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(2, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow() }

        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(1, 1).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(1, 1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(2, -1).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(2, -1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(-1).getOrThrow() }
        assertFails { "FF".hexToBytes().toIntNoLeadingZeros(-1, endian = ByteOrder.LITTLE_ENDIAN).getOrThrow() }

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testReadIntNoLeadingZeros() {


        assertFails { IntBuffer.wrap(byteArrayOf()).readIntNoLeadingZeros(1).getOrThrow() }
            .message?.contains("ByteArray Empty") ?: false

        assertEquals(
            128,
            IntBuffer.wrap(byteArrayOf(0x80.toByte()))
                .rewind()
                .readIntNoLeadingZeros(1).getOrThrow()
        )
        assertEquals(
            128,
            IntBuffer.wrap(byteArrayOf(0x80.toByte()))
                .rewind()
                .readIntNoLeadingZeros(2).getOrThrow()
        )

        assertEquals(
            -2147483648,
            IntBuffer.wrap("80000000".hexToBytes())
                .rewind()
                .readIntNoLeadingZeros(4).getOrThrow()
        )
        assertEquals(
            -1,
            IntBuffer.wrap("FFFFFFFF".hexToBytes())
                .rewind()
                .readIntNoLeadingZeros(4).getOrThrow()
        )

        assertEquals(
            Int.MAX_VALUE,
            IntBuffer.wrap("7FFFFFFF".hexToBytes())
                .rewind()
                .readIntNoLeadingZeros(4).getOrThrow()
        )
        assertEquals(
            16777215,
            IntBuffer.wrap("FFFFFF".hexToBytes())
                .rewind()
                .readIntNoLeadingZeros(3).getOrThrow()
        )
        assertEquals(
            65535,
            IntBuffer.wrap("FFFF".hexToBytes())
                .rewind()
                .readIntNoLeadingZeros(2).getOrThrow())
        assertEquals(
            255,
            IntBuffer.wrap("FF".hexToBytes())
            .rewind()
            .readIntNoLeadingZeros(1).getOrThrow())

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testULongToByteArrayWithoutLeadingZeros() {

        assertTrue(
            128uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals(ubyteArrayOf(0x80.toUByte()))
        )
        assertTrue(
            9223372036854775808uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("8000000000000000".hexToBytes().toUByteArray())
        )
        assertTrue(
            ULong.MAX_VALUE.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFFFFFFFF".hexToBytes().toUByteArray())
        )

        assertTrue(
            72057594037927935uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            281474976710655uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            1099511627775uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            4294967295uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            16777215uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            65535uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            255uL.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FF".hexToBytes().toUByteArray())
        )

    }

    @ExperimentalUnsignedTypes
    @Test
    fun testLongToByteArrayWithoutLeadingZeros() {

        assertTrue(
            128L.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals(byteArrayOf(0x80.toByte()))
        )
        assertTrue(
            Long.MIN_VALUE.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("8000000000000000".hexToBytes())
        )
        assertTrue(
            Long.MAX_VALUE.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("7FFFFFFFFFFFFFFF".hexToBytes())
        )
        assertTrue(
            (-1L).toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFFFFFFFF".hexToBytes())
        )
        assertTrue(
            281474976710655L.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFFFF".hexToBytes())
        )
        assertTrue(
            1099511627775L.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFFFF".hexToBytes())
        )
        assertTrue(
            4294967295L.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFF".hexToBytes())
        )
        assertTrue(
            16777215L.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFF".hexToBytes())
        )
        assertTrue(65535L.toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals("FFFF".hexToBytes()))
        assertTrue(255L.toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals("FF".hexToBytes()))

    }


    @ExperimentalUnsignedTypes
    @Test
    fun testUIntToUByteArrayWithoutLeadingZeros() {

        assertTrue(128u.toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals(ubyteArrayOf(0x80.toUByte())))
        assertTrue(
            9223372036854775808u.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("8000000000000000".hexToBytes().toUByteArray())
        )
        assertTrue(
            UInt.MAX_VALUE.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFF".hexToBytes().toUByteArray())
        )

        assertTrue(
            4294967295u.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            16777215u.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            65535u.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFF".hexToBytes().toUByteArray())
        )
        assertTrue(
            255u.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FF".hexToBytes().toUByteArray())
        )

    }

    @Test
    @ExperimentalUnsignedTypes
    fun testIntToByteArrayWithoutLeadingZeros() {

        assertTrue(
            128.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals(byteArrayOf(0x80.toByte()))
        )

        assertTrue(
            (-2147483648).toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals(
                byteArrayOf(0x80.toByte(), 0, 0, 0)
            )
        )

        assertTrue(
            Int.MAX_VALUE.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("7FFFFFFF".hexToBytes())
        )

        assertTrue(
            2147483647.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("7FFFFFFF".hexToBytes())
        )
        assertTrue(
            16777215.toByteArrayWithoutLeadingZeros().getOrThrow()
                .contentEquals("FFFFFF".hexToBytes())
        )
        assertTrue(65535.toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals("FFFF".hexToBytes()))
        assertTrue(255.toByteArrayWithoutLeadingZeros().getOrThrow().contentEquals("FF".hexToBytes()))

    }

    @Suppress("unused")
    @ExperimentalTime
    fun speedTestInt() {
        val buf = "7FFFFFFF".hexToBytes()
        val duration = measureTime {
            repeat(1_000_000) {
                buf.toIntNoLeadingZeros()
            }
        }
        println(duration)
    }

    @Suppress("unused")
    @ExperimentalTime
    fun speedTestLong() {
        val buf = "7FFFFFFFFFFFFFFF".hexToBytes()
        val duration = measureTime {
            repeat(1_000_000) {
                buf.toLongNoLeadingZeros()
            }
        }
        println(duration)
    }
}