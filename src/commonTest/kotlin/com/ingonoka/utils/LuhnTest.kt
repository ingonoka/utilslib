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
import kotlin.test.assertTrue

class LuhnKtTest {

    @Test
    fun luhnCheckSumString() {
        assertEquals(3, "7992739871".luhnCheckSum())
        assertEquals(5, "37828224631000".luhnCheckSum())
        assertEquals(5, "117123456701234".luhnCheckSum())
        assertEquals(1, "401288888888188".luhnCheckSum())

    }

    @Test
    fun luhnCheckSumList() {
        assertEquals(3, listOf(7, 9, 9, 2, 7, 3, 9, 8, 7, 1).luhnCheckSum())
        assertEquals(5, listOf(3, 7, 8, 2, 8, 2, 2, 4, 6, 3, 1, 0, 0, 0).luhnCheckSum())
        assertEquals(5, listOf(1, 1, 7, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4).luhnCheckSum())
        assertEquals(1, listOf(4, 0, 1, 2, 8, 8, 8, 8, 8, 8, 8, 8, 1, 8, 8).luhnCheckSum())

    }

    @Test
    fun luhnCheckSumLong() {
        assertEquals(3, 7992739871L.luhnCheckSum())
        assertEquals(5, 37828224631000L.luhnCheckSum())
        assertEquals(5, 117123456701234L.luhnCheckSum())
        assertEquals(1, 401288888888188L.luhnCheckSum())

    }

    @Test
    fun validateLuhnChecksumString() {
        assertTrue("4012888888881881".validateLuhnChecksum())
        assertTrue("378282246310005".validateLuhnChecksum())
        assertTrue("79927398713".validateLuhnChecksum())
        assertTrue("1171234567012345".validateLuhnChecksum())
    }

    @Test
    fun validateLuhnChecksumList() {
        assertTrue(listOf(7, 9, 9, 2, 7, 3, 9, 8, 7, 1, 3).validateLuhnChecksum())
        assertTrue(listOf(3, 7, 8, 2, 8, 2, 2, 4, 6, 3, 1, 0, 0, 0, 5).validateLuhnChecksum())
        assertTrue(listOf(1, 1, 7, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5).validateLuhnChecksum())
        assertTrue(listOf(4, 0, 1, 2, 8, 8, 8, 8, 8, 8, 8, 8, 1, 8, 8, 1).validateLuhnChecksum())
    }

    @Test
    fun validateLuhnChecksumLong() {
        assertTrue(4012888888881881.validateLuhnChecksum())
        assertTrue(378282246310005.validateLuhnChecksum())
        assertTrue(79927398713.validateLuhnChecksum())
        assertTrue(1171234567012345.validateLuhnChecksum())
    }
}