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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

class Base64KtTest {

    @ExperimentalStdlibApi
    @Test
    fun testToBase64() {

        assertTrue("Zg==".toUtf8().contentEquals("f".toUtf8().toBase64()))
        assertTrue("Zm8=".toUtf8().contentEquals("fo".toUtf8().toBase64()))
        assertTrue("Zm9v".toUtf8().contentEquals("foo".toUtf8().toBase64()))
        assertTrue("Zm9vYg==".toUtf8().contentEquals("foob".toUtf8().toBase64()))
        assertTrue("Zm9vYmE=".toUtf8().contentEquals("fooba".toUtf8().toBase64()))
        assertTrue("Zm9vYmFy".toUtf8().contentEquals("foobar".toUtf8().toBase64()))

        assertEquals("Zg==", "f".toBase64())
        assertEquals("Zm8=", "fo".toBase64())
        assertEquals("Zm9v", "foo".toBase64())
        assertEquals("Zm9vYg==", "foob".toBase64())
        assertEquals("Zm9vYmE=", "fooba".toBase64())
        assertEquals("Zm9vYmFy", "foobar".toBase64())

        val actual = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes().toBase64()
        val expected =
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").toUtf8()

        assertTrue(expected.contentEquals(actual))
    }

    @ExperimentalTime
    @ExperimentalStdlibApi
    @Test
    fun testFromBase64() {

        assertEquals("f", "Zg==".fromBase64().decodeToString())
        assertEquals("fo", "Zm8=".fromBase64().decodeToString())
        assertEquals("foo", "Zm9v".fromBase64().decodeToString())
        assertEquals("foob", "Zm9vYg==".fromBase64().decodeToString())
        assertEquals("fooba", "Zm9vYmE=".fromBase64().decodeToString())
        assertEquals("foobar", "Zm9vYmFy".fromBase64().decodeToString())

        assertTrue("f".toUtf8().contentEquals("Zg==".toUtf8().fromBase64()))
        assertTrue("fo".toUtf8().contentEquals("Zm8=".toUtf8().fromBase64()))
        assertTrue("foo".toUtf8().contentEquals("Zm9v".toUtf8().fromBase64()))
        assertTrue("foob".toUtf8().contentEquals("Zm9vYg==".toUtf8().fromBase64()))
        assertTrue("fooba".toUtf8().contentEquals("Zm9vYmE=".toUtf8().fromBase64()))
        assertTrue("foobar".toUtf8().contentEquals("Zm9vYmFy".toUtf8().fromBase64()))


        val expected = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes()
        val actual = ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").fromBase64()

        assertTrue(expected.contentEquals(actual))

    }

    @Test
    fun testSpeedByteArrayToBase64() {
        val buf = ("8505435056303161564F06514341543031634CC103131388C2020113C3045D78DCB6C4020384DE37023034021" +
                "859527B7951E77EB6CB250149FFA2006B1A415297D13AA48A021840986DC05DB2235088DB459938982" +
                "3A324842E73A635B3FD").hexToBytes()
        repeat(1000000) { buf.toBase64() }
    }

    @Test
    fun testSpeedStringFromBase64() {
        val arr = "hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0="

        repeat(1000000) { arr.fromBase64() }

    }

    @Test
    fun testSpeedByteArrayFromBase64() {
        val arr = ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").toUtf8()

        repeat(1000000) { arr.fromBase64() }

    }

    @Test
    fun testSpeedByteReadPacketFromBase64() {
        val arr = PoorMansByteBuffer(
            ("hQVDUFYwMWFWTwZRQ0FUMDFjTMEDExOIwgIBE8MEXXjctsQCA4TeNwIwNAIYWVJ7eVHnfrbLJQFJ/6IAaxpBUpfROqSKA" +
                    "hhAmG3AXbIjUIjbRZk4mCOjJIQuc6Y1s/0=").toUtf8()
        )

        repeat(1000000) { arr.fromBase64() }

    }

    @Test
    fun printIndices() {

        indicesTwo.forEach { if (it == -1) print("-1, ") else print("0x${it.toString(16)}, ") }
        println()
    }

    @Test
    fun testIsBase64() {

        for(c in alphabet) {
            assertTrue(c.toInt().toChar().isBase64())
        }

        assertTrue('='.isBase64())

        assertFalse('@'.isBase64())

        assertTrue("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/=123".isBase64())

        assertFalse("ABC".isBase64())

        assertFalse("ABC&".isBase64())

    }

}