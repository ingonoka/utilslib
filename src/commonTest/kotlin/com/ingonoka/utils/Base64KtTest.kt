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
import com.ingonoka.hexutils.hexToListOfInt
import com.ingonoka.hexutils.toHexShortShort
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.measureTime

class Base64KtTest {

    @Test
    fun testToBase64() {

        assertEquals("Zg==".map { it.code }, "f".encodeToByteArray().toBase64())
        assertEquals("Zm8=".map { it.code }, "fo".encodeToByteArray().toBase64())
        assertEquals("Zm9v".map { it.code }, "foo".encodeToByteArray().toBase64())
        assertEquals("Zm9vYg==".map { it.code }, "foob".encodeToByteArray().toBase64())
        assertEquals("Zm9vYmE=".map { it.code }, "fooba".encodeToByteArray().toBase64())
        assertEquals("Zm9vYmFy".map { it.code }, "foobar".encodeToByteArray().toBase64())

        val bytes = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes()

        val expected =
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").map { it.code }

        assertEquals(expected, bytes.toBase64())

    }

    @Test
    fun testFromListOfIntToBase64() {

        assertEquals("Zg==".map { it.code }, "f".encodeToByteArray().toListOfInt().toBase64())
        assertEquals("Zm8=".map { it.code }, "fo".encodeToByteArray().toListOfInt().toBase64())
        assertEquals("Zm9v".map { it.code }, "foo".encodeToByteArray().toListOfInt().toBase64())
        assertEquals("Zm9vYg==".map { it.code }, "foob".encodeToByteArray().toListOfInt().toBase64())
        assertEquals("Zm9vYmE=".map { it.code }, "fooba".encodeToByteArray().toListOfInt().toBase64())
        assertEquals("Zm9vYmFy".map { it.code }, "foobar".encodeToByteArray().toListOfInt().toBase64())

        val bytes = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToListOfInt()

        val expected =
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").map { it.code }

        assertEquals(expected, bytes.toBase64())
    }

    @Test
    fun testFromStringToBase64() {

        assertEquals("Zg==".map { it.code }, "f".toBase64())
        assertEquals("Zm8=".map { it.code }, "fo".toBase64())
        assertEquals("Zm9v".map { it.code }, "foo".toBase64())
        assertEquals("Zm9vYg==".map { it.code }, "foob".toBase64())
        assertEquals("Zm9vYmE=".map { it.code }, "fooba".toBase64())
        assertEquals("Zm9vYmFy".map { it.code }, "foobar".toBase64())

    }

    @Test
    fun testFromIntBufferToBase64() {

        assertEquals("Zg==".map { it.code }, BufferImpl.wrap("f".encodeToByteArray()).toBase64())
        assertEquals("Zm8=".map { it.code }, BufferImpl.wrap("fo".encodeToByteArray()).toBase64())
        assertEquals("Zm9v".map { it.code }, BufferImpl.wrap("foo".encodeToByteArray()).toBase64())
        assertEquals("Zm9vYg==".map { it.code }, BufferImpl.wrap("foob".encodeToByteArray()).toBase64())
        assertEquals("Zm9vYmE=".map { it.code }, BufferImpl.wrap("fooba".encodeToByteArray()).toBase64())
        assertEquals("Zm9vYmFy".map { it.code }, BufferImpl.wrap("foobar".encodeToByteArray()).toBase64())

        val bytes = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes()

        val expected =
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").map { it.code }

        assertEquals(expected, BufferImpl.wrap(bytes).toBase64())

    }

    @Test
    fun testFromBase64() {

        assertEquals("f", "Zg==".fromBase64().toByteArray().decodeToString())
        assertEquals("fo", "Zm8=".fromBase64().toByteArray().decodeToString())
        assertEquals(listOf('f'.code, 'o'.code, 'o'.code), "Zm9v".fromBase64())
        assertEquals("foob".map { it.code }, "Zm9vYg==".fromBase64())
        assertEquals("fooba".map { it.code }, "Zm9vYmE=".fromBase64())
        assertEquals("foobar".map { it.code }, "Zm9vYmFy".fromBase64())

        assertEquals("f".map { it.code }, "Zg==".fromBase64())
        assertEquals("fo".map { it.code }, "Zm8=".fromBase64())
        assertEquals("foo".map { it.code }, "Zm9v".fromBase64())
        assertEquals("foob".map { it.code }, "Zm9vYg==".fromBase64())
        assertEquals("fooba".map { it.code }, "Zm9vYmE=".fromBase64())
        assertEquals("foobar".map { it.code }, "Zm9vYmFy".fromBase64())


        val expected = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD")
        val actual = ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").fromBase64().toHexShortShort()

        assertEquals(expected, actual)
    }

    @Test
    fun testSpeedByteArrayToBase64() {
        val buf = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes()

        buf.toHexString()

        measureTime {
            repeat(1_000_000) { buf.toBase64() }
        }.also {
            println("ByteArray.toBase64: ${it / 1_000_000}")
        }
    }

    @Test
    fun testSpeedStringFromBase64() {
        val arr = "hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7e" +
                "VHnfrbLJQFJ/6IAaxpBUpfROqSKAhhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0="

        measureTime {
            repeat(1_000_000) { arr.fromBase64() }
        }.also {
            println("String.fromBase64: ${it/1_000_000}")
        }

    }

    @Test
    fun testSpeedByteArrayFromBase64() {
        val b64 = ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").encodeToByteArray()

        measureTime {
            repeat(1_000_000) { b64.fromBase64() }
        }.also {
            println("ByteArray.fromBase64: ${it/1_000_000}")
        }

    }

    @Test
    fun testSpeedListOfIntFromBase64() {
        val b64 = ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").map { it.code }

        measureTime {
            repeat(1_000_000) {
                b64.fromBase64()
            }
        }.also {
            println("List<Int>.fromBase64: ${it/1_000_000}")
        }

    }

    @Test
    fun testSpeedByteReadPacketFromBase64() {
        val b64 = BufferImpl.wrap(
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").encodeToByteArray()
        )

        measureTime {
            repeat(1_000_000) { b64.fromBase64() }
        }.also {
            println("ReadIntBuffer.fromBase64: ${it/1_000_000}")
        }

    }

    @Test
    fun printIndices() {

        indicesTwo.forEach { if (it == -1) print("-1, ") else print("0x${it.toString(16)}, ") }
        println()
    }

    @Test
    fun testIsBase64() {

        for (c in alphabet) {
            assertTrue(c.toInt().toChar().isBase64())
        }

        assertTrue('='.isBase64())

        assertFalse('@'.isBase64())

        assertTrue("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/=123".isBase64())

        assertFalse("ABC".isBase64())

        assertFalse("ABC&".isBase64())

    }
}