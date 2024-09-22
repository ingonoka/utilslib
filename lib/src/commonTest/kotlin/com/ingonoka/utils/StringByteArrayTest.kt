/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class StringByteArrayTest {

    @Test
    fun testByteArrayToString() {

        assertEquals("12", byteArrayOf(0x31, 0x32).asUtf8ToString())
        assertEquals("", byteArrayOf().asUtf8ToString())
        assertEquals("\u00001", byteArrayOf(0, 0x31).asUtf8ToString())
        assertEquals("\u00001\u0000", byteArrayOf(0, 0x31, 0).asUtf8ToString())

        assertEquals("2", byteArrayOf(0x31, 0x32).asUtf8ToString(1))
        assertEquals("2", byteArrayOf(0x31, 0x32).asUtf8ToString(1, 1))
        assertEquals("1", byteArrayOf(0x31, 0x32).asUtf8ToString(0, 1))
        assertEquals("12", byteArrayOf(0x31, 0x32).asUtf8ToString(0, 2))
        assertEquals("12", byteArrayOf(0x31, 0x32).asUtf8ToString(0))
        assertFails { byteArrayOf(0x31, 0x32).asUtf8ToString(0, 3) }

    }

    @Test
    fun testStringToByteArray() {

        assertArrayEquals(byteArrayOf(0x31, 0x32).toTypedArray(), "12".toUtf8().toTypedArray())
        assertArrayEquals(byteArrayOf().toTypedArray(), "".toUtf8().toTypedArray())
        assertArrayEquals(byteArrayOf(0, 0x31).toTypedArray(), "\u00001".toUtf8().toTypedArray())
        assertArrayEquals(byteArrayOf(0, 0x31, 0).toTypedArray(), "\u00001\u0000".toUtf8().toTypedArray())

    }

}