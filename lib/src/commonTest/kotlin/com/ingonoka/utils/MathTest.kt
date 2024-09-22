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

class MathTest {

    @Test
    fun testRoundUpToMultipleOf() {

        assertEquals(-0, (0).roundUpToMultipleOf(8))
        assertEquals(-0, (-7).roundUpToMultipleOf(8))
        assertEquals(-8, (-8).roundUpToMultipleOf(8))
        assertEquals(-16, (-17).roundUpToMultipleOf(8))

        assertEquals(2, 1.roundUpToMultipleOf(2))
        assertEquals(2, 2.roundUpToMultipleOf(2))
        assertEquals(4, 3.roundUpToMultipleOf(2))

        assertEquals(8, 7.roundUpToMultipleOf(8))
        assertEquals(8, 8.roundUpToMultipleOf(8))
        assertEquals(16, 9.roundUpToMultipleOf(8))

        assertEquals(16, 15.roundUpToMultipleOf(16))
        assertEquals(16, 16.roundUpToMultipleOf(16))
        assertEquals(32, 17.roundUpToMultipleOf(16))
        
        assertEquals(32, 31.roundUpToMultipleOf(32))
        assertEquals(32, 32.roundUpToMultipleOf(32))
        assertEquals(64, 33.roundUpToMultipleOf(32))

        assertEquals(2147483616, (Int.MAX_VALUE-31).roundUpToMultipleOf(32))
        assertEquals(2147483640, (Int.MAX_VALUE-7).roundUpToMultipleOf(8))
        assertEquals(2147483646, (Int.MAX_VALUE-1).roundUpToMultipleOf(2))

        assertEquals(-2147483646, (Int.MIN_VALUE+1).roundUpToMultipleOf(2))

        // LONG

        assertEquals(0L, (0L).roundUpToMultipleOf(8L))
        assertEquals(-0L, (-7L).roundUpToMultipleOf(8L))
        assertEquals(-8L, (-8L).roundUpToMultipleOf(8L))
        assertEquals(-16L, (-17L).roundUpToMultipleOf(8L))

        assertEquals(8L, 7L.roundUpToMultipleOf(8L))
        assertEquals(8L, 8L.roundUpToMultipleOf(8L))
        assertEquals(16L, 9L.roundUpToMultipleOf(8L))

        assertEquals(16L, 15L.roundUpToMultipleOf(16L))
        assertEquals(16L, 16L.roundUpToMultipleOf(16L))
        assertEquals(32L, 17L.roundUpToMultipleOf(16L))

        assertEquals(32L, 31L.roundUpToMultipleOf(32L))
        assertEquals(32L, 32L.roundUpToMultipleOf(32L))
        assertEquals(64L, 33L.roundUpToMultipleOf(32L))

        assertEquals(9223372036854775776, (Long.MAX_VALUE-31).roundUpToMultipleOf(32L))
        assertEquals(9223372036854775800, (Long.MAX_VALUE-7).roundUpToMultipleOf(8))

    }
}
